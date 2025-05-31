package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Festival API", description = "축제 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/festival")
public class FestivalController {

    private final FestivalService festivalService;

    @Operation(summary = "축제 생성", description = "축제를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "축제 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<FestivalResDto>> festivalCreate(
            @RequestBody FestivalReqDto festivalReqDto,
            @RequestPart(required = false) MultipartFile mainImage,
            @RequestPart(required = false) List<MultipartFile> images) {

        FestivalResDto dto = festivalService.createFestival(festivalReqDto);
        return new ResponseContainer<>(HttpStatus.CREATED, "축제 생성 성공", dto).toResponseEntity();
    }

    @Operation(summary = "해당 년/월 축제 조회", description = "해당 년/월에 개최되는 모든 축제 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "해당 월 축제 조회 성공")
    @GetMapping("/day")
    public ResponseEntity<ResponseContainer<List<FestivalResDto>>> festivalsByYearAndMonth(
            @RequestParam int year,
            @RequestParam int month) {

        List<FestivalResDto> dtos = festivalService.getFestivalsByYearAndMonth(year, month);
        return new ResponseContainer<>(HttpStatus.OK, "년/월 해당 축제 조회 성공", dtos).toResponseEntity();
    }

    @Operation(summary = "모든 축제 조회", description = "등록된 모든 축제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공")
    @GetMapping("/all")
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
