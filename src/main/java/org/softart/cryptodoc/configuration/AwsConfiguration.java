package org.softart.cryptodoc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfiguration {

    @Bean
    S3Client s3Client() {
        return S3Client.builder().build();
    }

}
