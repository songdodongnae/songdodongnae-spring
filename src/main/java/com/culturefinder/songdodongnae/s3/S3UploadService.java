package com.culturefinder.songdodongnae.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.culturefinder.songdodongnae.utils.ImageCompressor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final int MB = 1048576;
    private final int MAX_SIZE = MB;
    private final AmazonS3 amazonS3;
    private final ImageCompressor imageCompressor;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        byte[] fileData;
        if (multipartFile.getSize() > MAX_SIZE) {
            log.info("파일이 {}MB를 넘어서 압축합니다. 압축 이전 사이즈={}MB", (double) MAX_SIZE / MB, (double) multipartFile.getSize() / MB);
            String format = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
            fileData = imageCompressor.compressImage(multipartFile, format, MAX_SIZE);
        } else {
            fileData = multipartFile.getBytes();
        }

        // 압축된 파일 크기 기반으로 ObjectMetadata 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileData.length);
        metadata.setContentType(multipartFile.getContentType());

        // 압축된 파일을 InputStream으로 변환하여 업로드
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData)) {
            amazonS3.putObject(bucket, originalFilename, byteArrayInputStream, metadata);
        }

        log.info("파일 업로드 완료. 파일이름={} 파일사이즈={}MB", originalFilename, String.format("%.3f", (double) fileData.length / MB));
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }
}
