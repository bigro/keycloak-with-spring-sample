package com.example.sampleaccount.presentation.controller;

import com.example.sampleaccount.application.service.AccountService;
import com.example.sampleaccount.domain.model.account.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("entry")
    AccountRegisterResponse entry(@RequestBody AccountRegisterRequest request) {
        AccountIdentifier identifier = accountService.register(new AccountMailAddress(request.email()));
        return new AccountRegisterResponse(identifier);
    }

    @PostMapping("auth")
    AccountAuthenticationResponse auth(@RequestBody AccountAuthenticationRequest request) {
        AuthenticationKey authenticationKey = request.toKey();
        AuthenticationStatus status = accountService.auth(authenticationKey);
        return new AccountAuthenticationResponse(status.name());
    }

    @PostMapping("reset-password")
    AccountAuthenticationResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        Account account = request.toAccount();
        accountService.resetPassword(account);
        return AccountAuthenticationResponse.success();
    }
}
