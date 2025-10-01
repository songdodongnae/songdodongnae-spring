package com.culturefinder.songdodongnae.series;

import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.common.utils.CustomPage;
import com.culturefinder.songdodongnae.common.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Series API", description = "시리즈 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final AuthService authService;
    private final DeliciousSpotService deliciousSpotService;
    private final FestivalService festivalService;
    private final CurationService curationService;

    @Operation(summary = "모든 맛집 조회")
    @ApiResponse(responseCode = "200", description = "모든 맛집 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping("/delicious-spot")
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

    @Operation(summary = "모든 축제 조회")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping("/festival")
    public ResponseEntity<ResponseContainer<CustomPage<FestivalThumbnailResDto>>> getAllFestivals(
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllFestival(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllUserFestival(currentPage, pageSize, userId);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }
    }

    @Operation(summary = "타입별로 모든 큐레이션 조회")
    @ApiResponse(responseCode = "200", description = "타입별로 모든 큐레이션 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping("/curation/{type}")
    public ResponseEntity<ResponseContainer<CustomPage<CurationThumbnailResDto>>> getAllCurationFestival(
            @Parameter(description = "큐래이션 타입", required = true) @PathVariable CurationType type,
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize) {
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
