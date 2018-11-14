package com.example.sampleaccount.presentation.controller;

import com.example.sampleaccount.domain.model.account.AccountIdentifier;

public class AccountRegisterResponse {
    String accountIdentifier;

    public AccountRegisterResponse(AccountIdentifier identifier) {
        this.accountIdentifier = identifier.value();
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }
}
