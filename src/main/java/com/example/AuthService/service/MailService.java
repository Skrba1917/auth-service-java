package com.example.AuthService.service;

import com.example.AuthService.exceptions.SpringTwitterException;
import com.example.AuthService.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service

public class MailService {

	@Autowired
    private JavaMailSender mailSender;
    private  MailContentBuilder mailContentBuilder;

    
    public MailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
		super();
		this.mailSender = mailSender;
		this.mailContentBuilder = mailContentBuilder;
	}


	@Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springtwitter@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            
        } catch (MailException e) {
           
            throw new SpringTwitterException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
}
