package org.softart.cryptodoc.exceptions.encrypt;

import org.softart.cryptodoc.exceptions.CryptoDocException;

public class ValidateEncryptionKeyException extends CryptoDocException {
    public ValidateEncryptionKeyException() {
        super("one of provided keys is invalid");
    }
}
