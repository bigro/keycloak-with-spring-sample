package com.example.client.domain.model.account;

public class Account {
    AccountIdentifier identifier;
    AccountMailAddress mailAddress;

    public Account(AccountIdentifier identifier, AccountMailAddress mailAddress) {
        this.identifier = identifier;
        this.mailAddress = mailAddress;
    }

    public AccountIdentifier identifier() {
        return identifier;
    }

    public AccountMailAddress mailAddress() {
        return mailAddress;
    }
}
