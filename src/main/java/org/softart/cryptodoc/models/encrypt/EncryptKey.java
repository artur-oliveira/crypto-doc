package org.softart.cryptodoc.models.encrypt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncryptKey {
    private String publicKey;
    private String aesKey;
    private String ivSpec;
}
