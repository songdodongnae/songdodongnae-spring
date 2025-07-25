package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Festival API", description = "축제 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    @Operation(summary = "축제 생성", description = "축제를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "축제 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<FestivalResDto>> createFestival(
            @Valid @RequestBody FestivalReqDto festivalReqDto) {
        Long userId = getUserId();
        FestivalResDto dto = festivalService.createFestival(festivalReqDto, userId);
        return ResponseContainer.create(HttpStatus.CREATED, "축제 생성 성공", dto);
    }

    @Operation(summary = "해당 년/월 축제 조회", description = "해당 년/월에 개최되는 모든 축제 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "해당 월 축제 조회 성공")
    @GetMapping("/day")
    public ResponseEntity<ResponseContainer<List<FestivalResDto>>> getFestivalsByYearAndMonth(
            @RequestParam int year,
            @RequestParam int month) {
        Long userId = getUserId();
        List<FestivalResDto> dtos = festivalService.getFestivalsByYearAndMonth(year, month, userId);
        return ResponseContainer.create(HttpStatus.OK, "년/월 해당 축제 조회 성공", dtos);
    }

    @Operation(summary = "모든 축제 조회", description = "등록된 모든 축제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<FestivalResDto>>> festivalAll(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {

        CustomPage<FestivalResDto> dtos = festivalService.getAllFestival(currentPage, pageSize);
        return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
    }

    @Operation(summary = "축제 조회", description = "특정 ID의 축제 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "축제 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> getFestival(@PathVariable Long id) {
        Long userId = getUserId();
        FestivalResDto dto = festivalService.getFestival(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "축제 조회 성공", dto);
    }

    @Operation(summary = "축제 삭제", description = "특정 ID의 축제 정보를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "축제 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> deleteFestival(@PathVariable Long id) {
        Long userId = getUserId();
        FestivalResDto dto = festivalService.deleteFestival(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "축제 삭제 성공", dto);
    }

    @Operation(summary = "축제 수정", description = "특정 ID의 축제 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "축제 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> updateFestival(
            @PathVariable Long id, @Valid @RequestBody FestivalReqDto festivalReqDto)  {
        Long userId = getUserId();
        FestivalResDto dto = festivalService.updateFestival(id, festivalReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "축제 수정 성공", dto);
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Long userId = Long.parseLong(authentication.getName());
        Long userId = 1L;
        return userId;
    }
}