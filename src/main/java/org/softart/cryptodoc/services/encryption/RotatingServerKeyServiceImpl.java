package org.softart.cryptodoc.services.encryption;

import lombok.SneakyThrows;
import org.softart.cryptodoc.components.EncryptUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class RotatingServerKeyServiceImpl implements ServerKeyService {

    private KeyPair currentRsaKeyPair;
    private SecretKey currentAesKey;
    private IvParameterSpec ivParameterSpec;
    private final Object lock = new Object();

    public RotatingServerKeyServiceImpl() {
        rotateKeys();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::rotateKeys, 1, 1, TimeUnit.HOURS);
    }

    @SneakyThrows
    private void rotateKeys() {
        synchronized (lock) {
            this.currentRsaKeyPair = EncryptUtils.newRsaKeyPair();
            this.currentAesKey = EncryptUtils.newAesKey();
            this.ivParameterSpec = EncryptUtils.ivParameterSpec();
        }
    }

    @Override
    public PublicKey getRsaPublicKey() {
        return currentRsaKeyPair.getPublic();
    }

    @Override
    public PrivateKey getRsaPrivateKey() {
        return currentRsaKeyPair.getPrivate();
    }

    @Override
    public SecretKey getAesKey() {
        return currentAesKey;
    }

    @Override
    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }
}
