package com.example.client.presentation.controller;

public enum ResetPasswordStatus {
    SUCCESS, FAIL;

    public boolean isFailed() {
        return this == ResetPasswordStatus.FAIL;
    }
}
