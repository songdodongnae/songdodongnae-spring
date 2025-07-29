package com.culturefinder.songdodongnae.series;

import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final AuthService authService;
    private final DeliciousSpotService deliciousSpotService;
    private final FestivalService festivalService;
    private final CurationService curationService;

    @Operation(summary = "모든 맛집 조회", description = "모든 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "모든 맛집 조회 성공")
    @GetMapping("/delicious-spot")
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

    @Operation(summary = "모든 축제 조회", description = "등록된 모든 축제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공")
    @GetMapping("/festival")
    public ResponseEntity<ResponseContainer<CustomPage<FestivalThumbnailResDto>>> getAllFestivals(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllFestival(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllUserFestival(currentPage, pageSize, userId);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }
    }

    @Operation(summary = "타입별로 모든 큐레이션 조회", description = "등록된 타입별로 모든 큐레이션 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "타입별로 모든 큐레이션 조회 성공")
    @GetMapping("/curation/{type}")
    public ResponseEntity<ResponseContainer<CustomPage<CurationThumbnailResDto>>> getAllCurationFestival(
            @PathVariable CurationType type,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<CurationThumbnailResDto> dtos = curationService.getAllCurationByType(currentPage, pageSize, type);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 큐레이션 조회 성공", dtos);
        }else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<CurationThumbnailResDto> dtos = curationService.getAllUserCurationByType(userId, currentPage, pageSize, type);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 큐레이션 조회 성공", dtos);
        }
    }

}
