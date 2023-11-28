package org.softart.cryptodoc.exceptions.repository;

import org.softart.cryptodoc.exceptions.CryptoDocException;

public class CryptoDocRepositoryException extends CryptoDocException {

    public CryptoDocRepositoryException() {
    }

    public CryptoDocRepositoryException(String message) {
        super(message);
    }

    public CryptoDocRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoDocRepositoryException(Throwable cause) {
        super(cause);
    }

    public CryptoDocRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
