package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.io.Serializable;

@Value
// Does this need to be serializable?
public class AuthenticationRequest implements Serializable {
    private static final long serializableVersionUID = -6545648414564L;
    private String username, password;

    /**
     * Returns a brief description of the request detailing only which username
     * it belongs to. The password is excluded since it is not encrypted.
     */
    // However, since the password can be accessed programmatically, does excluding the password actually matter?
    @Override
    public String toString() {
        return "Authentication request for username " + username;
    }
}


