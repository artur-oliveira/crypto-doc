package org.softart.cryptodoc.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.softart.cryptodoc.models.auth.request.LoginRequest;
import org.softart.cryptodoc.models.auth.request.RefreshTokenLoginRequest;
import org.softart.cryptodoc.models.auth.request.RegisterRequest;
import org.softart.cryptodoc.models.auth.response.JwtResponse;
import org.softart.cryptodoc.models.encrypt.KeyPairResponse;
import org.softart.cryptodoc.services.auth.JwtService;
import org.softart.cryptodoc.services.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Getter
public final class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping(value = "/token", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody final LoginRequest authRequest) {
        Authentication authentication = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = getJwtService().generateAccessToken(authentication);
        String refresh = getJwtService().generateRefreshToken(authentication);
        return ResponseEntity.ok(JwtResponse.builder().accessToken(jwt).refreshToken(refresh).build());
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody final RefreshTokenLoginRequest authRequest) {
        if (!getJwtService().isValidRefresh(authRequest.getRefreshToken())) {
            throw new InsufficientAuthenticationException("Invalid refresh token");
        }
        return ResponseEntity.ok(JwtResponse
                .builder()
                .accessToken(getJwtService().generateAccessToken(authRequest.getRefreshToken()))
                .build());
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<KeyPairResponse> register(@Valid @RequestBody final RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(getUserService().createUser(request));
    }
}
