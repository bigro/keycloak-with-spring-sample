package com.example.client.infrastructure.security.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OAuth2TokenService {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2TokenService.class);

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate;
    private final OAuth2ClientProperties.Provider provider;
    private final OAuth2ClientProperties.Registration registration;

    public OAuth2TokenService(OAuth2AuthorizedClientService authorizedClientService,
                              RestTemplate restTemplate,
                              OAuth2ClientProperties properties) {
        this.authorizedClientService = authorizedClientService;
        this.restTemplate = restTemplate;
        this.provider = properties.getProvider().get("todo");
        this.registration = properties.getRegistration().get("todo");
    }

    /**
     * アクセストークンの値を取得する。
     */
    public String getAccessTokenValue() {
        OAuth2AccessToken accessToken = getAuthorizedClient().getAccessToken();
        // アクセストークンが期限切れだったらリフレッシュ
        if (isExpiredToken(accessToken)) {
            logger.debug("Access token was expired!");
            refresh();
            accessToken = getAuthorizedClient().getAccessToken();
        }
        String tokenValue = accessToken.getTokenValue();
        logger.debug("access_token = {}", tokenValue);
        return tokenValue;
    }

    /**
     * リフレッシュトークンの値を取得する。
     */
    public String getRefreshTokenValue() {
        OAuth2RefreshToken refreshToken = getAuthorizedClient().getRefreshToken();
        String tokenValue = refreshToken.getTokenValue();
        return tokenValue;
    }

    /**
     * アクセストークンが期限切れならばtrueを返す。
     */
    private boolean isExpiredToken(OAuth2AccessToken accessToken) {
        return accessToken.getExpiresAt().isBefore(Instant.now());
    }

    /**
     * リフレッシュトークンでアクセストークンを再取得する。
     */
    private void refresh() {
        // POSTするリクエストパラメーターを作成
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("grant_type", "refresh_token");
        formParams.add("refresh_token", getRefreshTokenValue());

        // リクエストヘッダーを作成
        HttpHeaders httpHeaders = new HttpHeaders();
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();
        String authHeaderValue = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + authHeaderValue);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        // リクエストを作成
        RequestEntity<MultiValueMap<String, String>> requestEntity =
                new RequestEntity<>(formParams, httpHeaders, HttpMethod.POST, URI.create(provider.getTokenUri()));

        // POSTリクエスト送信（リフレッシュトークン取得）
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                requestEntity, new ParameterizedTypeReference<Map<String, String>>() {});
        Map<String, String> responseJson = responseEntity.getBody();

        // ログ出力
        logger.debug("{}", responseEntity.getStatusCode());
        logger.debug("{}", responseJson);

        // 既存のトークンを削除
        OAuth2AuthenticationToken authentication = getAuthentication();
        authorizedClientService.removeAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        // 新しいトークンを登録
        OAuth2AuthorizedClient authorizedClient = createAuthorizedClient(authentication, responseJson);
        authorizedClientService.saveAuthorizedClient(authorizedClient, authentication);
    }

    /**
     * OAuth2AuthorizedClientを新規作成する。
     */
    private OAuth2AuthorizedClient createAuthorizedClient(OAuth2AuthenticationToken authentication, Map<String, String> responseJson) {
        OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
                createClientRegistration(authentication),
                authentication.getName(),
                new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        responseJson.get("access_token"),
                        Instant.now(),
                        Instant.now().plus(Integer.parseInt(responseJson.get("expires_in")), ChronoUnit.SECONDS),
                        Arrays.stream(responseJson.get("scope").split("\\S")).collect(Collectors.toSet())
                ),
                new OAuth2RefreshToken(
                        responseJson.get("refresh_token"),
                        Instant.now()
                )
        );
        return authorizedClient;
    }

    /**
     * ClientRegistrationを作成する。
     */
    private ClientRegistration createClientRegistration(OAuth2AuthenticationToken authentication) {
        return ClientRegistration
                .withRegistrationId(authentication.getAuthorizedClientRegistrationId())
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(registration.getClientAuthenticationMethod()))
                .authorizationGrantType(new AuthorizationGrantType(registration.getAuthorizationGrantType()))
                .redirectUriTemplate(registration.getRedirectUri())
                .scope(registration.getScope())
                .authorizationUri(provider.getAuthorizationUri())
                .tokenUri(provider.getTokenUri())
                .userInfoUri(provider.getUserInfoUri())
                .userNameAttributeName(provider.getUserNameAttribute())
                .jwkSetUri(provider.getJwkSetUri())
                .clientName(registration.getClientName())
                .build();
    }

    /**
     * OAuth2AuthorizedClientを取得する。
     */
    private OAuth2AuthorizedClient getAuthorizedClient() {
        // OAuth2AuthenticationTokenはAuthenticationインタフェース実装クラス
        OAuth2AuthenticationToken authentication = getAuthentication();
        // OAuth2AuthorizedClientを取得
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        return authorizedClient;
    }

    /**
     * OAuth2AuthenticationTokenを取得する。
     */
    private OAuth2AuthenticationToken getAuthentication() {
        OAuth2AuthenticationToken authentication =
                (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}