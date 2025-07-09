package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "DeliciousSpot API", description = "맛집 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delicious-spots")
public class DeliciousSpotController {

    private final DeliciousSpotService deliciousSpotService;

    @Operation(summary = "맛집 생성", description = "맛집을 생성합니다")
    @ApiResponse(responseCode = "200", description = "맛집 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<DeliciousSpotResponseDto>> createDeliciousSpot(
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto,
            @RequestPart(required = false) MultipartFile mainImage,
            @RequestPart(required = false) List<MultipartFile> images) {

        DeliciousSpotResponseDto dto = deliciousSpotService.createDeliciousSpot(deliciousSpotReqDto);
        return ResponseContainer.create(HttpStatus.CREATED, "맛집 생성 성공", dto);
    }

    @Operation(summary = "맛집 조회", description = "특정 ID의 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "맛집 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResponseDto>> readDeliciousSpot(
            @PathVariable Long id) {

        DeliciousSpotResponseDto dto = deliciousSpotService.getDeliciousSpotById(id);
        return ResponseContainer.create(HttpStatus.OK, "맛집 조회 성공", dto);
    }

    @Operation(summary = "맛집 수정", description = "특정 ID의 맛집을 수정합니다")
    @ApiResponse(responseCode = "200", description = "맛집 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResponseDto>> updateDeliciousSpot(
            @PathVariable Long id,
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpotResponseDto dto = deliciousSpotService.updateDeliciousSpot(id, deliciousSpotReqDto);
        return ResponseContainer.create(HttpStatus.OK, "맛집 수정 성공", dto);
    }

    @Operation(summary = "맛집 삭제", description = "특정 ID의 맛집을 삭제합니다")
    @ApiResponse(responseCode = "200", description = "맛집 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<Object>> deleteDeliciousSpot(
            @PathVariable Long id) {
        deliciousSpotService.deleteDeliciousSpot(id);
        return ResponseContainer.create(HttpStatus.OK, "맛집 삭제 성공", null);
    }

    @Operation(summary = "모든 맛집 조회", description = "모든 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "모든 맛집 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<List<DeliciousSpotResponseDto>>> getAllDeliciousSpots() {
        List<DeliciousSpotResponseDto> deliciousSpots = deliciousSpotService.getAllDeliciousSpots();
        return ResponseContainer.create(HttpStatus.OK, "모든 맛집 조회 성공", deliciousSpots);
    }

}
