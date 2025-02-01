package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.ResponseContainer;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delicious-spot")
public class DeliciousSpotController {

    private final DeliciousSpotRepository deliciousSpotRepository;

    @GetMapping("/thumbnails")
    public ResponseEntity<ResponseContainer<List<DeliciousSpotThumbnailResponseDto>>> thumbnails_get() {
        return new ResponseEntity<>(
                new ResponseContainer<>(
                        HttpStatus.OK, "",
                        deliciousSpotRepository.findAllDeliciousSpotList()
                        .stream()
                        .map(DeliciousSpotThumbnailResponseDto::new)
                        .collect(Collectors.toList())
                ), HttpStatus.OK
        );
    }
}
