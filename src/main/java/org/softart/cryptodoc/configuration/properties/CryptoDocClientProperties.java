package org.softart.cryptodoc.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("crypto-doc.client")
public class CryptoDocClientProperties {
    private String host;
}
