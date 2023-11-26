# Crypto Doc

Serviço de armazenamento de documentos utilizando criptografia AES / RSA

# Casos de Uso

## Cadastro

No momento em que o cliente cadastrar-se no serviço, será retornada uma chave privada e uma chave pública.

A chave privada deve ser armazenada pelo cliente, pois ela será utilizada para descriptografar
as chaves de descriptografia dos documentos. Em caso de perca da chave privada, não será possível recuperar
o conteúdo dos documentos.

O serviço armazena a chave pública do cliente para criptografar as chaves de criptografia.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/auth/register' \
--data-raw '{
    "user_name": "teste",
    "email": "teste@gmail.com",
    "password": "teste1234",
    "first_name": "Teste",
    "last_name": "Testadinho"
}'


```

#### Response

```json
{
  "private_key": "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDre8FJcOwsjTwscI+ExjvHuMKnfaF9Qqq4uy8DxlhC7mwqn4Eqr4LP3jjzey58R0lrVuvnnfkUI+hbQh9Pi4pW6BI8Cj93VefhFhhxr6wX/DHIJHKFmXjZiMy+9n9rSad4+Sf7LJ6zEjFQt3pThvU9Krhe1izS1+59Ej2FoOXO6PsCOpTJto7HefVaDpST3PJ1584zH1dcoE4g+5eq5+SH3onQETTph11AsaFCGldt+1zngQdE/ynHqYzeHsGCFWvgWA11Aj5nCLUHG0+7SNl355FXMIPsuWCxr/oBnuJZITm/ouppH1DANyKerEZfwkhvVTjWxCJak7203lmV6Ue/AgMBAAECggEAH0ae47fey7AFJ6CE6ffrkAQ8aO3Trq+rExaxZjPyxw5O9Cjz3o0pt51mklga/XX5DLZNEMSM2h2cH9AcGSmJudngNMnmUgg+ivOvUsd9TkDjBRe/f3pkcFKuDEhFoM9LMf24T1ucHGE0humc5/kgcKZQOdsCYGKTkgMD2XqxlcBfThpu5EaDvXusNRDXjneQVvVduO5rLkBQObAq6txvU8bHkAJFwWWYTEVhiwlu3wtVIAWVI4oFBd92gLe1T81UxK77E5StffpbanVqltkrBquJPBIPkGhSM01CP+zE9T+G/400gbN6h/xONCyQvJFMu3awcqXQjQE3opIUBT9FmQKBgQD9ETBMoycal2LaUeRn0ibCP4vWU+zeru+JBmRxl7UvmhRRTeBmviF0/vOOiwYo+y6fl1MR4X6/3t2GuuIsSLUiT2Of20VA2EJmrao7olsBf9g/wFr4sV2Gp6x7r8lWg3LFIeOQafz3gJC6FbDyoIEvHzV8afPG2YeKzrscPLrVcwKBgQDuNmXs7+xNg09qnW6dXGX2OAPQePOqpaWZd96RwZzGjKc7UY9jGFlqrWUU/LHLtnKy4bE+L6znwDcx8cMCHeC8e6Oz9rPkAAybAjv7KL/UWK/JNDswH6DAtUIfM0QiOHei/BI6oMaFk5A6JnP950Bu56egJsoMRHBr2B4Bu7VRhQKBgCIniLo/Js7q8Zd/h/EPJtX+l5PkncpsF74Jk9LU48o55FYYyZl0PbW5lwt87eboB6HYXYjDQ0UIAiPl4xKQ+YlY55wyjsUyqruK4rfLsLaiF4B7KIv5/CJpTNkgn27CrOG4MitScqbAvkevF8OXL/g5IRHahuEmiKmOPyiPdbPLAoGAdYuMQh2l+h3Ii3CCC85zVcsQPsfgXR1+GLuorT3fsa3oO4Ikgh6ayOQlE/UZes9dy91Ii8oUxQr5cYC93IaHxhowjnY3EFPh5H92v5m9Bvh4TY2jgzyoI0+OAD1Y6pX4mwlNqXZ6X6GQMtGUvP5mshNHiOR7IRCIdP7g17QbZc0CgYBLNqqozb8QtxdiLLarH6rQDAJXiIV/WAPFWil0OGu5k4Vw8R5/SWiWPfIV2OmLQahjaZo+tx3nhLNCC0em1rtdYbc583lSGphdretnPtxdm+vqf5uN0nCeRjYYLP0Xx9X1NqgRCnvGDvqQzy5qre/DzHjDBRrdvSp9oUGyWO9ipQ==",
  "public_key": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA63vBSXDsLI08LHCPhMY7x7jCp32hfUKquLsvA8ZYQu5sKp+BKq+Cz94483sufEdJa1br5535FCPoW0IfT4uKVugSPAo/d1Xn4RYYca+sF/wxyCRyhZl42YjMvvZ/a0mnePkn+yyesxIxULd6U4b1PSq4XtYs0tfufRI9haDlzuj7AjqUybaOx3n1Wg6Uk9zydefOMx9XXKBOIPuXqufkh96J0BE06YddQLGhQhpXbftc54EHRP8px6mM3h7BghVr4FgNdQI+Zwi1BxtPu0jZd+eRVzCD7Llgsa/6AZ7iWSE5v6LqaR9QwDcinqxGX8JIb1U41sQiWpO9tN5ZlelHvwIDAQAB"
}
```

## Autenticação

Existem duas maneiras de se autenticar no serviço, utilizando o email/usuário e senha ou utilizando o token de renovação
JWT.

### Email/Usuário e Senha

Se o cliente não possuir um refresh token, ele deve autenticar-se utilizando seu login e senha.

Será retornado um token de acesso e um token de renovação.

O token de acesso possui validade durante o dia vigente até às 23:59:59.
Enquanto o token de renovação possui validade do dia vigente acrescido de um ano, às 23:59:59.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/auth/token' \
--header 'Content-Type: application/json' \
--data '{
    "login": "teste",
    "password": "teste1234"
}'
```

