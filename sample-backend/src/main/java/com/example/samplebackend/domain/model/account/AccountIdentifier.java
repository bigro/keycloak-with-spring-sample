package com.example.samplebackend.domain.model.account;

public class AccountIdentifier {
    private String value;

    public AccountIdentifier(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
