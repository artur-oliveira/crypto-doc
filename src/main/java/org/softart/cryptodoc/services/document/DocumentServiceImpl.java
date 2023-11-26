package org.softart.cryptodoc.services.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.bouncycastle.util.encoders.Base64;
import org.softart.cryptodoc.components.Base64Utils;
import org.softart.cryptodoc.components.EncryptUtils;
import org.softart.cryptodoc.components.auth.Auth;
import org.softart.cryptodoc.components.aws.CryptoDocS3;
import org.softart.cryptodoc.models.document.Document;
import org.softart.cryptodoc.models.document.request.SecureDocumentUploadRequest;
import org.softart.cryptodoc.models.document.request.UnsecureDocumentUploadRequest;
import org.softart.cryptodoc.repository.document.DocumentRepository;
import org.softart.cryptodoc.services.encryption.ServerKeyService;
import org.softart.cryptodoc.services.encryption.UserPublicKeyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

@Service
@AllArgsConstructor
@Data
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ServerKeyService serverKeyService;
    private final UserPublicKeyService userPublicKeyService;
    private final CryptoDocS3 cryptoDocS3;

    public String getDocumentKey(Document document) {
        return String.join("/", "docs", document.getUser().getUsername(), document.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Document> listDocuments(Pageable pageable) {
        return getDocumentRepository().findDocumentsByUser(Auth.getUser(), pageable);
    }

    @Transactional
    public Document createDocument(String name, byte[] docBytes) {
        Document newDocument = getDocumentRepository().save(Document
                .builder()
                .name(name)
                .user(Auth.getUser())
                .build());

        getCryptoDocS3().upload(getDocumentKey(newDocument), docBytes);

        return newDocument;
    }

    @Override
    @SneakyThrows
    @Transactional
    public Document createDocument(SecureDocumentUploadRequest document) {
        SecretKey secretKey = EncryptUtils.getSecretKey(EncryptUtils.decryptRsa(document.getAesKey(), getServerKeyService().getRsaPrivateKey()));
        IvParameterSpec ivParameterSpec = EncryptUtils.ivParameterSpec(EncryptUtils.decryptRsa(document.getIvSpec(), getServerKeyService().getRsaPrivateKey()));
        byte[] documentBytes = Base64.decode(EncryptUtils.decryptAesAsBytes(document.getEncryptedDocument(), secretKey, ivParameterSpec));
        return createDocument(document.getDocumentName(), documentBytes);
    }

    @Override
    @Transactional
    public Document createDocument(UnsecureDocumentUploadRequest document) {
        return createDocument(document.getDocumentName(), Base64.decode(document.getDocument().getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    private Document.EncryptedContent getEncryptedContent(Document document) {
        PublicKey userPublicKey = getUserPublicKeyService().getUserPublicKey(Auth.getUser());
        SecretKey aesKey = getServerKeyService().getAesKey();
        IvParameterSpec ivParameterSpec = getServerKeyService().getIvParameterSpec();

        byte[] content = getCryptoDocS3().download(getDocumentKey(document));
        String encryptedContent = EncryptUtils.encryptAesAsString(content, aesKey, ivParameterSpec);

        return Document.EncryptedContent
                .builder()
                .data(encryptedContent)
                .aesKey(EncryptUtils.encryptRsaAsString(EncryptUtils.keyToString(aesKey), userPublicKey))
                .ivSpec(EncryptUtils.encryptRsaAsString(EncryptUtils.ivParameterSpecToString(ivParameterSpec), userPublicKey))
                .build();
    }

    private Document.EncryptedContent getPlainContent(Document document) {
        return Document.EncryptedContent
                .builder()
                .data(Base64Utils.encodeToString(getCryptoDocS3().download(getDocumentKey(document))))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public Document getDocument(Long documentId, boolean secure) {
        Document document = getDocumentRepository().findDocumentByIdAndUser(documentId, Auth.getUser());
        return document.withContent(secure ? getEncryptedContent(document) : getPlainContent(document));
    }
}
