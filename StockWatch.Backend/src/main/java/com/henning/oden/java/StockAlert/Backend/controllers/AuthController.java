package com.henning.oden.java.StockAlert.Backend.controllers;

import com.henning.oden.java.StockAlert.Backend.dto.AuthenticationRequest;
import com.henning.oden.java.StockAlert.Backend.dto.AuthenticationResponse;
import com.henning.oden.java.StockAlert.Backend.security.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.security.services.JwtAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AuthController {

    private JwtAuthenticationService jwtAuthenticationService;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();
            String token = jwtAuthenticationService.authenticateUser(username, password);
            AuthenticationResponse response = new AuthenticationResponse(username, token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials supplied");
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String,String>> info(HttpServletRequest req) {
        String username = jwtTokenProvider.getCurrentUserUsername(req);
        return ResponseEntity.ok(Map.of("username", username));
    }
}
