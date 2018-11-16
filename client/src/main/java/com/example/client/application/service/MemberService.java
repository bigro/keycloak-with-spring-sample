package com.example.client.application.service;

import com.example.client.domain.model.account.*;
import com.example.client.domain.model.member.NickName;
import com.example.client.presentation.controller.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class MemberService {
    private final RestTemplate restTemplate;

    public MemberService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MemberProfileRegisterStatus registerProfile(AccountIdentifier accountIdentifier, NickName nickName) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountIdentifier", accountIdentifier.value());
        map.put("nickName", nickName.value());

        ResponseEntity<MemberProfileRegisterResponse> response = restTemplate.exchange(
                "http://localhost:8110/member/profile/register",
                HttpMethod.POST,
                new HttpEntity<>(map),
                MemberProfileRegisterResponse.class);

        return response.getBody().getStatus();
    }
}
