package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.dto.ResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/festival")
public class FestivalController {

    private final FestivalService festivalService;

    @PostMapping
    public ResponseEntity<ResDto> festivalCreate(
            @RequestBody FestivalReqDto festivalReqDto
            /*, @RequestPart(required = false) MultipartFile posterFile,
            @RequestPart(required = false) MultipartFile imageFile*/) {
        FestivalResDto dto = festivalService.createFestival(festivalReqDto/*, posterFile, imageFile*/);
        return new ResponseEntity<>(new ResDto(HttpStatus.CREATED, "축제생성 완료", dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDto> festivalUpdate(@PathVariable Long id, @RequestBody FestivalReqDto festivalReqDto) {
        FestivalResDto dto = festivalService.updateFestival(id, festivalReqDto);
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제수정 완료", dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDto> festivalDelete(@PathVariable Long id) {
        FestivalResDto dto = festivalService.deleteFestival(id);
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제삭제 완료", dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResDto> festivalAll() {
        List<FestivalResDto> dtos = festivalService.getAllFestival();
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제모두조회 완료", dtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDto> festivalRead(@PathVariable Long id) {
        FestivalResDto dto = festivalService.getFestival(id);
        return new ResponseEntity<>(new ResDto(HttpStatus.OK, "축제조회 완료", dto), HttpStatus.OK);
    }
}
