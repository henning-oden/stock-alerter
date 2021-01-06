package com.henning.oden.java.StockAlert.Backend.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class AuthenticationRequest implements Serializable {
    private static final long serializableVersionUID = -6545648414564L;
    private String username, password;
}


