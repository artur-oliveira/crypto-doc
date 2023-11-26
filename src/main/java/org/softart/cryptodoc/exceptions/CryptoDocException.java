package org.softart.cryptodoc.exceptions;

public abstract class CryptoDocException extends RuntimeException {
    public CryptoDocException() {
    }

    public CryptoDocException(String message) {
        super(message);
    }

    public CryptoDocException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoDocException(Throwable cause) {
        super(cause);
    }

    public CryptoDocException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
