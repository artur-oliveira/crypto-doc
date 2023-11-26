package org.softart.cryptodoc.services.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.softart.cryptodoc.components.EncryptUtils;
import org.softart.cryptodoc.exceptions.auth.UserAlreadyExistsException;
import org.softart.cryptodoc.models.auth.Role;
import org.softart.cryptodoc.models.auth.User;
import org.softart.cryptodoc.models.auth.request.RegisterRequest;
import org.softart.cryptodoc.models.encrypt.KeyPairResponse;
import org.softart.cryptodoc.repository.auth.UserRepository;
import org.softart.cryptodoc.services.encryption.UserPublicKeyService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.util.Set;

@Service
@AllArgsConstructor
@Data
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserPublicKeyService userPublicKeyService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    @SneakyThrows
    public KeyPairResponse createUser(RegisterRequest request) {
        getUserRepository().findUserByEmail(request.getEmail()).ifPresent(account -> {
            throw new UserAlreadyExistsException(request.getEmail());
        });
        getUserRepository().findUserByUsername(request.getUserName()).ifPresent(account -> {
            throw new UserAlreadyExistsException(request.getUserName());
        });
        User user = getUserRepository().save(User
                .builder()
                .email(request.getEmail())
                .username(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(getPasswordEncoder().encode(request.getPassword()))
                .roles(Set.of(Role.USER))
                .build());

        KeyPair keyPair = EncryptUtils.newRsaKeyPair();
        getUserPublicKeyService().saveUserPublicKey(user, keyPair.getPublic());

        return KeyPairResponse
                .builder()
                .publicKey(EncryptUtils.keyToString(keyPair.getPublic()))
                .privateKey(EncryptUtils.keyToString(keyPair.getPrivate()))
                .build();
    }
}
