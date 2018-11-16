package com.example.sampleaccount.presentation.controller;

import com.example.sampleaccount.domain.model.account.Account;
import com.example.sampleaccount.domain.model.account.AccountIdentifier;
import com.example.sampleaccount.domain.model.account.AccountMailAddress;
import com.example.sampleaccount.domain.model.account.AccountPassword;

public class ResetPasswordRequest {
    String identifier;
    String mailAddress;
    String password;

    public String getIdentifier() {
        return identifier;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Account toAccount() {
        return new Account(
                new AccountIdentifier(identifier),
                new AccountMailAddress(mailAddress),
                new AccountPassword(password)
        );
    }
}
