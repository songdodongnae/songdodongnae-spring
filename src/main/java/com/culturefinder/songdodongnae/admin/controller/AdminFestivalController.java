package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/festival")
@RequiredArgsConstructor
public class AdminFestivalController {

//    private final FestivalService festivalService;
//
//    @PostMapping
//    public ResponseEntity<ResDto> festivalCreate(
//            @RequestBody FestivalReqDto festivalReqDto /*,
//            @RequestPart(required = false) MultipartFile posterFile,
//            @RequestPart(required = false) MultipartFile imageFile*/) {
//        FestivalResDto dto = festivalService.createFestival(festivalReqDto/*, posterFile, imageFile*/);
//        return new ResponseEntity<>(new ResDto(HttpStatus.CREATED, "축제생성 완료", dto), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ResDto> festivalUpdate(@PathVariable Long id, @RequestBody FestivalReqDto festivalReqDto) {
//        FestivalResDto dto = festivalService.updateFestival(id, festivalReqDto);
//        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제수정 완료", dto), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ResDto> festivalDelete(@PathVariable Long id) {
//        FestivalResDto dto = festivalService.deleteFestival(id);
//        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제삭제 완료", dto), HttpStatus.OK);
//    }
}
