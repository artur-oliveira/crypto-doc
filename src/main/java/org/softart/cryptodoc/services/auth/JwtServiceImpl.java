package org.softart.cryptodoc.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.softart.cryptodoc.configuration.properties.CryptoDocAuthProperties;
import org.softart.cryptodoc.models.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.function.Function;

@Service
@Getter
@Setter
@Log4j2
public final class JwtServiceImpl implements JwtService {

    private final ObjectMapper mapper;
    private final SecretKey keyAccess;
    private final SecretKey keyRefresh;

    @Autowired
    public JwtServiceImpl(
            ObjectMapper mapper,
            CryptoDocAuthProperties cryptoDocAuthProperties
    ) {
        this.mapper = mapper;
        this.keyAccess = Keys.hmacShaKeyFor(Decoders.BASE64.decode(cryptoDocAuthProperties.getSecretKeyAccess()));
        this.keyRefresh = Keys.hmacShaKeyFor(Decoders.BASE64.decode(cryptoDocAuthProperties.getSecretKeyRefresh()));
    }

    private String generateToken(
            String id,
            String issuer,
            String subject,
            Date iat,
            Date exp,
            TokenType typ
    ) {
        return Jwts
                .builder()
                .id(id)
                .issuer(issuer)
                .subject(subject)
                .issuedAt(iat)
                .expiration(exp)
                .claim("type", typ.name().toLowerCase())
                .signWith(typ.secretKeyFor(this))
                .compact();
    }

    private String generateToken(User user, TokenType tokenType, Function<ZonedDateTime, Date> expirationFunction) {
        ZonedDateTime now = ZonedDateTime.now();
        return generateToken(
                user.getUsername(),
                user.getId().toString(),
                user.getFirstName() + " " + user.getLastName(),
                Date.from(now.toInstant()),
                expirationFunction.apply(now),
                tokenType
        );
    }

    @Override
    public String generateAccessToken(Authentication authentication) {
        return generateToken((User) authentication.getPrincipal(), TokenType.ACCESS, (now) -> Date.from(now.with(LocalTime.MAX).toInstant()));
    }

    @Override
    public String generateAccessToken(String refreshToken) {
        Jws<Claims> claimsJws = claimsRefresh(refreshToken);
        ZonedDateTime now = ZonedDateTime.now();
        return generateToken(
                claimsJws.getPayload().getId(),
                claimsJws.getPayload().getIssuer(),
                claimsJws.getPayload().getSubject(),
                Date.from(now.toInstant()),
                Date.from(now.with(LocalTime.MAX).toInstant()),
                TokenType.ACCESS
        );
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        return generateToken((User) authentication.getPrincipal(), TokenType.REFRESH, (now) -> Date.from(now.with(LocalTime.MAX).plusDays(365).toInstant()));
    }

    public Jws<Claims> claims(String authToken, SecretKey secretKey) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(authToken);
    }

    public Jws<Claims> claims(String authToken) {
        return claims(authToken, getKeyAccess());
    }

    public Jws<Claims> claimsRefresh(String authToken) {
        return claims(authToken, getKeyRefresh());
    }

    @Override
    public String jti(String authToken) {
        return claims(authToken).getPayload().getId();
    }

    @Override
    public boolean isValidAccess(String authToken) {
        try {
            claims(authToken);
            return true;
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean isValidRefresh(String refreshToken) {
        try {
            claimsRefresh(refreshToken);
            return true;
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private enum TokenType {
        ACCESS {
            @Override
            SecretKey secretKeyFor(JwtServiceImpl impl) {
                return impl.getKeyAccess();
            }
        },
        REFRESH {
            @Override
            SecretKey secretKeyFor(JwtServiceImpl impl) {
                return impl.getKeyRefresh();
            }
        };

        abstract SecretKey secretKeyFor(JwtServiceImpl impl);
    }
}
