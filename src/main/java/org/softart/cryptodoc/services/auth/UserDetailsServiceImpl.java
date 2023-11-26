package org.softart.cryptodoc.services.auth;

import lombok.Getter;
import org.softart.cryptodoc.models.auth.User;
import org.softart.cryptodoc.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Getter
public final class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCache userCache;

    @Autowired
    public UserDetailsServiceImpl(
            UserRepository userRepository,
            UserCache userCache
    ) {
        this.userRepository = userRepository;
        this.userCache = userCache;
    }

    private UsernameNotFoundException notFoundException(String username) {
        return new UsernameNotFoundException(username + " not found");
    }

    private User loadUserByUserNameInDatabase(String data) {
        Optional<User> optionalUser = Optional.empty();
        if (Objects.nonNull(data) && data.contains("@")) {
            optionalUser = getUserRepository().findUserByEmail(data);
        } else if (Objects.nonNull(data)) {
            optionalUser = getUserRepository().findUserByUsername(data);
        }

        User user = optionalUser.orElseThrow(() -> notFoundException(data));
        getUserCache().putUserInCache(user);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable((User) getUserCache().getUserFromCache(username)).orElseGet(() -> loadUserByUserNameInDatabase(username));
    }
}
