#!/usr/bin/env bash

KEYCLOAK_URL=http://localhost:9000
REALM=sample-app
CLIENT=sample-client

# 管理者トークン取得
ADMIN_TOKEN=$(curl -X POST ${KEYCLOAK_URL}/auth/realms/master/protocol/openid-connect/token \
   -H "Content-Type: application/x-www-form-urlencoded" \
   -d "username=admin" -d "password=admin" -d "grant_type=password" -d "client_id=admin-cli" | jq -r .access_token)

# REALM作成
curl -v -X POST ${KEYCLOAK_URL}/auth/admin/realms/ \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    -H "Content-Type: application/json"  \
    --data '{
         "realm":"'${REALM}'",
         "enabled":true,
         "registrationAllowed":true,
         "registrationEmailAsUsername":true,
         "resetPasswordAllowed":true,
         "verifyEmail":true,
         "smtpServer":{
           "host":"mailhog",
           "from":"test@example.com"
         }
     }'

# client登録
curl -v -X POST ${KEYCLOAK_URL}/auth/admin/realms/sample-app/clients \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    -H "Content-Type: application/json" \
    --data '{
        "enabled":true,
        "publicClient":false,
        "attributes":{},
        "redirectUris":[],
        "clientId":"'${CLIENT}'"
        ,"protocol":"openid-connect",
        "rootUrl":"http://localhost:8080",
        "baseUrl":"http://localhost:8080/contents"
    }'