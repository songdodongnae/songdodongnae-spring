package com.culturefinder.songdodongnae.s3.controller;

import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.s3.dto.PresignedUrlDto;
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

    @Operation(summary = "Presigned URL 생성", description = "Presigned URL을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공")
    @GetMapping("/presigned-url")
    public ResponseEntity<ResponseContainer<PresignedUrlDto>> getPresignedUrl() {
        UUID uuid = UUID.randomUUID();
        String url = s3UploadService.generatePresignedUrl(uuid.toString());
        PresignedUrlDto presignedUrlDto = new PresignedUrlDto(url);
        return new ResponseContainer<>(HttpStatus.OK, "Presigned URL 생성 성공", presignedUrlDto).toResponseEntity();
    }

}
