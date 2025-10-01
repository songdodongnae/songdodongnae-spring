package com.culturefinder.songdodongnae.common.s3.controller;

import com.culturefinder.songdodongnae.common.s3.S3UploadService;
import com.culturefinder.songdodongnae.common.s3.dto.PresignedUrlResponseDto;
import com.culturefinder.songdodongnae.common.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Tag(name = "S3 API", description = "파일 업로드 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3UploadService s3UploadService;

    @Operation(summary = "Presigned URL, Image URL 생성")
    @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping("/presigned-url")
    public ResponseEntity<ResponseContainer<PresignedUrlResponseDto>> getPresignedUrl() {
        UUID uuid = UUID.randomUUID();
        String presignedUrl = s3UploadService.generatePresignedUrl(uuid.toString());
        String imageUrl = "https://songdo-s3-bucket.s3.ap-northeast-2.amazonaws.com/" + uuid.toString();
        PresignedUrlResponseDto presignedUrlDto = new PresignedUrlResponseDto(presignedUrl, imageUrl);
        return ResponseContainer.create(HttpStatus.OK, "Presigned URL, Image URL 생성 성공", presignedUrlDto);
    }

}
