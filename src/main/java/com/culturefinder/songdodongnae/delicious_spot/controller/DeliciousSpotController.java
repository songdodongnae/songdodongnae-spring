package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DeliciousSpot API", description = "맛집 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delicious-spots")
public class DeliciousSpotController {

    private final DeliciousSpotService deliciousSpotService;
    private final AuthService authService;

    @Operation(summary = "맛집 생성", description = "맛집을 생성합니다")
    @ApiResponse(responseCode = "200", description = "맛집 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> createDeliciousSpot(
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        DeliciousSpotResDto dto = deliciousSpotService.createDeliciousSpot(deliciousSpotReqDto, userId);
        return ResponseContainer.create(HttpStatus.CREATED, "맛집 생성 성공", dto);
    }

    @Operation(summary = "맛집 조회", description = "특정 ID의 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "맛집 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> readDeliciousSpot(@PathVariable Long id) {

        if (!authService.isAuthenticatedUser()) {
            DeliciousSpotResDto dto = deliciousSpotService.getDeliciousSpotById(id);
            return ResponseContainer.create(HttpStatus.OK, "맛집 조회 성공", dto);
        }
        else {
            Long userId = authService.getAuthenticatedUserId();
            DeliciousSpotResDto dto = deliciousSpotService.getUserDeliciousSpot(id, userId);
            return ResponseContainer.create(HttpStatus.OK, "맛집 조회 성공", dto);
        }

    }

    @Operation(summary = "모든 맛집 조회", description = "모든 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "모든 맛집 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<DeliciousSpotThumbnailResDto>>> getAllDeliciousSpots(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<DeliciousSpotThumbnailResDto> deliciousSpots = deliciousSpotService.getAllDeliciousSpots(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "모든 맛집 조회 성공", deliciousSpots);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<DeliciousSpotThumbnailResDto> deliciousSpots = deliciousSpotService.getUserAllDeliciousSpots(userId, currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "사용자 맛집 조회 성공", deliciousSpots);
        }
    }

    @Operation(summary = "맛집 수정", description = "특정 ID의 맛집을 수정합니다")
    @ApiResponse(responseCode = "200", description = "맛집 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> updateDeliciousSpot(
            @PathVariable Long id,
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        DeliciousSpotResDto dto = deliciousSpotService.updateDeliciousSpot(id, deliciousSpotReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "맛집 수정 성공", dto);
    }

    @Operation(summary = "맛집 삭제", description = "특정 ID의 맛집을 삭제합니다")
    @ApiResponse(responseCode = "200", description = "맛집 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<Object>> deleteDeliciousSpot(
            @PathVariable Long id) {
        Long userId = authService.getAuthenticatedUserId();
        deliciousSpotService.deleteDeliciousSpot(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "맛집 삭제 성공", null);
    }

}
