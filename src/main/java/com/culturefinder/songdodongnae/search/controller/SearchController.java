package com.culturefinder.songdodongnae.search.controller;

import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.search.service.SearchService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
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
    public ResponseEntity<ResponseContainer<SearchSummaryResDto>> getSearchSummary(
            @RequestParam @NotBlank(message = "검색어를 입력해주세요.") String query
    ) {
        SearchSummaryResDto searchSummary = searchService.getSearchSummary(query);
        return ResponseContainer.create(HttpStatus.OK, "요약 검색 성공", searchSummary);
    }

    @GetMapping("/festivals")
    public ResponseEntity<ResponseContainer<?>> getSearchFestivals(
            @RequestParam @NotBlank(message = "검색어를 입력해주세요.") String query
    ) {
        return null;
    }
}
