package com.culturefinder.songdodongnae.common.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String generatePresignedUrl(String objectKey) {
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000); // 1시간 유효
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(request).toString();
    }

    public void deleteFile(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        if (amazonS3.doesObjectExist(bucket, fileName)) {
            amazonS3.deleteObject(bucket, fileName);
            log.info("파일 삭제 완료. 파일이름={}", fileName);
        } else {
            log.warn("파일이 존재하지 않습니다. 파일이름={}", fileName);
        }
    }
}