#### Response

```json
{
  "access_token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ0ZXN0ZSIsImlzcyI6IjIiLCJzdWIiOiJUZXN0ZSBUZXN0YWRpbmhvIiwiaWF0IjoxNzAxMDI3NzU1LCJleHAiOjE3MDEwNDMxOTksInR5cGUiOiJhY2Nlc3MifQ.9gLaN0U7fzsB67Uuydt5r0-wA9AAb0DUajPJ6MF7yeUy6YWBhXPhqAEtvdsEPfKsZ4ycZFdxHRgfPIgVyCKZHA",
  "refresh_token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ0ZXN0ZSIsImlzcyI6IjIiLCJzdWIiOiJUZXN0ZSBUZXN0YWRpbmhvIiwiaWF0IjoxNzAxMDI3NzU1LCJleHAiOjE3MzI1NzkxOTksInR5cGUiOiJyZWZyZXNoIn0.XL2af5M92HqVsGt0uRyR5zigbohcBpayk8HBWYi_1uaBmxWj988Bii1y2cwkBRkMQiMXZ8qhNJvKexImzroIKw"
}
```

### Token de renovação

Ao cliente autenticar-se utilizando o token de renovação, será retornado apenas um novo token de acesso. Com as mesmas
especificações explicadas acima.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/auth/token/refresh' \
--header 'Content-Type: application/json' \
--data '{
    "refresh_token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ0ZXN0ZSIsImlzcyI6IjIiLCJzdWIiOiJUZXN0ZSBUZXN0YWRpbmhvIiwiaWF0IjoxNzAxMDI3NzU1LCJleHAiOjE3MzI1NzkxOTksInR5cGUiOiJyZWZyZXNoIn0.XL2af5M92HqVsGt0uRyR5zigbohcBpayk8HBWYi_1uaBmxWj988Bii1y2cwkBRkMQiMXZ8qhNJvKexImzroIKw"
}'
```

#### Response

```json
{
  "refresh_token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJ0ZXN0ZSIsImlzcyI6IjIiLCJzdWIiOiJUZXN0ZSBUZXN0YWRpbmhvIiwiaWF0IjoxNzAxMDI3NzU1LCJleHAiOjE3MzI1NzkxOTksInR5cGUiOiJyZWZyZXNoIn0.XL2af5M92HqVsGt0uRyR5zigbohcBpayk8HBWYi_1uaBmxWj988Bii1y2cwkBRkMQiMXZ8qhNJvKexImzroIKw"
}
```

## Chave pública

Estas chaves servem para criptografar o conteúdo durante o upload de documentos dos clientes

Para obter as chaves de criptografia o cliente deve realizar uma requisição conforme abaixo.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/public-key' \
--header 'Authorization: Bearer {{access_token}}'
```

#### Response

