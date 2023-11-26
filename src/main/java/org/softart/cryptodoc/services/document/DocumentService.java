package org.softart.cryptodoc.services.document;

import org.softart.cryptodoc.models.document.Document;
import org.softart.cryptodoc.models.document.request.SecureDocumentUploadRequest;
import org.softart.cryptodoc.models.document.request.UnsecureDocumentUploadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentService {

    Page<Document> listDocuments(Pageable pageable);

    Document createDocument(SecureDocumentUploadRequest document);

    Document createDocument(UnsecureDocumentUploadRequest document);

    Document getDocument(Long documentId, boolean secure);
}
