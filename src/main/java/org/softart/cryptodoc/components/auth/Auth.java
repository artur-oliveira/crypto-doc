package org.softart.cryptodoc.components.auth;

import org.softart.cryptodoc.models.auth.User;
import org.springframework.security.core.context.SecurityContextHolder;

public final class Auth {

    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
