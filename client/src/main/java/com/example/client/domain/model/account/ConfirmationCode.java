package com.example.client.domain.model.account;

public class ConfirmationCode {
    private String value;

    public ConfirmationCode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
