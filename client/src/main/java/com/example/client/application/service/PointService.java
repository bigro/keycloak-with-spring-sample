package com.example.client.application.service;

import com.example.client.infrastructure.security.oauth.OAuth2TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PointService {

    private final RestTemplate restTemplate;
    private final OAuth2TokenService oAuth2TokenService;
    private final String resourceServerUri;

    public PointService(RestTemplate restTemplate,
                           OAuth2TokenService oAuth2TokenService,
                           @Value("${resource-server.uri}") String resourceServerUri) {
        this.restTemplate = restTemplate;
        this.oAuth2TokenService = oAuth2TokenService;
        this.resourceServerUri = resourceServerUri;
    }

    public int get() {
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(
                resourceServerUri + "/point", HttpMethod.GET, createHttpEntity(),
                new ParameterizedTypeReference<Integer>() {});
        return responseEntity.getBody();
    }

    private HttpEntity<?> createHttpEntity(Object requestBody) {
        // リクエストヘッダーにアクセストークンを設定
        HttpHeaders httpHeaders = new HttpHeaders();
        // "Authorization: Bearer <ACCESS_TOKEN>"というヘッダーを追加
        httpHeaders.add(HttpHeaders.AUTHORIZATION,
                "Bearer " + oAuth2TokenService.getAccessTokenValue());
        // HttpEntityを作成
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        return httpEntity;
    }

    private HttpEntity<?> createHttpEntity() {
        return createHttpEntity(null);
    }
}
