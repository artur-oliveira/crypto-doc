package org.softart.cryptodoc.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.softart.cryptodoc.components.EncryptUtils;
import org.softart.cryptodoc.components.auth.Auth;
import org.softart.cryptodoc.exceptions.encrypt.ValidateEncryptionKeyException;
import org.softart.cryptodoc.models.auth.User;
import org.softart.cryptodoc.models.encrypt.EncryptKey;
import org.softart.cryptodoc.services.encryption.ServerKeyService;
import org.softart.cryptodoc.services.encryption.UserPublicKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/public-key")
@RequiredArgsConstructor
@Getter
public class PublicKeyController {

    private final UserPublicKeyService userPublicKeyService;
    private final ServerKeyService serverKeyService;

    @GetMapping
    public ResponseEntity<EncryptKey> getPublicKey() throws GeneralSecurityException {
        PublicKey userPublicKey = getUserPublicKeyService().getUserPublicKey(Auth.getUser());

        String publicKeyAsString = EncryptUtils.keyToString(getServerKeyService().getRsaPublicKey());
        String publicKeyEncryped = EncryptUtils.encryptAesAsString(publicKeyAsString, serverKeyService.getAesKey(), getServerKeyService().getIvParameterSpec());
        String aesKeyEncrypted = EncryptUtils.encryptRsaAsString(EncryptUtils.keyToString(getServerKeyService().getAesKey()), userPublicKey);
        String encryptedIvSpec = EncryptUtils.encryptRsaAsString(EncryptUtils.ivParameterSpecToString(getServerKeyService().getIvParameterSpec()), userPublicKey);

        return ResponseEntity.ok(EncryptKey
                .builder()
                .publicKey(publicKeyEncryped)
                .aesKey(aesKeyEncrypted)
                .ivSpec(encryptedIvSpec)
                .build());
    }

    @PostMapping
    public ResponseEntity<Object> validate(@RequestBody EncryptKey encryptKey) {
        String currentAes = EncryptUtils.keyToString(getServerKeyService().getAesKey());
        String currentIvSpec = EncryptUtils.ivParameterSpecToString(getServerKeyService().getIvParameterSpec());
        String currentPubKey = EncryptUtils.keyToString(getServerKeyService().getRsaPublicKey());

        if (!(Objects.equals(encryptKey.getAesKey(), currentAes) &&
                Objects.equals(encryptKey.getIvSpec(), currentIvSpec) &&
                Objects.equals(encryptKey.getPublicKey(), currentPubKey))) {
            throw new ValidateEncryptionKeyException();
        }
        return ResponseEntity.noContent().build();
    }
}
