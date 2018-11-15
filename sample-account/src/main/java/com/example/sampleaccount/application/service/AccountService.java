package com.example.sampleaccount.application.service;

import com.example.sampleaccount.domain.model.account.AccountMailAddress;
import com.example.sampleaccount.domain.model.account.AccountIdentifier;
import com.example.sampleaccount.domain.model.account.AccountRepository;
import com.example.sampleaccount.domain.model.account.ConfirmationCode;
import com.example.sampleaccount.infrastructure.mail.ConfirmationCodeMail;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    AccountRepository repository;
        ConfirmationCodeMail confirmationCodeMail;

    public AccountService(AccountRepository repository, ConfirmationCodeMail confirmationCodeMail) {
        this.repository = repository;
        this.confirmationCodeMail = confirmationCodeMail;
    }

    public AccountIdentifier register(AccountMailAddress mailAddress) {
        AccountIdentifier identifier = newIdentifier();
        ConfirmationCode confirmationCode = ConfirmationCode.issue();
        repository.register(identifier, mailAddress, confirmationCode);

        confirmationCodeMail.send(mailAddress, confirmationCode);
        return identifier;
    }

    public AccountIdentifier newIdentifier() {
        return new AccountIdentifier(UUID.randomUUID().toString());
    }
}
