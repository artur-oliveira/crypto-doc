package org.softart.cryptodoc.models.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public final class LoginRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
