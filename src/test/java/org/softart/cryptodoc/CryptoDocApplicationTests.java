package org.softart.cryptodoc;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.softart.cryptodoc.components.Base64Utils;
import org.softart.cryptodoc.components.EncryptUtils;
import org.softart.cryptodoc.components.client.CryptoDocClient;
import org.softart.cryptodoc.models.document.Document;
import org.softart.cryptodoc.models.document.request.SecureDocumentUploadRequest;
import org.softart.cryptodoc.models.encrypt.EncryptKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

@SpringBootTest
class CryptoDocApplicationTests {

    @Autowired
    CryptoDocClient baseClient;

    @SneakyThrows
    PrivateKey getTestPrivateKey() {
        String privateKeyText = Files.readString(Paths.get("/home/artur/priv.txt")).trim();
        return EncryptUtils.getPrivateKey(privateKeyText);
    }

    @Test
    @Order(1)
    void testValidatingKeys() throws Exception {
        CryptoDocClient newClient = baseClient.authenticatedClient("teste", "teste1234");
        EncryptKey key = newClient.getKeys();

        PrivateKey testClientPrivateKey = getTestPrivateKey();
        String aes = EncryptUtils.decryptRsa(key.getAesKey(), testClientPrivateKey);
        String iv = EncryptUtils.decryptRsa(key.getIvSpec(), testClientPrivateKey);
        String pub = EncryptUtils.decryptAes(key.getPublicKey(), EncryptUtils.getSecretKey(aes), EncryptUtils.ivParameterSpec(iv));

        newClient.validateKeys(EncryptKey
                .builder()
                .aesKey(aes)
                .ivSpec(iv)
                .publicKey(pub)
                .build());
    }

    @Test
    @Order(2)
    void testUploadingSecure() throws Exception {
        CryptoDocClient newClient = baseClient.authenticatedClient("teste", "teste1234");
        EncryptKey key = newClient.getKeys();
        PrivateKey testClientPrivateKey = getTestPrivateKey();
        SecretKey aes = EncryptUtils.getSecretKey(EncryptUtils.decryptRsa(key.getAesKey(), testClientPrivateKey));
        IvParameterSpec spec = EncryptUtils.ivParameterSpec(EncryptUtils.decryptRsa(key.getIvSpec(), testClientPrivateKey));
        PublicKey publicKey = EncryptUtils.getPublicKey(EncryptUtils.decryptAes(key.getPublicKey(), aes, spec));
        newClient.validateKeys(EncryptKey
                .builder()
                .aesKey(EncryptUtils.keyToString(aes))
                .ivSpec(EncryptUtils.ivParameterSpecToString(spec))
                .publicKey(EncryptUtils.keyToString(publicKey))
                .build());


        String doc = Base64Utils.encodeToString(Files.readString(Paths.get("/home/artur/Downloads/passwords.txt")).trim().getBytes(StandardCharsets.UTF_8));

        String encryptedDoc = EncryptUtils.encryptAesAsString(doc, aes, spec);
        String encryptedKey = EncryptUtils.encryptRsaAsString(EncryptUtils.keyToString(aes), publicKey);
        String encryptedIv = EncryptUtils.encryptRsaAsString(EncryptUtils.ivParameterSpecToString(spec), publicKey);

        Document docCrea = newClient.uploadSecureDocument(SecureDocumentUploadRequest
                .builder()
                .encryptedDocument(encryptedDoc)
                .documentName("senhas_" + UUID.randomUUID() + ".txt")
                .aesKey(encryptedKey)
                .ivSpec(encryptedIv)
                .build());

    }


    @Test
    @Order(3)
    void testGetDocumentSecure() throws Exception {
        CryptoDocClient newClient = baseClient.authenticatedClient("teste", "teste1234");
        EncryptKey key = newClient.getKeys();
        PrivateKey testClientPrivateKey = getTestPrivateKey();
        SecretKey aes = EncryptUtils.getSecretKey(EncryptUtils.decryptRsa(key.getAesKey(), testClientPrivateKey));
        IvParameterSpec spec = EncryptUtils.ivParameterSpec(EncryptUtils.decryptRsa(key.getIvSpec(), testClientPrivateKey));
        PublicKey publicKey = EncryptUtils.getPublicKey(EncryptUtils.decryptAes(key.getPublicKey(), aes, spec));
        newClient.validateKeys(EncryptKey
                .builder()
                .aesKey(EncryptUtils.keyToString(aes))
                .ivSpec(EncryptUtils.ivParameterSpecToString(spec))
                .publicKey(EncryptUtils.keyToString(publicKey))
                .build());

        Document document = newClient.getDocument(1L);
        Document.EncryptedContent content = document.getContent();

        String decryptedDoc = EncryptUtils.decryptAes(content.getData(), aes, spec);

        Assertions.assertEquals("teste1234", decryptedDoc);


    }

}
