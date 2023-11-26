package org.softart.cryptodoc.models.document.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class SecureDocumentUploadRequest {
    @NotNull
    private String encryptedDocument;
    @NotNull
    private String documentName;
    @NotNull
    private String aesKey;
    @NotNull
    private String ivSpec;
}
