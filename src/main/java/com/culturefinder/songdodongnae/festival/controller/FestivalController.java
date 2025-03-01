package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.festival.domain.FestivalCategory;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.dto.ResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/festival")
public class FestivalController {

    private final FestivalService festivalService;

    @Operation(summary = "축제 생성", description = "새로운 축제를 생성합니다.")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "축제 생성 완료"))
    @PostMapping
    public ResponseEntity<ResDto> festivalCreate(
            @RequestBody FestivalReqDto festivalReqDto /*,
            @RequestPart(required = false) MultipartFile posterFile,
            @RequestPart(required = false) MultipartFile imageFile*/) {
        FestivalResDto dto = festivalService.createFestival(festivalReqDto/*, posterFile, imageFile*/);
        return new ResponseEntity<>(new ResDto(HttpStatus.CREATED, "축제생성 완료", dto), HttpStatus.CREATED);
    }

    @Operation(summary = "축제 수정", description = "기존 축제 정보를 수정합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "축제 수정 완료"))
    @PutMapping("/{id}")
    public ResponseEntity<ResDto> festivalUpdate(@PathVariable Long id, @RequestBody FestivalReqDto festivalReqDto) {
        FestivalResDto dto = festivalService.updateFestival(id, festivalReqDto);
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제수정 완료", dto), HttpStatus.OK);
    }

    @Operation(summary = "축제 삭제", description = "축제를 삭제합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "축제 삭제 완료"))
    @DeleteMapping("/{id}")
    public ResponseEntity<ResDto> festivalDelete(@PathVariable Long id) {
        FestivalResDto dto = festivalService.deleteFestival(id);
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제삭제 완료", dto), HttpStatus.OK);
    }

    @Operation(summary = "모든 축제 조회", description = "등록된 모든 축제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "축제 목록 조회 성공")
    @GetMapping
    public ResponseEntity<ResDto> festivalAll() {
        List<FestivalResDto> dtos = festivalService.getAllFestival();
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제모두조회 완료", dtos), HttpStatus.OK);
    }

    @Operation(summary = "축제 조회", description = "특정 ID의 축제 정보를 조회합니다.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "축제 조회 성공"))
    @GetMapping("/{id}")
    public ResponseEntity<ResDto> festivalRead(@PathVariable Long id) {
        FestivalResDto dto = festivalService.getFestival(id);
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제조회 완료", dto), HttpStatus.OK);
    }

}
