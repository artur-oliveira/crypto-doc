package org.softart.cryptodoc.exceptions.auth;

import lombok.Getter;
import org.softart.cryptodoc.exceptions.CryptoDocException;

@Getter
public class UserAlreadyExistsException extends CryptoDocException {

    private final String userNameOrEmail;

    public UserAlreadyExistsException(String userNameOrEmail) {
        super("User with username or email " + userNameOrEmail + " already exists");
        this.userNameOrEmail = userNameOrEmail;
    }
}
