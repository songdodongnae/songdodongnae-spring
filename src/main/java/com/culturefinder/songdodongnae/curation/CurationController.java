package com.culturefinder.songdodongnae.curation;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.domain.SeriesDeliciousSpot;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
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

//    @GetMapping("/thumbnail/all")
//    public ResponseEntity<ResponseContainer<List<CurationThumbnailResDto>>> getAllCurationThumbnails() {
//        List<CurationThumbnailResDto> dtos = curationService.getAllCurationThumbnails();
//        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 썸네일 목록 조회 성공", dtos).toResponseEntity();
//    }

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
        List<SeriesDeliciousSpot> seriesDeliciousSpotList = series.getSeriesDeliciousSpotList();
        List<CurationThumbnailResDto> dtos = new ArrayList<>();
        for (SeriesDeliciousSpot e: seriesDeliciousSpotList) {
            DeliciousSpot deliciousSpot = e.getDeliciousSpot();
            CurationThumbnailResDto dto = CurationThumbnailResDto
                    .builder()
                    .id(deliciousSpot.getId())
                    .title(deliciousSpot.getName())
                    .imageUrl(deliciousSpot.getImageUrl())
                    .createdTime(deliciousSpot.getCreatedTime())
                    .introduction(deliciousSpot.getOnelineDescription())
                    .curationType(CurationType.DELICIOUS_SPOT)
                    .build();
            dtos.add(dto);
        }

        return new ResponseContainer<>(HttpStatus.OK, "특정 시리즈에 포함된 모든 큐레이션 조회 성공", dtos).toResponseEntity();
    }

}
