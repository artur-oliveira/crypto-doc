package org.softart.cryptodoc.services.encryption;

import org.softart.cryptodoc.models.auth.User;

import java.security.GeneralSecurityException;
import java.security.PublicKey;

public interface UserPublicKeyService {
    PublicKey getUserPublicKey(User user) throws GeneralSecurityException;

    void saveUserPublicKey(User user, PublicKey publicKey);
}
