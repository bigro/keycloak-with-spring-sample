package com.example.sampleaccount.domain.model.account;

public class AuthenticationKey {
    AccountIdentifier identifier;
    ConfirmationCode confirmationCode;

    public AuthenticationKey(AccountIdentifier identifier, ConfirmationCode confirmationCode) {
        this.identifier = identifier;
        this.confirmationCode = confirmationCode;
    }

    public AccountIdentifier identifier() {
        return identifier;
    }

    public ConfirmationCode confirmationCode() {
        return confirmationCode;
    }
}
