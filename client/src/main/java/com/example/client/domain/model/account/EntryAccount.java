package com.example.client.domain.model.account;

public class EntryAccount {
    AccountIdentifier identifier;
    AccountMailAddress mailAddress;

    public EntryAccount(AccountIdentifier identifier, AccountMailAddress mailAddress) {
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
