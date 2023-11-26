package org.softart.cryptodoc.models.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.*;
import org.softart.cryptodoc.models.auth.User;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@With
public class Document {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static final class EncryptedContent {
        private String data;
        private String aesKey;
        private String ivSpec;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 5000, nullable = false)
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @Transient
    @JsonUnwrapped(prefix = "content_")
    private EncryptedContent content;
}
