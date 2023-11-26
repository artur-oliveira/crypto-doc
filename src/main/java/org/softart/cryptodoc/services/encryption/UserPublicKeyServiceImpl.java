package org.softart.cryptodoc.services.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.softart.cryptodoc.components.EncryptUtils;
import org.softart.cryptodoc.components.aws.CryptoDocS3;
import org.softart.cryptodoc.models.auth.User;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.security.PublicKey;

@Service
@Data
@AllArgsConstructor
public class UserPublicKeyServiceImpl implements UserPublicKeyService {
    private final CryptoDocS3 cryptoDocS3;

    String getKey(User user) {
        return String.join("/", "pub_keys", user.getUsername() + ".pub");
    }

    @Override
    public PublicKey getUserPublicKey(User user) throws GeneralSecurityException {
        return EncryptUtils.getPublicKey(getCryptoDocS3().download(getKey(user)));
    }

    @Override
    public void saveUserPublicKey(User user, PublicKey publicKey) {
        getCryptoDocS3().upload(getKey(user), EncryptUtils.keyToString(publicKey));
    }
}
