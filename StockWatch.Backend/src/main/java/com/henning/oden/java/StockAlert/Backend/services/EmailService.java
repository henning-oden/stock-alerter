package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.dto.RegistrationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.StockAlertDto;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private EmailConfirmationTokenService emailConfirmationTokenService;

    @Value( "${sendgrid.api_key}" )
    private String sendGridKey;

    public EmailService(EmailConfirmationTokenService emailConfirmationTokenService) {
        this.emailConfirmationTokenService = emailConfirmationTokenService;
    }

    public boolean sendRegistrationConfirmationEmail(RegistrationRequest registrationRequest, long userId) {
        Email from = new Email("henning.oden@outlook.com");
        String subject = "Validate your StockAlert account e-mail";
        Email to = new Email(registrationRequest.getEmail());
        String token = emailConfirmationTokenService.getEmailConfirmationToken(userId).getToken();
        Content content = new Content("text/plain", "To validate your e-mail, use this link: <placeholder>?token=" + token);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();
        return sendMail(mail, sg, request);
    }

    private boolean sendMail(Mail mail, SendGrid sg, Request request) {
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean sendStockAlertEmail(StockAlertDto stockAlertDto) {
        Email from = new Email("henning.oden@outlook.com");
        String subject = "Stock watch alert!";
        Email to = new Email(stockAlertDto.getEmailAddress());
        Content content = new Content("text/plain",
                "Your stock watch for the stock " + stockAlertDto.getStockCommonName()
                        + " has triggered! Last recorded price was " + stockAlertDto.getLastPrice().toString()
                        + " dollars.");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();
        return sendMail(mail, sg, request);
    }
}
