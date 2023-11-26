package org.softart.cryptodoc.components.aws;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.softart.cryptodoc.configuration.properties.CryptoDocS3Properties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@Data
@AllArgsConstructor
public class CryptoDocS3 {
    private final CryptoDocS3Properties cryptoDocS3Properties;
    private final S3Client s3Client;

    public void upload(String key, String content) {
        getS3Client().putObject(PutObjectRequest.builder().bucket(defaultBucket()).key(key).build(), RequestBody.fromString(content));
    }

    public void upload(String key, byte[] content) {
        getS3Client().putObject(PutObjectRequest.builder().bucket(defaultBucket()).key(key).build(), RequestBody.fromBytes(content));
    }

    public byte[] download(String key) {
        ResponseBytes<GetObjectResponse> response = getS3Client().getObjectAsBytes(GetObjectRequest.builder().bucket(defaultBucket()).key(key).build());
        return response.asByteArrayUnsafe();
    }

    String defaultBucket() {
        return getCryptoDocS3Properties().getDefaultBucketName();
    }
}
