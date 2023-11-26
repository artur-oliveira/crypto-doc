package org.softart.cryptodoc.services.auth;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateAccessToken(Authentication authentication);

    String generateAccessToken(String refreshToken);

    String generateRefreshToken(Authentication authentication);

    boolean isValidAccess(String authToken);

    boolean isValidRefresh(String refreshToken);

    String jti(String jwt);

}
