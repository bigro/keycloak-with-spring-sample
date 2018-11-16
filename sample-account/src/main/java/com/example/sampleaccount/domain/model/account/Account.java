package com.example.sampleaccount.domain.model.account;

public class Account {
    AccountIdentifier identifier;
    AccountMailAddress mailAddress;
    AccountPassword password;

    public Account(AccountIdentifier identifier, AccountMailAddress mailAddress, AccountPassword password) {
        this.identifier = identifier;
        this.mailAddress = mailAddress;
        this.password = password;
    }

    public AccountIdentifier identifier() {
        return identifier;
    }

    public AccountMailAddress mailAddress() {
        return mailAddress;
    }

    public AccountPassword password() {
        return password;
    }
}
