package com.culturefinder.songdodongnae.delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.service.DeliciousSpotService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DeliciousSpot API", description = "맛집 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delicious-spot")
public class DeliciousSpotController {

    private final DeliciousSpotService deliciousSpotService;

    @Operation(summary = "맛집 조회", description = "특정 ID의 맛집을 조회합니다")
    @ApiResponse(responseCode = "200", description = "맛집 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<DeliciousSpotResponseDto>> readDeliciousSpot(
            @PathVariable Long id) {

        DeliciousSpotResponseDto dto = deliciousSpotService.getDeliciousSpotById(id);
        return new ResponseContainer<>(HttpStatus.OK, "맛집 조회 성공", dto).toResponseEntity();
    }

}
