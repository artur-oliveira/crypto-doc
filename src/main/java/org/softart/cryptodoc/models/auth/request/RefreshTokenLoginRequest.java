package org.softart.cryptodoc.models.auth.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class RefreshTokenLoginRequest {
    private String refreshToken;
}
