package org.softart.cryptodoc.components;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Base64Utils {
    /**
     * It takes a string, converts it to a byte array, and then calls the encode function that takes a byte array
     *
     * @param s The string to be encoded.
     * @return A byte array
     */
    public static byte[] encode(String s) {
        return encode(s.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * It takes a byte array and returns a byte array.
     *
     * @param s The string to be encoded.
     * @return A byte array
     */
    public static byte[] encode(byte[] s) {
        return Base64.getEncoder().encode(s);
    }

    public static String encodeToString(String s) {
        return encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * It encodes a byte array into a string.
     *
     * @param s The string to be encoded.
     * @return A string
     */
    public static String encodeToString(byte[] s) {
        return Base64.getEncoder().encodeToString(s);
    }
}