```json
{
  "public_key": "lsMoAKot/k1Lh4DI7OT+TKkPy5B3/mqZfLeBoQIHpCavs/yTSOBmQDmJxo5nRYKrqx7D/1UcY8oWFcf7x6xEpLQ82SafNp3ZZVPCfRqOb9m+I8jfqutNoASqYcvJqrAJReu9g1lQBzunYS+tVrNwHFtUm/WUfQKxC7kBJQBVHJ820mjsTP+hN3BlFcFYrUKv6xBCxri84w13R3Xpq19OSGy5waKljovIJ8bjYkITVN0qnDLgT8sW9kImpvp48oQppGjhf6g8sGJsc88umXCcduq0jOYttW/Q/HrZhM0iBgmaXQGYSoXybtjwI7ofbcDyWBOcmhMWwnLfiSNjOkaHVXEqHmZKga9h1wj551rY9ZEucJEfFAbcvsHL5bVnl1yxc7qf+HvLYt+ttNWXnAomaYUCfrKF5vYy05mNd9c0qmXNkeJW5dgloZJhRcMBfcVH0eiid8hGcuFEbK1qnXbKn3FgbFKlYEqTus+fQ88LWrCztWBI0zLk59g/Oqfb66mVKjrZe5v1bBzIeVy2Oqty7Q==",
  "aes_key": "5YqZu3JX+sWIIXq5tYoSAsqhR4KSOhOSBNzbD2KkNMPSD5h7lyfzcgHZ3YqfOQufnDgdDcfouM8SO2g7j5xX51N3hTxd4T0HNrHKARvOAjQ9gED3h+KPRxSTVUVJ8P3zycfnXdKCKz+nF0Kr3GfjRBgnKRu8r0PZH78h2+oqLrpubW3CX3q/lnOWEa1gnNCV4/7lLJD0K1lgkN3Vn6No0dA/z90GVXoPcpJOw0u1oBmV3Yuylb0eTW6smVKUxkowV6kHZbdYv7Zf1TUh8JY0ImcL2d0ZpzRH2VH1QKGrd3/eAwSJ4GVyECdBMGrozYlmOKXqp4fVaNVM7QehifdS8w==",
  "iv_spec": "Pecjqt417fQQahLvJUO+bV/Dds1EAgTrR4mYsq+znJXP6POH3OlJKmy2P7TeICdNigqwZ6/2Ls5v06H01rhU4jNGZFhBz7AL5tHm0bH0tOywyA+lf0Jw4OWFN7H0dDqy62Pw/1tB1SiJ/SHYo9A+tXcp2m6lbJL4ksKtmQd97HuW4gCWXLKxzj3wNs2cj3xrETFIpuGLgO/bZ3t7PISeO3XgpVjcnVlQYzBJtuOHucJSUJ5VpULjoyC3AgQNNftsU99eptctcYoMjoKypVXvM5Mf4Uhx/BYaTi4ieLWPogvQx1+gvttTDA+d/vikq/d9zinS9JndLnBdD2RQW/irlw=="
}
```

A chave pública é rotacionada a cada hora, por isso o cliente deve realizar a requisição a cada hora para obter a chave.

Além disso, a chave pública é criptografada utilizando AES. E as chaves de criptografia são criptografadas utilizando
RSA, utilizando a chave pública do cliente.

É de responsabilidade do cliente descriptografar as chaves de criptografia utilizando sua chave privada. E em seguida
descriptografar a chave pública utilizando a chave AES obtidas.

## Validação de chaves

Antes de realizar o upload de um documento, o cliente deve validar se a chave pública que ele possui é a mesma que
corresponde
ao servidor, devido à rotação de chaves.

Para realizar a validação, o cliente deve enviar a chave pública, a chave AES e o IV Spec para o servidor. Todos
descriptografados.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/public-key' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{access_token}}' \
--data '{
    "public_key": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsvZJG6hdxmbhV31u5JX8DfFkzrbMA+6vTCe5B+tvEjLxJY7TfhxvF4hTgIVYGY7dWHRu2Xs/2q9Ro9jPWSUJwkEsDOxiBMODnWIqEbgVj4QJJzVE3WgcrFL4PM50mRGZ8hnWa6Gj/1ao1+u87VYnOkz7A0y79o5RpyPcoAxpbb6soPQPewnITLmZ4c5AtuaLCEo8qOi3SnF3sGSN4LVZMd39x203Keyl/SdcdddvWLNq5/dDd1noUjGtuU16lUr7v7hZPA8hUovyvDojxSO9+pPrK+ndiWBYiXbWo44pdFNaGHk3tRXWdv7Ll1tuTMleyGlPfuvhuTxt81QXuFsEKwIDAQAB",
    "aes_key": "Em/ep6AQtZTnE/p9R7D23ziIwgGBDQHhWHjko8UqHRM=",
    "iv_spec": "IxUH253tISBPYk84DimtAQ=="
}'
```

Se as chaves estiverem corretas, será retornado ao cliente **HTTP 204 - No Content**, caso contrário, será retornado *
*HTTP 400 - Bad Request**

## Upload de documento

Para realizar o upload de um documento, o cliente deve enviar o documento criptografado com a chave AES que foi obtida
anteriormente.

Em seguida, criptografando esta chave AES com a chave pública do servidor.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/document' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{access_token}}' \
--data '{
    "name": "nome_do_documento.txt",
    "encrypted_document": "{{documento_criptografado}}",
    "aes_key": "{{chave_aes_criptografada}}",
    "iv_spec": "{{iv_spec_criptografado}}"
}'
```

