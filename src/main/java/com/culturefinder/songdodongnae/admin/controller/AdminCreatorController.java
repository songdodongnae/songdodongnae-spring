package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.service.CreatorService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/creator")
@RequiredArgsConstructor
public class AdminCreatorController {

    private final CreatorService creatorService;

//    @PostMapping
//    public ResponseEntity<ResponseContainer<CreatorResDto>> creatorCreate(@RequestBody CreatorReqDto creatorReqDto) {
//        CreatorResDto dto = creatorService.createCreator(creatorReqDto);
//        return new ResponseEntity<>(new ResDto(HttpStatus.CREATED, "크리에이터생성 완료", dto), HttpStatus.CREATED);
//    }
}
