package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.dto.ValidationResponse;
import com.henning.oden.java.StockAlert.Backend.entities.EmailConfirmationToken;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.repos.EmailConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailConfirmationTokenService {
    private CustomUserDetailsService userDetailsService;
    private EmailConfirmationTokenRepository confirmationTokenRepository;

    public EmailConfirmationToken getEmailConfirmationToken(long userId) {
        String uuidString = UUID.randomUUID().toString();
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken(userId, uuidString);
        EmailConfirmationToken savedToken = confirmationTokenRepository.saveAndFlush(emailConfirmationToken);
        return savedToken;
    }

    public ValidationResponse verifyEmail(String token) {
        Optional<EmailConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isPresent()) {
            EmailConfirmationToken confirmationToken = confirmationTokenOptional.get();
            if (!confirmationToken.getIsUsed()) {
                verifyUserEmail(confirmationToken);
                consumeConfirmationToken(confirmationToken);
                return new ValidationResponse("Success", "E-mail successfully validated. You may now log in.");
            }
        }
        return new ValidationResponse("Failure", "You have used an invalid validation token.");
    }

    private void verifyUserEmail(EmailConfirmationToken confirmationToken) {
        Optional<SystemUser> userOptional = userDetailsService.findById(confirmationToken.getUserId());
        if (userOptional.isPresent()) {
            userDetailsService.verifyUserEmail(userOptional.get());
        } else {
            throw new RuntimeException("User not found by id " + confirmationToken.getUserId() + "!");
        }
    }

    private void consumeConfirmationToken(EmailConfirmationToken confirmationToken) {
        confirmationToken.setIsUsed(true);
        confirmationTokenRepository.saveAndFlush(confirmationToken);
    }
}
