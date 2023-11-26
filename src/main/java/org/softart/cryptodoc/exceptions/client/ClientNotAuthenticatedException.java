package org.softart.cryptodoc.exceptions.client;

import org.softart.cryptodoc.exceptions.CryptoDocException;

public class ClientNotAuthenticatedException extends CryptoDocException {
    public ClientNotAuthenticatedException() {
        super("cannot make this request due to the client is not authenticated");
    }
}
