package com.example.client.application.service;

import com.example.client.domain.model.account.Account;
import com.example.client.domain.model.account.AccountIdentifier;
import com.example.client.domain.model.account.AccountMailAddress;
import com.example.client.domain.model.account.ConfirmationCode;
import com.example.client.domain.model.auth.AuthenticationStatus;
import com.example.client.presentation.controller.AccountDto;
import com.example.client.presentation.controller.AuthenticationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class AccountService {
    private final RestTemplate restTemplate;

    public AccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Account register(String email) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);

        ResponseEntity<AccountDto> response = restTemplate.exchange(
                 "http://localhost:8100/account/register",
                HttpMethod.POST,
                new HttpEntity<>(map),
                AccountDto.class);


        AccountDto accountDto = response.getBody();

        AccountIdentifier identifier = new AccountIdentifier(accountDto.getAccountIdentifier());
        return new Account(identifier, new AccountMailAddress(email));
    }

    // TODO:「認証」ではないよな...
    // 登録の際にメールで送られてきた確認コードで確認？する（名前が思いつかない）
    public AuthenticationStatus auth(AccountIdentifier accountIdentifier, ConfirmationCode confirmationCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountIdentifier", accountIdentifier.value());
        map.put("confirmationCode", confirmationCode.value());

        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
                "http://localhost:8100/account/auth",
                HttpMethod.POST,
                new HttpEntity<>(map),
                AuthenticationResponse.class);
        return response.getBody().getStatus();
    }
}
