package org.softart.cryptodoc.services.encryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface ServerKeyService {
    PublicKey getRsaPublicKey();

    PrivateKey getRsaPrivateKey();

    SecretKey getAesKey();

    IvParameterSpec getIvParameterSpec();

}
