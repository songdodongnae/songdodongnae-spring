package com.culturefinder.songdodongnae.curation.controller;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotSummaryDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.dto.SeriesSummaryDto;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/curation")
@RestController
public class CurationController {

    private final CurationService curationService;
    private final SeriesRepository seriesRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;

    @Operation(summary = "모든 맛집 조회", description = "모든 맛집의 id와 title을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 맛집 id와 title 조회 성공")
    @GetMapping("/delicious-spot")
    public ResponseEntity<ResponseContainer<List<DeliciousSpotSummaryDto>>> getAllDeliciousSpotThumbnails() {
        List<DeliciousSpotSummaryDto> deliciousSpotSummaries = deliciousSpotRepository.findAll().stream()
                .map(DeliciousSpotSummaryDto::fromEntity)
                .toList();
        return new ResponseContainer<>(HttpStatus.OK, "모든 맛집 id와 title 조회 성공", deliciousSpotSummaries).toResponseEntity();
    }

    @Operation(summary = "모든 시리즈 조회", description = "모든 시리즈의 id와 title을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 시리즈 id와 title 조회 성공")
    @GetMapping("/series")
    public ResponseEntity<ResponseContainer<List<SeriesSummaryDto>>> getAllSeries() {
        List<SeriesSummaryDto> seriesSummaries = seriesRepository.findAllSeries().stream()
                .map(SeriesSummaryDto::fromEntity)
                .toList();
        return new ResponseContainer<>(HttpStatus.OK, "모든 시리즈 id와 title 조회 성공", seriesSummaries).toResponseEntity();
    }

    @Operation(summary = "모든 큐레이션 썸네일 조회", description = "모든 큐레이션의 썸네일을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 큐레이션 썸네일 조회 성공")
    @GetMapping("/thumbnail/all")
    public ResponseEntity<ResponseContainer<List<CurationThumbnailResDto>>> getAllCurationThumbnails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            List<CurationThumbnailResDto> dtos = curationService.getAllCurationThumbnails();
            return new ResponseContainer<>(HttpStatus.OK, "모든 큐레이션 썸네일 조회 성공", dtos).toResponseEntity();
        } else {
            Long userId = Long.parseLong(authentication.getName());
            List<CurationThumbnailResDto> dtos = curationService.getUserCurationThumbnails(userId);
            return new ResponseContainer<>(HttpStatus.OK, "사용자 큐레이션 썸네일 조회 성공", dtos).toResponseEntity();
        }
    }

    @Operation(summary = "큐레이션 상세 조회", description = "큐레이션의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 상세 조회 성공")
    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseContainer<Curation>> getCurationDetail(@RequestParam Long id) {
        Curation curation = curationService.findCurationById(id);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 상세 조회 성공", curation).toResponseEntity();
    }

    @Operation(summary = "큐레이션 등록", description = "큐레이션을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 등록 성공")
    @GetMapping("/create")
    public ResponseEntity<ResponseContainer<Curation>> createCuration(@RequestParam Curation curation) {
        Curation createdCuration = curationService.createCuration(curation);
        return new ResponseContainer<>(HttpStatus.CREATED, "큐레이션 등록 성공", createdCuration).toResponseEntity();
    }

    @Operation(summary = "큐레이션 삭제", description = "큐레이션을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 삭제 성공")
    @GetMapping("/delete/{id}")
    public ResponseEntity<ResponseContainer<Curation>> deleteCuration(@RequestParam Long id) {
        Curation deletedCuration = curationService.deleteCuration(id);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 삭제 성공", deletedCuration).toResponseEntity();
    }

}
