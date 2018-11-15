package com.example.client.presentation.controller;

import com.example.client.domain.model.auth.AuthenticationStatus;

public class AuthenticationResponse {
    AuthenticationStatus status;

    public AuthenticationStatus getStatus() {
        return status;
    }
}
