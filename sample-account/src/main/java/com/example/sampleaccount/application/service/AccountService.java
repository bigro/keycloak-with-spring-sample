package com.example.sampleaccount.application.service;

import com.example.sampleaccount.domain.model.account.*;
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
        // TODO:すでにメールアドレスが登録されている場合を考慮する
        AccountIdentifier identifier = newIdentifier();
        ConfirmationCode confirmationCode = ConfirmationCode.issue();
        repository.register(identifier, mailAddress, confirmationCode);

        confirmationCodeMail.send(mailAddress, confirmationCode);
        return identifier;
    }

    public AccountIdentifier newIdentifier() {
        return new AccountIdentifier(UUID.randomUUID().toString());
    }

    public AuthenticationStatus auth(AuthenticationKey authenticationKey) {
        if (!repository.exists(authenticationKey)) {
            return AuthenticationStatus.FAIL;
        }
        
        return AuthenticationStatus.SUCCESS;
    }

    public void resetPassword(Account account) {
        repository.resetPassword(account);
    }
}
