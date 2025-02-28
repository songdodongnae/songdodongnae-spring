package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.SingleDeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delicious-spot")
public class DeliciousSpotController {

    private final DeliciousSpotRepository deliciousSpotRepository;
    private final SeriesRepository seriesRepository;

    @GetMapping("/thumbnails")
    public ResponseEntity<ResponseContainer<List<DeliciousSpotThumbnailResponseDto>>> thumbnails_get() {
        List<DeliciousSpotThumbnailResponseDto> data = seriesRepository.findAllSeries()
                .stream()
                .map(DeliciousSpotThumbnailResponseDto::new)
                .toList();

        return new ResponseContainer<>(HttpStatus.OK, "", data).toResponseEntity();
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ResponseContainer<List<SingleDeliciousSpotResponseDto>>> list_get(@PathVariable Long id) {
        List<SingleDeliciousSpotResponseDto> data = seriesRepository.findSeriesById(id)
                .getDeliciousSpots()
                .stream()
                .map(SingleDeliciousSpotResponseDto::new)
                .toList();

        return new ResponseContainer<>(HttpStatus.OK, "", data).toResponseEntity();
    }

}
