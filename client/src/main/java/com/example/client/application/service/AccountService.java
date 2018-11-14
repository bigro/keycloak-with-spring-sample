package com.example.client.application.service;

import com.example.client.presentation.controller.AccountDto;
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


    public AccountDto register(String email) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);

        ResponseEntity<AccountDto> response = restTemplate.exchange(
                 "http://localhost:8100/account/register",
                HttpMethod.POST,
                new HttpEntity<>(map),
                AccountDto.class);
        return response.getBody();
    }
}
