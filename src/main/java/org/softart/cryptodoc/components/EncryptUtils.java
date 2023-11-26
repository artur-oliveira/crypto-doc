package org.softart.cryptodoc.components;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptUtils {
    public static KeyFactory getRsaKeyFactory() throws GeneralSecurityException {
        return KeyFactory.getInstance("RSA", "BC");
    }

    public static KeyPairGenerator getRsaKeyPairGenerator() throws GeneralSecurityException {
        return KeyPairGenerator.getInstance("RSA", "BC");
    }

    public static KeyPair newRsaKeyPair() throws GeneralSecurityException {
        KeyPairGenerator generator = getRsaKeyPairGenerator();
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public static KeyGenerator getAesKeyGenerator() throws GeneralSecurityException {
        return KeyGenerator.getInstance("AES", "BC");
    }

    public static SecretKey newAesKey() throws GeneralSecurityException {
        KeyGenerator generator = getAesKeyGenerator();
        generator.init(256);
        return generator.generateKey();
    }

    public static Cipher getRsaCipher() throws GeneralSecurityException {
        return Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
    }

    public static Cipher getAesCipher() throws GeneralSecurityException {
        return Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
    }


    public static PublicKey getPublicKey(String key) throws GeneralSecurityException {
        return getPublicKey(key.getBytes(StandardCharsets.UTF_8));
    }

    public static PrivateKey getPrivateKey(String key) throws GeneralSecurityException {
        return getPrivateKey(key.getBytes(StandardCharsets.UTF_8));
    }

    public static SecretKey getSecretKey(String key) throws GeneralSecurityException {
        return getSecretKey(key.getBytes(StandardCharsets.UTF_8));
    }

    public static SecretKey getSecretKey(byte[] bytes) throws GeneralSecurityException {
        byte[] decodedKey = Base64.getDecoder().decode(bytes);
        return new SecretKeySpec(decodedKey, "AES");
    }

    public static PublicKey getPublicKey(byte[] bytes) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(bytes);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return getRsaKeyFactory().generatePublic(keySpec);
    }

    public static IvParameterSpec ivParameterSpec() {
        byte[] bytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return new IvParameterSpec(bytes);
    }

    public static IvParameterSpec ivParameterSpec(String bytes) {
        return new IvParameterSpec(Base64.getDecoder().decode(bytes));
    }

    public static PrivateKey getPrivateKey(byte[] bytes) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = getRsaKeyFactory();

        return keyFactory.generatePrivate(keySpec);
    }

    public static byte[] encryptRsa(String plainText, PublicKey publicKey) throws GeneralSecurityException {
        return encryptRsa(plainText.getBytes(), publicKey);
    }


    public static byte[] encryptAes(String plainText, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        return encryptAes(plainText.getBytes(), key, spec);
    }

    public static byte[] encryptAes(byte[] o, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        Cipher cipher = getAesCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(o);
    }

    public static byte[] encryptRsa(byte[] o, PublicKey publicKey) throws GeneralSecurityException {
        Cipher cipher = getRsaCipher();
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(o);
    }

    public static String encryptAesAsString(String o, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        return Base64Utils.encodeToString(encryptAes(o, key, spec));
    }

    public static String encryptAesAsString(byte[] o, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        return Base64Utils.encodeToString(encryptAes(o, key, spec));
    }


    public static String encryptRsaAsString(String o, PublicKey publicKey) throws GeneralSecurityException {
        return Base64Utils.encodeToString(encryptRsa(o, publicKey));
    }

    public static String encryptRsaAsString(byte[] o, PublicKey publicKey) throws GeneralSecurityException {
        return Base64Utils.encodeToString(encryptRsa(o, publicKey));
    }

    public static String decryptRsa(String encryptedString, PrivateKey privateKey) throws GeneralSecurityException {
        return decryptRsa(Base64.getDecoder().decode(encryptedString), privateKey);
    }

    public static String decryptAes(String encryptedString, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        return decryptAes(Base64.getDecoder().decode(encryptedString), key, spec);
    }


    public static String decryptAes(byte[] encrypted, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        Cipher cipher = getAesCipher();
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedData = cipher.doFinal(encrypted);
        return new String(decryptedData);
    }


    public static byte[] decryptAesAsBytes(String encryptedString, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        return decryptAesAsBytes(Base64.getDecoder().decode(encryptedString), key, spec);
    }


    public static byte[] decryptAesAsBytes(byte[] encrypted, SecretKey key, IvParameterSpec spec) throws GeneralSecurityException {
        Cipher cipher = getAesCipher();
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        return cipher.doFinal(encrypted);
    }


    public static String decryptRsa(byte[] encrypted, PrivateKey privateKey) throws GeneralSecurityException {
        Cipher cipher = getRsaCipher();
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(encrypted);
        return new String(decryptedData);
    }

    public static String keyToString(Key key) {
        return Base64Utils.encodeToString(key.getEncoded());
    }

    public static String ivParameterSpecToString(IvParameterSpec spec) {
        return Base64Utils.encodeToString(spec.getIV());
    }
}
