package com.henning.oden.java.StockAlert.Backend.services;

import com.henning.oden.java.StockAlert.Backend.dto.RegistrationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.RegistrationResponse;
import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsService userDetailsService;

    public RegistrationResponse registerUser (RegistrationRequest registrationRequest) {
        try {
            userDetailsService.loadUserByUsername(registrationRequest.getUsername());
        } catch (UsernameNotFoundException ex) {
            SystemUser user = SystemUser.fromRegistrationRequest(registrationRequest);
            try {
                SystemUser savedUser = userDetailsService.save(user);
                emailService.sendRegistrationConfirmationEmail(registrationRequest, savedUser.getId());
                RegistrationResponse response = new RegistrationResponse("Success", "New user with username " + registrationRequest.getUsername() + " registered. Please check your e-mail!");
                return response;
            } catch (RuntimeException rex) {
                userDetailsService.deleteUser(user);
                return new RegistrationResponse("Failure", "An error occurred while registering the user.");
                // todo: Log error at warning level.
            }
        }
        RegistrationResponse response = new RegistrationResponse("Failure", "User with username " + registrationRequest.getUsername() + " already exists.");
        return response;
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "UserRegistrationService{" +
                "emailService=" + emailService +
                ", passwordEncoder=" + passwordEncoder +
                ", userDetailsService=" + userDetailsService +
                '}';
    }
}
