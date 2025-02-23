package com.culturefinder.songdodongnae.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

        double fileSizeMB = (double) multipartFile.getSize()/1024/1024;
        log.info("파일 업로드 완료. 파일이름={} 파일사이즈={}MB", originalFilename, String.format("%.3f", fileSizeMB));
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }
}
