package com.henning.oden.java.StockAlert.Backend.controllers;

import com.henning.oden.java.StockAlert.Backend.dto.AuthenticationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.AuthenticationResponse;
import com.henning.oden.java.StockAlert.Backend.dto.RegistrationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.RegistrationResponse;
import com.henning.oden.java.StockAlert.Backend.dto.UserInfoResponse;
import com.henning.oden.java.StockAlert.Backend.dto.ValidationResponse;
import com.henning.oden.java.StockAlert.Backend.security.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.security.services.JwtAuthenticationService;
import com.henning.oden.java.StockAlert.Backend.services.EmailConfirmationTokenService;
import com.henning.oden.java.StockAlert.Backend.services.EmailService;
import com.henning.oden.java.StockAlert.Backend.services.UserRegistrationService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowCredentials = "true")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AuthController {

    private JwtAuthenticationService jwtAuthenticationService;
    private JwtTokenProvider jwtTokenProvider;
    private UserRegistrationService userRegistrationService;
    private EmailService emailService;
    private EmailConfirmationTokenService emailConfirmationTokenService;

    @PostMapping("/signin")
    public AuthenticationResponse signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();
            String token = jwtAuthenticationService.authenticateUser(username, password);
            AuthenticationResponse response = new AuthenticationResponse(username, token);
            return response;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials supplied");
        }
    }

    @GetMapping("/info")
    public UserInfoResponse info(HttpServletRequest req) {
        String username = jwtTokenProvider.getCurrentUserUsername(req);
        return new UserInfoResponse(username);
    }

    @PostMapping("/signup")
    public RegistrationResponse signup(@RequestBody RegistrationRequest req) {
        return userRegistrationService.registerUser(req);
    }

    @GetMapping("/validate")
    public ValidationResponse validateEmail(@RequestParam String token) {
        return emailConfirmationTokenService.verifyEmail(token);
    }
}
