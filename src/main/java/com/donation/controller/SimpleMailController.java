package com.donation.controller;

import com.donation.model.EmailStatus;
import com.donation.model.ImageMapper;
import com.donation.model.Mail;
import com.donation.service.EmailHtmlSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sumit on 6/11/2018.
 */
@RestController
@RequestMapping("/mail")
@Profile("dev")
public class SimpleMailController {

    @Autowired
    private EmailHtmlSender emailHtmlSender;

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public EmailStatus getFormData(@RequestParam("recipient") String recipient,
                              @RequestParam("subject") String subject) throws IOException {
        List<ImageMapper> imageFiles = Arrays.asList(ImageMapper.builder()
                .mapperName("imageResourceName")
                .imageFileName("email-banner.jpg")
                .contentType("image/jpeg")
                .build());
        Context context = new Context(Locale.forLanguageTag("en"));
        context.setVariable("title", "Sumit");
        context.setVariable("description", "doing test lets see");
        imageFiles.forEach(imageMapper ->
            context.setVariable(imageMapper.getMapperName(), imageMapper.getImageFileName())
        );
        context.setVariable("imageResourceName", "email-banner.jpg");
        Mail mail = Mail.builder().recipient(recipient).subject(subject).imageMappers(imageFiles).build();
        EmailStatus emailStatus = emailHtmlSender.send(mail, "email/email-template", context);
        return emailStatus;
    }
}