#### Response

```json
{
  "id": 1,
  "name": "nome_do_documento.txt"
}
```

## Listagem de documentos

A listagem de documentos corresponde aos documentos que o cliente possui salvos no serviço.

Listar os documentos apenas retorna seu identificador e seu nome, desta forma, **não** retornando o seu conteúdo.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/document?size=1&page=0' \
--header 'Authorization: Bearer {{access_token}}'
```

#### Response

```json
{
  "content": [
    {
      "id": 1,
      "name": "senhas_c56c7eae-649b-468d-94e6-3cd4fe4e498d.txt"
    }
  ],
  "pageable": {
    "page_number": 0,
    "page_size": 1,
    "sort": {
      "sorted": false,
      "empty": true,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "total_pages": 1,
  "total_elements": 1,
  "last": true,
  "size": 1,
  "number": 0,
  "sort": {
    "sorted": false,
    "empty": true,
    "unsorted": true
  },
  "number_of_elements": 1,
  "first": true,
  "empty": false
}
```

## Detalhar documento

Detalhar documento retorna o documento com seu conteúdo criptografado, ou não, dependendo da forma de consumo.

### Conteúdo Criptografado

Por padrão, o serviço retorna o documento criptografado para o usuário no momento em que o mesmo detalha.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/document/1' \
--header 'Authorization: Bearer {{access_token}}'``
```

#### Response

```json
{
  "id": 1,
  "name": "senhas_c56c7eae-649b-468d-94e6-3cd4fe4e498d.txt",
  "content_data": "fHAyCHCWW0YYw0e8d3h7Zg==",
  "content_aes_key": "DYmkf6atMHagsSAzxpZBJMhIlFI4Z7+dnsXmoZJl1HkWCupFEnaNqDh0nZI9b5PNQmjiBKFcwYeNx4T4oh94RU5C9txzLPaXfp7+zV9ZVuPKMrG6ECs3Lk5NW4gAsP/II52jfLzqvQE/Fgq3BSUhrZ2umLnSHdIeSaNw0eQDc3j0KDqNLI5goGoe5pTw1igOKRHwU9WCekNa6+lC9AEw/8ut792BCnzaklK+drmbLd8WWYI47Hs0uw+2fIMu35Bkx66+gPq77FiLPRUEuYsYCZHq8gWjIuDU2eQCTRKMzuahTnlVKk99khH2lX9QfuZAcv/x5X86lFawjFLLKmj9Vg==",
  "content_iv_spec": "QgUP5IOKA3qBsZrvjXJTSbH9vcnjWXxPnMKkU10l42VY4Dpj1c3rr7NkH4c26yBJOKlCP3dWIlqXDBiw6CP5lZ+DhuAn1JYRc+c0MQGC14AZrILgZaewGLH6ltR/gPJJbuUhAmSeo5UrSiSnf4ZXE2N68qc1WGlQs32SgV3JD2Kf/BnBvALmDZiOnOrlQc2r2Ym9PcBb4qzs3g/L7u0G9TaGwOLfOSk26Cp+DqZOgGLcXYh539pDV6Q0kHhcCT6BC2TJkwo802JZwSz/l2AGIh4YvtuGdUeirJScU/NSf9NtUcM3M25dkyeR3hWV/UinLZOEHy4lJhUGpiII7RnHwA=="
}
```

O processo de descriptografia é o mesmo utilizado para validar as chaves.
O cliente deve descriptografar a chave AES utilizando sua chave privada.
E em seguida descriptografar o conteúdo utilizando a chave AES obtida.

### Conteúdo Descriptografado

Em contrapartida, o serviço também retorna o documento descriptografado, caso o cliente deseje, embora não recomendado
por questões de segurança.

Para isso, base adicionar um parâmetro de query na requisição.

#### Request

```shell
curl --location 'https://crypto-doc.eelixo.com/api/v1/document/1?secure=false' \
--header 'Authorization: Bearer {{access_token}}'``
```

#### Response

```json
{
  "id": 1,
  "name": "senhas_c56c7eae-649b-468d-94e6-3cd4fe4e498d.txt",
  "content_data": "dGVzdGUxMjM0"
}
```

O conteúdo retornado, tanto sem criptografia como com criptografia, é sempre codificado em base64.