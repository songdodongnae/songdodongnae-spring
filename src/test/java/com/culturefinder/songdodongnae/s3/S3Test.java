package com.culturefinder.songdodongnae.s3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class S3Test {

    @Autowired S3UploadService s3UploadService;

    @Test
    public void deleteImage() {
        String url = "https://songdo-s3-bucket.s3.ap-northeast-2.amazonaws.com/KakaoTalk_Photo_2025-04-12-17-23-10.png";
        s3UploadService.deleteFile(url);
    }

}
