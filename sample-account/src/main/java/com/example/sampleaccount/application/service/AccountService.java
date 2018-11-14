package com.example.sampleaccount.application.service;

import com.example.sampleaccount.domain.model.account.AccountMailAddress;
import com.example.sampleaccount.domain.model.account.AccountIdentifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    public AccountIdentifier register(AccountMailAddress mailAddress) {
        return new AccountIdentifier(UUID.randomUUID().toString());
    }
}
