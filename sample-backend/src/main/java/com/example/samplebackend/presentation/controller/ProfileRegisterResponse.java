package com.example.samplebackend.presentation.controller;

public class ProfileRegisterResponse {
    String status;

    public ProfileRegisterResponse(String status) {
        this.status = status;
    }

    public static ProfileRegisterResponse success() {
        return new ProfileRegisterResponse("SUCCESS");
    }

    public String getStatus() {
        return status;
    }
}
