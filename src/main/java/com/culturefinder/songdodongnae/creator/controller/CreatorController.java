package com.culturefinder.songdodongnae.creator.controller;

import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.service.CreatorService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/creator")
@RestController
public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping("/thumbnail")
    public ResponseEntity<ResponseContainer<List<CreatorThumbnailResDto>>> getCreatorThumbnails() {
        List<CreatorThumbnailResDto> dtos = creatorService.getCreatorThumbnails();
        return new ResponseContainer<>(HttpStatus.OK, "크리에이터 썸네일 목록 조회 성공", dtos).toResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> getCreator(@PathVariable Long id) {
        CreatorResDto dto = creatorService.getCreator(id);
        return new ResponseContainer<>(HttpStatus.OK, "크리에이터 조회 성공", dto).toResponseEntity();
    }

}
