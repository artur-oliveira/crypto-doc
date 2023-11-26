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
public final class UnsecureDocumentUploadRequest {
    @NotNull
    private String document;
    @NotNull
    private String documentName;
}
