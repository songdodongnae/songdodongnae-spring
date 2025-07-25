package com.culturefinder.songdodongnae.search.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.search.service.SearchService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/summary")
    @Operation(summary = "통합 검색", description = "검색어에 맞는 모든 것을 조회합니다.")
    public ResponseEntity<ResponseContainer<SearchSummaryResDto>> getSearchSummary(
            @RequestParam @NotBlank(message = "검색어를 입력해주세요.") String query
    ) {
        SearchSummaryResDto searchSummary = searchService.getSearchSummary(query);
        return ResponseContainer.create(HttpStatus.OK, "요약 검색 성공", searchSummary);
    }

    @GetMapping("/festivals")
    @Operation(summary = "축제 검색", description = "검색어에 맞는 축제를 페이지네이션하여 조회합니다.")
    public ResponseEntity<ResponseContainer<CustomPage<FestivalResDto>>> searchFestivals(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        CustomPage<FestivalResDto> result = searchService.searchFestivals(keyword, currentPage, pageSize);
        return ResponseContainer.create(
                HttpStatus.OK,
                "축제 검색 성공",
                result
        );
    }

    @GetMapping("/deliciousSpots")
    @Operation(summary = "맛집 검색", description = "검색어에 맞는 맛집을 페이지네이션하여 조회합니다.")
    public ResponseEntity<ResponseContainer<CustomPage<DeliciousSpotResDto>>> searchDeliciousSpots(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        CustomPage<DeliciousSpotResDto> result = searchService.searchDeliciousSpots(keyword, currentPage, pageSize);
        return ResponseContainer.create(
                HttpStatus.OK,
                "맛집 검색 성공",
                result
        );
    }

    @GetMapping("/curations")
    @Operation(summary = "큐레이션 검색", description = "검색어에 맞는 큐레이션을 페이지네이션하여 조회합니다.")
    public ResponseEntity<ResponseContainer<CustomPage<CurationResDto>>> searchCurations(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        CustomPage<CurationResDto> result = searchService.searchCuration(keyword, currentPage, pageSize);
        return ResponseContainer.create(
                HttpStatus.OK,
                "큐레이션 검색 성공",
                result
        );
    }
}
