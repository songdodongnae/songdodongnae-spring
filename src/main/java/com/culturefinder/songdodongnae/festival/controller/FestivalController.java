package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
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
import java.util.stream.Collectors;

@Tag(name = "Festival API", description = "축제 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FestivalController {

    private final FestivalService festivalService;
    private final FestivalRepository festivalRepository;

    @Operation(summary = "해당 년/월 축제 조회", description = "해당 년/월에 개최되는 모든 축제 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "해당 월 축제 조회 성공")
    @GetMapping("/festivals")
    public ResponseEntity<ResponseContainer<List<FestivalResDto>>> festivalsByYearAndMonth(
            @RequestParam int year,
            @RequestParam int month) {

        List<FestivalResDto> dtos = festivalService.getFestivalsByYearAndMonth(year, month)
                .stream()
                .map(Festival::fromEntity)
                .collect(Collectors.toList());
        return new ResponseContainer<>(HttpStatus.OK, "년/월 해당 축제 조회 성공", dtos).toResponseEntity();
    }

    @Operation(summary = "모든 축제 조회", description = "등록된 모든 축제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공")
    @GetMapping("festivals/all")
    public ResponseEntity<ResponseContainer<List<FestivalResDto>>> festivalAll() {
        List<FestivalResDto> dtos = festivalService.getAllFestival()
                .stream()
                .map(Festival::fromEntity)
                .collect(Collectors.toList());
        return new ResponseContainer<>(HttpStatus.OK, "모든 축제 조회 성공", dtos).toResponseEntity();
    }

    @Operation(summary = "축제 조회", description = "특정 ID의 축제 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "축제 조회 성공")
    @GetMapping("/festival/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> festivalRead(@PathVariable Long id) {
        FestivalResDto dto = festivalService.getFestival(id).fromEntity();
        return new ResponseContainer<>(HttpStatus.OK, "축제 조회 성공", dto).toResponseEntity();
    }

}
