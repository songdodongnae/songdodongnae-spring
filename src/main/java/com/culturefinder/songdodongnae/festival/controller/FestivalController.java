package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Festival API", description = "축제 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/festival")
public class FestivalController {

    private final FestivalService festivalService;

    @Operation(summary = "모든 축제 조회", description = "등록된 모든 축제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<List<FestivalResDto>>> festivalAll() {
        List<FestivalResDto> dtos = festivalService.getAllFestival();
        return new ResponseContainer<>(HttpStatus.OK, "모든 축제 조회 성공", dtos).toResponseEntity();
    }

    @Operation(summary = "축제 조회", description = "특정 ID의 축제 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "축제 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> festivalRead(@PathVariable Long id) {
        FestivalResDto dto = festivalService.getFestival(id);
        return new ResponseContainer<>(HttpStatus.OK, "축제 조회 성공", dto).toResponseEntity();
    }

}
