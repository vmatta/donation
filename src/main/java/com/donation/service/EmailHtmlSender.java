package com.donation.service;

import com.donation.model.EmailStatus;
import com.donation.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by Sumit on 6/11/2018.
 */
@Service
public class EmailHtmlSender {
    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public EmailStatus send(Mail mail, String templateName, Context context) {
        String body = templateEngine.process(templateName, context);
        return emailSender.sendHtml(mail, body);
    }
}
