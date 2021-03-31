package com.henning.oden.java.StockAlert.Backend.security.services;

import com.henning.oden.java.StockAlert.Backend.entities.SystemUser;
import com.henning.oden.java.StockAlert.Backend.security.JwtTokenProvider;
import com.henning.oden.java.StockAlert.Backend.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtAuthenticationService {
    @Getter @Setter
    private CustomUserDetailsService userDetailsService;
    @Getter @Setter
    private AuthenticationManager authenticationManager;
    @Getter @Setter
    private JwtTokenProvider jwtTokenProvider;

    public String authenticateUser(String username, String password) {
        SystemUser user = (SystemUser) userDetailsService.loadUserByUsername(username);
        if( !user.isEmailVerified() ) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "User email is not verified.");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.createToken(username, userDetailsService.loadUserByUsername(username)
                .getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
    }

    public String getCurrentUserUsername(HttpServletRequest req) {
        UserDetails authenticatedUser = getUserDetailsFromRequest(req);
        Authentication claims = jwtTokenProvider.getAuthentication(authenticatedUser);
        return claims.getName();
    }

    private UserDetails getUserDetailsFromRequest(HttpServletRequest req) {
        String username = jwtTokenProvider.getUsernameFromRequest(req);
        return userDetailsService.loadUserByUsername(username);
    }

    /**
     * Auto-generated toString() method. Format is unspecified and may change.
     */
    @Override
    public String toString() {
        return "JwtAuthenticationService{" +
                "userDetailsService=" + userDetailsService +
                ", authenticationManager=" + authenticationManager +
                ", jwtTokenProvider=" + jwtTokenProvider +
                '}';
    }
}