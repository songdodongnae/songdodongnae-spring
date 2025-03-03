package com.culturefinder.songdodongnae.series.controller;

import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.dto.SeriesThumbnailResponseDto;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesRepository seriesRepository;

    @GetMapping("/thumbnails")
    @Operation(summary = "홈화면 시리즈 썸네일 조회", description = "홈화면에 보여지는 시리즈 썸네일을 조회합니다.")
    public ResponseEntity<ResponseContainer<List<SeriesThumbnailResponseDto>>> thumbnails_get() {
        List<SeriesThumbnailResponseDto> seriesList = seriesRepository.findAllSeries()
                .stream().filter(Series::getIsOnMain)
                .sorted(Comparator.comparingInt(Series::getOrderNumber))
                .map(SeriesThumbnailResponseDto::new)
                .toList();
        return new ResponseContainer<List<SeriesThumbnailResponseDto>>(HttpStatus.OK, "", seriesList)
                .toResponseEntity();
    }
}
