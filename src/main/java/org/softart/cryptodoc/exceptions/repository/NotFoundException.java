package org.softart.cryptodoc.exceptions.repository;

import lombok.Getter;
import org.softart.cryptodoc.exceptions.CryptoDocException;

@Getter
public class NotFoundException extends CryptoDocException {
    private final Object identifier;

    public NotFoundException(Object identifier) {
        super("not found object with identifier " + identifier);
        this.identifier = identifier;
    }
}
