package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> createDeliciousSpot(
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpotResDto dto = deliciousSpotService.createDeliciousSpot(deliciousSpotReqDto);
        return ResponseContainer.create(HttpStatus.CREATED, "맛집 생성 성공", dto);
    }

    @Operation(summary = "맛집 조회", description = "특정 ID의 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "맛집 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> readDeliciousSpot(
            @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DeliciousSpotResDto dto = null;
        if (authentication == null) {
            dto = deliciousSpotService.getDeliciousSpotById(id);
        } else {
            Long userId = Long.parseLong(authentication.getName());
            dto = deliciousSpotService.getUserDeliciousSpot(userId, id);
        }
        return ResponseContainer.create(HttpStatus.OK, "맛집 조회 성공", dto);
    }

    @Operation(summary = "맛집 수정", description = "특정 ID의 맛집을 수정합니다")
    @ApiResponse(responseCode = "200", description = "맛집 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResDto>> updateDeliciousSpot(
            @PathVariable Long id,
            @Valid @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpotResDto dto = deliciousSpotService.updateDeliciousSpot(id, deliciousSpotReqDto);
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
    public ResponseEntity<ResponseContainer<CustomPage<DeliciousSpotResDto>>> getAllDeliciousSpots(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            CustomPage<DeliciousSpotResDto> deliciousSpots = deliciousSpotService.getAllDeliciousSpots(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "모든 맛집 조회 성공", deliciousSpots);
        } else {
            Long userId = Long.parseLong(authentication.getName());
            CustomPage<DeliciousSpotResDto> deliciousSpots = deliciousSpotService.getAllDeliciousSpots(userId, currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "사용자 맛집 조회 성공", deliciousSpots);
        }
    }

}
