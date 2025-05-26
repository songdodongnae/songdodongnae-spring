package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DeliciousSpot API", description = "맛집 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delicious-spot")
public class DeliciousSpotController {

    private final DeliciousSpotService deliciousSpotService;

    @Operation(summary = "맛집 생성", description = "맛집을 생성합니다")
    @ApiResponse(responseCode = "201", description = "맛집 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<DeliciousSpotResponseDto>> readDeliciousSpot(
            @RequestBody DeliciousSpotReqDto deliciousSpotReqDto) {

        DeliciousSpotResponseDto dto = deliciousSpotService.createDeliciousSpot(deliciousSpotReqDto);
        return new ResponseContainer<>(HttpStatus.CREATED, "맛집 생성 성공", dto).toResponseEntity();
    }

    @Operation(summary = "맛집 조회", description = "특정 ID의 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "맛집 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResponseDto>> readDeliciousSpot(
            @PathVariable Long id) {

        DeliciousSpotResponseDto dto = deliciousSpotService.getDeliciousSpotById(id);
        return new ResponseContainer<>(HttpStatus.OK, "맛집 조회 성공", dto).toResponseEntity();
    }

}
