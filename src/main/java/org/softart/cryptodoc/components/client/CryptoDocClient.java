package org.softart.cryptodoc.components.client;

import lombok.Data;
import org.softart.cryptodoc.configuration.properties.CryptoDocClientProperties;
import org.softart.cryptodoc.exceptions.client.ClientNotAuthenticatedException;
import org.softart.cryptodoc.models.auth.request.LoginRequest;
import org.softart.cryptodoc.models.auth.response.JwtResponse;
import org.softart.cryptodoc.models.document.Document;
import org.softart.cryptodoc.models.document.request.SecureDocumentUploadRequest;
import org.softart.cryptodoc.models.encrypt.EncryptKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;

@Component
@Data
public class CryptoDocClient {
    private final CryptoDocClientProperties properties;
    private final RestTemplate restTemplate;
    private final JwtResponse jwt;

    @Autowired
    public CryptoDocClient(CryptoDocClientProperties properties, RestTemplate restTemplate) {
        this(properties, restTemplate, null);
    }

    public CryptoDocClient(
            CryptoDocClientProperties properties,
            RestTemplate restTemplate,
            JwtResponse jwt
    ) {
        this.properties = properties;
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public CryptoDocClient authenticatedClient(String userName, String login) {
        JwtResponse response = getRestTemplate().exchange(getURI("/api/v1/auth/token"), HttpMethod.POST, new HttpEntity<>(new LoginRequest(userName, login), baseHeaders()), JwtResponse.class).getBody();
        return new CryptoDocClient(getProperties(), getRestTemplate(), response);
    }

    public EncryptKey getKeys() {
        return getRestTemplate().exchange(getURI("/api/v1/public-key"), HttpMethod.GET, new HttpEntity<>(null, authenticatedHeaders()), EncryptKey.class).getBody();
    }

    public void validateKeys(EncryptKey encryptKey) {
        getRestTemplate().exchange(getURI("/api/v1/public-key"), HttpMethod.POST, new HttpEntity<>(encryptKey, authenticatedHeaders()), Void.class);
    }

    public Document uploadSecureDocument(SecureDocumentUploadRequest upload) {
        return getRestTemplate().exchange(getURI("/api/v1/document"), HttpMethod.POST, new HttpEntity<>(upload, authenticatedHeaders()), Document.class).getBody();
    }

    public Document getDocument(Long documentId) {
        return getRestTemplate().exchange(getURI("/api/v1/document/" + documentId), HttpMethod.GET, new HttpEntity<>(null, authenticatedHeaders()), Document.class).getBody();
    }

    HttpHeaders baseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    HttpHeaders authenticatedHeaders() {
        HttpHeaders headers = baseHeaders();
        if (Objects.isNull(getJwt()) || Objects.isNull(getJwt().getAccessToken())) {
            throw new ClientNotAuthenticatedException();
        }
        headers.setBearerAuth(getJwt().getAccessToken());
        return headers;
    }

    URI getURI(String path) {
        return URI.create(getHost() + path);
    }

    String getHost() {
        return properties.getHost();
    }
}
