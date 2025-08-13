package com.culturefinder.songdodongnae.search.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.exception.ErrorDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.search.service.SearchService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search API", description = "검색 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    private final AuthService authService;

    @GetMapping("/summary")
    @Operation(summary = "통합 검색")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통합 검색 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "400", description = "검색어가 비어있거나 잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<ResponseContainer<SearchSummaryResDto>> getSearchSummary(
            @Parameter(description = "검색어", required = true, example = "송도")
            @RequestParam @NotBlank(message = "검색어를 입력해주세요.") String query
    ) {
        if (!authService.isAuthenticatedUser()) {
            SearchSummaryResDto searchSummary = searchService.getSearchSummary(null, query);
            return ResponseContainer.create(HttpStatus.OK, "요약 검색 성공", searchSummary);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            SearchSummaryResDto searchSummary = searchService.getSearchSummary(userId, query);
            return ResponseContainer.create(HttpStatus.OK, "요약 검색 성공", searchSummary);
        }
    }

    @GetMapping("/festivals")
    @Operation(summary = "축제 검색")
    @ApiResponse(responseCode = "200", description = "축제 검색 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    public ResponseEntity<ResponseContainer<CustomPage<FestivalThumbnailResDto>>> searchFestivals(
            @Parameter(description = "검색어", required = true, example = "축제") @RequestParam String query,
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize
    ) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<FestivalThumbnailResDto> result = searchService.searchFestivals(query, currentPage, pageSize);
            return ResponseContainer.create(
                    HttpStatus.OK,
                    "축제 검색 성공",
                    result
            );
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<FestivalThumbnailResDto> result = searchService.searchUserFestivals(query, currentPage, pageSize, userId);
            return ResponseContainer.create(
                    HttpStatus.OK,
                    "축제 검색 성공",
                    result
            );
        }
    }

    @GetMapping("/deliciousSpots")
    @Operation(summary = "맛집 검색")
    @ApiResponse(responseCode = "200", description = "맛집 검색 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    public ResponseEntity<ResponseContainer<CustomPage<DeliciousSpotThumbnailResDto>>> searchDeliciousSpots(
            @Parameter(description = "검색어", required = true, example = "맛집") @RequestParam String query,
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize
    ) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<DeliciousSpotThumbnailResDto> result = searchService.searchDeliciousSpots(query, currentPage, pageSize);
            return ResponseContainer.create(
                    HttpStatus.OK,
                    "맛집 검색 성공",
                    result
            );
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<DeliciousSpotThumbnailResDto> result = searchService.searchUserDeliciousSpots(query, currentPage, pageSize, userId);
            return ResponseContainer.create(
                    HttpStatus.OK,
                    "맛집 검색 성공",
                    result
            );
        }
    }

    @GetMapping("/curations")
    @Operation(summary = "큐레이션 검색")
    @ApiResponse(responseCode = "200", description = "큐레이션 검색 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    public ResponseEntity<ResponseContainer<CustomPage<CurationThumbnailResDto>>> searchCurations(
            @Parameter(description = "검색어", required = true, example = "큐레이션") @RequestParam String query,
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize
    ) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<CurationThumbnailResDto> result = searchService.searchCuration(query, currentPage, pageSize);
            return ResponseContainer.create(
                    HttpStatus.OK,
                    "큐레이션 검색 성공",
                    result
            );
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<CurationThumbnailResDto> result = searchService.searchUserCuration(query, currentPage, pageSize, userId);
            return ResponseContainer.create(
                    HttpStatus.OK,
                    "큐레이션 검색 성공",
                    result
            );
        }
    }
}
