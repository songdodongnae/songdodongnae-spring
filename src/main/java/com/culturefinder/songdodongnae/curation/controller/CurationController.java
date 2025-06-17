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

    @GetMapping("/thumbnail/top")
    @Operation(summary = "큐레이션 썸네일 상위 20개 조회", description = "홈화면에 보여지는 큐레이션 썸네일 20개를 조회합니다.")
    public ResponseEntity<ResponseContainer<List<CurationThumbnailResDto>>> getTopCurationThumbnails() {
        List<CurationThumbnailResDto> dtos = curationService.getTopCurationThumbnails();
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 썸네일 상위 20개 조회 성공", dtos).toResponseEntity();
    }

    @GetMapping("/thumbnail")
    @Operation(summary = "시리즈에 포함된 큐레이션 조회", description = "특정 시리즈에 포함된 큐레이션을 조회합니다. (API 변경 예정)")
    public ResponseEntity<ResponseContainer<List<CurationThumbnailResDto>>> getCurationThumbnailsBySeriesId(
            @RequestParam Long seriesId
    ) {
        // TODO: 시리즈-축제 연결 부분이 아직 구현이 안되서 맛집만 보여줌
        // TODO: Pagination 필요
        Series series = seriesRepository.findSeriesById(seriesId);
        List<Curation> curations = series.getCurations();
        List<CurationThumbnailResDto> dtos = new ArrayList<>();
        for (Curation c: curations) {
//            DeliciousSpot deliciousSpot = c.getDeliciousSpot();
//            CurationThumbnailResDto dto = CurationThumbnailResDto
//                    .builder()
//                    .id(deliciousSpot.getId())
//                    .title(deliciousSpot.getTitle())
//                    .createdTime(deliciousSpot.getCreatedAt())
//                    .introduction(deliciousSpot.getOnelineDescription())
//                    .curationType(CurationType.DELICIOUS_SPOT)
//                    .build();
//            dtos.add(dto);
        }

        return new ResponseContainer<>(HttpStatus.OK, "특정 시리즈에 포함된 모든 큐레이션 조회 성공", dtos).toResponseEntity();
    }

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
        List<CurationThumbnailResDto> dtos = curationService.getAllCurationThumbnails();
        return new ResponseContainer<>(HttpStatus.OK, "모든 큐레이션 썸네일 조회 성공", dtos).toResponseEntity();
    }

    @Operation(summary = "큐레이션 상세 조회", description = "큐레이션의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 상세 조회 성공")
    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseContainer<Curation>> getCurationDetail(@RequestParam Long id) {
        Curation curation = curationService.findCurationById(id);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 상세 조회 성공", curation).toResponseEntity();
    }

    @Operation(summary = "큐레이션 등록", description = "큐레이션을 등록합니다.")
    @ApiResponse(responseCode = "20", description = "큐레이션 등록 성공")
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
