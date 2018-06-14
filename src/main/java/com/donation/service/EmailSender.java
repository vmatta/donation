package com.donation.service;

import com.donation.exception.MailException;
import com.donation.model.EmailStatus;
import com.donation.model.ImageMapper;
import com.donation.model.Mail;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Created by Sumit on 6/11/2018.
 */
@Service
public class EmailSender {
    public static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailStatus sendPlainText(Mail mail, String text) {
        return sendM(mail, text, false);
    }

    public EmailStatus sendHtml(Mail mail, String htmlBody) {
        return sendM(mail, htmlBody, true);
    }

    private EmailStatus sendM(Mail mail, String text, Boolean isHtml) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(mail.getRecipient());
            helper.setSubject(mail.getSubject());
            helper.setText(text, isHtml);
            List<ImageMapper> imageMappers = defaultIfNull(mail.getImageMappers(), emptyList());
            imageMappers.forEach(imageMapper -> {
                addInLineImage(helper, imageMapper);
            });
            javaMailSender.send(message);
            LOGGER.info("Send email '{}' to: {}", mail.getSubject(), mail.getRecipient());
            return new EmailStatus(mail.getRecipient(), mail.getSubject(), text).success();
        } catch (Exception e) {
            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", mail.getRecipient(), e.getMessage()));
            return new EmailStatus(mail.getRecipient(), mail.getSubject(), text).error(e.getMessage());
        }
    }

    private void addInLineImage(MimeMessageHelper helper, ImageMapper imageMapper) {
        try {
            InputStreamSource byteArrayResource = new ByteArrayResource(IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream("static/images/" + imageMapper.getImageFileName())));
            helper.addInline(imageMapper.getImageFileName(), byteArrayResource, imageMapper.getContentType());
        } catch (IOException | MessagingException e) {
            throw new MailException(e);
        }
    }
}
