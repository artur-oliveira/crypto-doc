package org.softart.cryptodoc.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.softart.cryptodoc.models.document.Document;
import org.softart.cryptodoc.models.document.request.SecureDocumentUploadRequest;
import org.softart.cryptodoc.models.document.request.UnsecureDocumentUploadRequest;
import org.softart.cryptodoc.services.document.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/document")
@AllArgsConstructor
@Data
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<Page<Document>> listDocuments(final @NotNull Pageable pageable) {
        return ResponseEntity.ok(getDocumentService().listDocuments(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(final @PathVariable("id") Long documentId, final @RequestParam("secure") Optional<Boolean> secure) {
        return ResponseEntity.ok(getDocumentService().getDocument(documentId, secure.orElse(true)));
    }

    @PostMapping
    public ResponseEntity<Document> uploadDocument(@Valid @RequestBody SecureDocumentUploadRequest document) {
        return ResponseEntity.ok(getDocumentService().createDocument(document));
    }

    @PostMapping("/unsecure")
    public ResponseEntity<Object> uploadDocumentUnsecure(@Valid @RequestBody UnsecureDocumentUploadRequest document) {
        return ResponseEntity.ok(getDocumentService().createDocument(document));
    }
}
