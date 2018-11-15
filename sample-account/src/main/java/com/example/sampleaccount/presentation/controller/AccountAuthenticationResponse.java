package com.example.sampleaccount.presentation.controller;

public class AccountAuthenticationResponse {
    String status;

    public AccountAuthenticationResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
