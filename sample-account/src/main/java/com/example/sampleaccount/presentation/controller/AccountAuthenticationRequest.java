package com.example.sampleaccount.presentation.controller;

import com.example.sampleaccount.domain.model.account.AccountIdentifier;
import com.example.sampleaccount.domain.model.account.AuthenticationKey;
import com.example.sampleaccount.domain.model.account.ConfirmationCode;

public class AccountAuthenticationRequest {
    String accountIdentifier;
    String confirmationCode;

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public AuthenticationKey toKey() {
        return new AuthenticationKey(new AccountIdentifier(this.accountIdentifier), new ConfirmationCode(this.confirmationCode));
    }
}
