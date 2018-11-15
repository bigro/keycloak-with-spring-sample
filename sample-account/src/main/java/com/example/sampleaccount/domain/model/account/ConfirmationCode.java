package com.example.sampleaccount.domain.model.account;

public class ConfirmationCode {
    static final int LENGTH = 1000;
    String value;

    public ConfirmationCode(String value) {
        this.value = value;
    }

    public static ConfirmationCode issue() {
        String code = String.format("%04d", (int) (Math.random() * LENGTH));
        return new ConfirmationCode(code);
    }

    public String value() {
        return value;
    }
}
