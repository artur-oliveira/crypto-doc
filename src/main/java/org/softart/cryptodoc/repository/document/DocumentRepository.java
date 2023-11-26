package org.softart.cryptodoc.repository.document;

import org.softart.cryptodoc.models.auth.User;
import org.softart.cryptodoc.models.document.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Transactional(readOnly = true)
    Page<Document> findDocumentsByUser(User user, Pageable pageable);

    @Transactional(readOnly = true)
    Document findDocumentByIdAndUser(Long documentId, User user);
}
