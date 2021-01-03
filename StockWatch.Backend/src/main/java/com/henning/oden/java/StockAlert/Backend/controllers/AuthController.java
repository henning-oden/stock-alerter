package com.henning.oden.java.StockAlert.Backend.controllers;

import com.henning.oden.java.StockAlert.Backend.entities.AuthenticationRequest;
import com.henning.oden.java.StockAlert.Backend.security.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class AuthController {

    AuthenticationManager authenticationManager;

    JwtTokenProvider jwtTokenProvider;

    CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody AuthenticationRequest data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.userDetailsService.loadUserByUsername(username)
                    .getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials supplied");
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<Object, Object>> info(HttpServletRequest req) {
        String token = jwtTokenProvider.resolveToken(req);
        Authentication claims = jwtTokenProvider.getAuthentication(token);
        String username = claims.getName();
        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        return ResponseEntity.ok(model);
    }
}
