package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "맛집 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "맛집 생성 성공"),
            @ApiResponse(responseCode = "400", description = "필수 필드 누락 또는 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음 (ADMIN 권한 필요)"),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> createDeliciousSpot(
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        DeliciousSpotResDto dto = deliciousSpotService.createDeliciousSpot(deliciousSpotReqDto, userId);
        return ResponseContainer.create(HttpStatus.CREATED, "맛집 생성 성공", dto);
    }

    @Operation(summary = "맛집 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "맛집 조회 성공"),
            @ApiResponse(responseCode = "404", description = "맛집을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> readDeliciousSpot(
            @Parameter(description = "맛집 ID", required = true) @PathVariable Long id) {

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

    @Operation(summary = "모든 맛집 조회")
    @ApiResponse(responseCode = "200", description = "모든 맛집 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<DeliciousSpotThumbnailResDto>>> getAllDeliciousSpots(
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize
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

    @Operation(summary = "맛집 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "맛집 수정 성공"),
            @ApiResponse(responseCode = "400", description = "필수 필드 누락 또는 잘못된 요청 데이터"),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음 (ADMIN 권한 필요)"),
            @ApiResponse(responseCode = "404", description = "맛집 또는 크리에이터를 찾을 수 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> updateDeliciousSpot(
            @Parameter(description = "맛집 ID", required = true) @PathVariable Long id,
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        DeliciousSpotResDto dto = deliciousSpotService.updateDeliciousSpot(id, deliciousSpotReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "맛집 수정 성공", dto);
    }

    @Operation(summary = "맛집 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "맛집 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음 (ADMIN 권한 필요)"),
            @ApiResponse(responseCode = "404", description = "맛집을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<Object>> deleteDeliciousSpot(
            @Parameter(description = "맛집 ID", required = true) @PathVariable Long id) {
        Long userId = authService.getAuthenticatedUserId();
        deliciousSpotService.deleteDeliciousSpot(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "맛집 삭제 성공", null);
    }

}
