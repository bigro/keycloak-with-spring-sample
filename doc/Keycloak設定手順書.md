# Keycloak設定手順書
## REALM作成
1. 左上の [Master] をクリックし、Add realmをクリック

![2018-11-07 19 50 40](https://user-images.githubusercontent.com/21081829/48127815-fca76c00-e2c7-11e8-90a1-04af5b30afc2.png)

2. Nameに任意のREALM名を入力してcreate

![2018-11-07 19 51 31](https://user-images.githubusercontent.com/21081829/48127894-31b3be80-e2c8-11e8-9994-4314f1a856ac.png)

## Client作成
1. 左のメニューから [Clients] をクリックし、表の右上の [Create] をクリック

![2018-11-07 20 12 53](https://user-images.githubusercontent.com/21081829/48128350-8d327c00-e2c9-11e8-8b85-667aace8755e.png)

2. [Client ID] に任意のクライアントID、 [Cleint Protocol] に `openid-connect` 、 [Root URL] にクライアントアプリのルートURLを入力する

![2018-11-07 20 08 28](https://user-images.githubusercontent.com/21081829/48128446-d4b90800-e2c9-11e8-9fa2-8321f2a99269.png)

3. [Settings] タブで [Access Type] を `confidential` にして [Save] する

![2018-11-07 20 28 24](https://user-images.githubusercontent.com/21081829/48129102-e4d1e700-e2cb-11e8-91be-fbd51b67d3b2.png)
