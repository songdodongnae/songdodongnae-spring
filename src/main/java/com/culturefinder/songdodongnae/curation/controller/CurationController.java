package com.culturefinder.songdodongnae.curation.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ResponseContainer<CurationResDto>> createCuration(@Valid @RequestBody CurationReqDto curationReqDto) {
        CurationResDto curationResDto = curationService.createCuration(curationReqDto);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 생성 성공", curationResDto).toResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> updateCuration(@PathVariable Long id, @Valid @RequestBody CurationReqDto curationReqDto){
        CurationResDto curationResDto = curationService.updateCuration(id, curationReqDto);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 수정 성공", curationResDto).toResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> deleteCuration(@PathVariable Long id){
        CurationResDto curationResDto = curationService.deleteCuration(id);
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 삭제 성공", curationResDto).toResponseEntity();
    }
}
