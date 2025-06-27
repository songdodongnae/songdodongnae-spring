package com.culturefinder.songdodongnae.s3.controller;

import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.s3.dto.PresignedUrlResponseDto;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3UploadService s3UploadService;

    @Operation(summary = "Presigned URL, Image URL 생성", description = "Presigned URL과 이미지 URL을 생성합니다. Presigned URL로 이미지를 업로드한 이후, 이미지 URL을 활용해서 요청하면 됩니다.")
    @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공")
    @GetMapping("/presigned-url")
    public ResponseEntity<ResponseContainer<PresignedUrlResponseDto>> getPresignedUrl() {
        UUID uuid = UUID.randomUUID();
        String presignedUrl = s3UploadService.generatePresignedUrl(uuid.toString());
        String imageUrl = "https://songdo-s3-bucket.s3.ap-northeast-2.amazonaws.com/" + uuid.toString();
        PresignedUrlResponseDto presignedUrlDto = new PresignedUrlResponseDto(presignedUrl, imageUrl);
        return new ResponseContainer<>(HttpStatus.OK, "Presigned URL, Image URL 생성 성공", presignedUrlDto).toResponseEntity();
    }

}
