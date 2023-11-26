package org.softart.cryptodoc.models.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
