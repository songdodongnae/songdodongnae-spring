package com.culturefinder.songdodongnae.curation.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/curations")
@RestController
public class CurationController {

    private final CurationService curationService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> getCuration(@PathVariable Long id){
        CurationResDto curationResDto = curationService.getCuration(id);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 조회 성공", curationResDto).toResponseEntity();
    }
}
