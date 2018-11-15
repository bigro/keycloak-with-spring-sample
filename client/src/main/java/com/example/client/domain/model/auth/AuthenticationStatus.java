package com.example.client.domain.model.auth;

public enum  AuthenticationStatus {
    SUCCESS, FAIL;

    public boolean isSuccess() {
        return this == AuthenticationStatus.SUCCESS;
    }

    public boolean isFailed() {
        return this == AuthenticationStatus.FAIL;
    }
}
