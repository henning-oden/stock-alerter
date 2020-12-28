package com.henning.oden.java.StockAlert.Backend.controllers;

import com.henning.oden.java.StockAlert.Backend.entities.AuthenticationRequest;
import com.henning.oden.java.StockAlert.Backend.jwt.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.repos.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    SystemUserRepository users;

    @PostMapping("/signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"))
                    .getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials supplied");
        }
    }
}
