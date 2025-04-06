package com.culturefinder.songdodongnae.curation;

import com.culturefinder.songdodongnae.utils.ResponseContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/curation")
@RestController
public class CurationController {

    private final CurationService curationService;

    @GetMapping("/thumbnail/all")
    public ResponseEntity<ResponseContainer<List<CurationThumbnailResDto>>> getAllCurationThumbnails() {
        List<CurationThumbnailResDto> dtos = curationService.getAllCurationThumbnails();
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 썸네일 목록 조회 성공", dtos).toResponseEntity();
    }

    @GetMapping("/thumbnail/top")
    public ResponseEntity<ResponseContainer<List<CurationThumbnailResDto>>> getTopCurationThumbnails() {
        List<CurationThumbnailResDto> dtos = curationService.getTopCurationThumbnails();
        return new ResponseContainer<>(HttpStatus.OK, "큐레이션 썸네일 상위 20개 조회 성공", dtos).toResponseEntity();
    }

}
