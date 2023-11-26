package org.softart.cryptodoc.services.auth;

import org.softart.cryptodoc.models.auth.request.RegisterRequest;
import org.softart.cryptodoc.models.encrypt.KeyPairResponse;

public interface UserService {
    KeyPairResponse createUser(RegisterRequest request);
}
