package com.culturefinder.songdodongnae.admin.festival.controller;

import com.culturefinder.songdodongnae.admin.festival.dto.AdminFestivalReqDto;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/festival")
@RequiredArgsConstructor
public class AdminFestivalController {

    private final FestivalService festivalService;

    @GetMapping
    public String festival_get() {
        return "admin/festival";
    }

    @GetMapping("/create")
    public String createFestivalForm(Model model) {
        model.addAttribute("festival", new AdminFestivalReqDto());
        return "admin/festival-form";
    }

    @PostMapping("/create")
    public String createFestival(@ModelAttribute("festival") AdminFestivalReqDto festivalReqDto) {
        if (festivalReqDto.getName() == null|| festivalReqDto.getDescription() == null ) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        if (festivalReqDto.getName().isBlank() || festivalReqDto.getDescription().isBlank() ) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        festivalService.createFestival(festivalReqDto);
        return "redirect:/admin/festival";
    }

    @GetMapping("/list")
    public String festivalList(Model model) {
        List<FestivalResDto> festivals = festivalService.getAllFestival();
        model.addAttribute("festivals", festivals);
        return "admin/festival-list";
    }

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
