package com.example.sampleaccount.infrastructure.mail;

import com.example.sampleaccount.domain.model.account.AccountMailAddress;
import com.example.sampleaccount.domain.model.account.ConfirmationCode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationCodeMail {
    JavaMailSender mailSender;

    public ConfirmationCodeMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void send(AccountMailAddress mailAddress, ConfirmationCode issue) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(mailAddress.value());
        mailMessage.setFrom("account@example.com");
        mailMessage.setSubject("確認コード発行");
        mailMessage.setText("確認コードは\n" + issue.value() +  "\nです\n");

        mailSender.send(mailMessage);
    }
}

