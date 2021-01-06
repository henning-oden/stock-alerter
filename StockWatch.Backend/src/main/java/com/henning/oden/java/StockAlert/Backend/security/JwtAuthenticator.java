package com.henning.oden.java.StockAlert.Backend.security;

import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtAuthenticator {
    @Getter @Setter
    private CustomUserDetailsService userDetailsService;
    @Getter @Setter
    private AuthenticationManager authenticationManager;
    @Getter @Setter
    private JwtTokenProvider jwtTokenProvider;

    public String authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.createToken(username, userDetailsService.loadUserByUsername(username)
                .getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
    }

    public String getCurrentUserUsername(HttpServletRequest req) {
        String username = jwtTokenProvider.getUsernameFromRequest(req);
        UserDetails authenticatedUser = userDetailsService.loadUserByUsername(username);
        Authentication claims = jwtTokenProvider.getAuthentication(authenticatedUser);
        return claims.getName();
    }

    public Authentication getAuthenticationFromToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenProvider.getAuthentication(userDetails);
    }

    public String resolveToken(HttpServletRequest req) {
        return jwtTokenProvider.resolveToken(req);
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}