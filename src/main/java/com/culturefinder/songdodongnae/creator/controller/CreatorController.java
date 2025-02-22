package com.culturefinder.songdodongnae.creator.controller;

import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.service.CreatorService;
import com.culturefinder.songdodongnae.festival.dto.ResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/creator")
@RestController
public class CreatorController {

    private final CreatorService creatorService;

    @PostMapping
    public ResponseEntity<ResDto> creatorCreate(@RequestBody CreatorReqDto creatorReqDto) {
        CreatorResDto dto = creatorService.createCreator(creatorReqDto);
        return new ResponseEntity<>(new ResDto(HttpStatus.CREATED, "크리에이터생성 완료", dto), HttpStatus.CREATED);
    }
}
