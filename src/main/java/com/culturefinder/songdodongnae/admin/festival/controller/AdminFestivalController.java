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
        return "admin/festival/festival";
    }

    @GetMapping("/create")
    public String createFestivalForm(Model model) {
        model.addAttribute("festival", new AdminFestivalReqDto());
        return "admin/festival/festival-form";
    }

    @PostMapping("/create")
    public String createFestival(@ModelAttribute AdminFestivalReqDto festivalReqDto) {
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
        return "admin/festival/festival-list";
    }

    @GetMapping("/update/{id}")
    public String updateFestivalForm(@PathVariable Long id, Model model) {
        FestivalResDto festival = festivalService.getFestival(id);

        AdminFestivalReqDto festivalReqDto = new AdminFestivalReqDto();
        festivalReqDto.setName(festival.getName());
        festivalReqDto.setStartDate(festival.getStartDate());
        festivalReqDto.setEndDate(festival.getEndDate());
        festivalReqDto.setStartTime(festival.getStartTime());
        festivalReqDto.setEndTime(festival.getEndTime());
        festivalReqDto.setTimeDescription(festival.getTimeDescription());
        festivalReqDto.setLocation(festival.getLocation());
        festivalReqDto.setFee(festival.getFee());
        festivalReqDto.setContact(festival.getContact());
        festivalReqDto.setHomePageUrl(festival.getHomePageUrl());
        festivalReqDto.setReservationUrl(festival.getReservationUrl());
        festivalReqDto.setOnelineDescription(festival.getOnelineDescription());
        festivalReqDto.setDescription(festival.getDescription());

        model.addAttribute("festival", festivalReqDto);
        model.addAttribute("id", id);
        return "admin/festival/festival-update";
    }

    @PostMapping("/update/{id}")
    public String updateFestival(@PathVariable Long id, @ModelAttribute AdminFestivalReqDto festivalReqDto) {
        if (festivalReqDto.getName() == null|| festivalReqDto.getDescription() == null ) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        if (festivalReqDto.getName().isBlank() || festivalReqDto.getDescription().isBlank() ) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        festivalService.updateFestival(id, festivalReqDto);
        return "redirect:/admin/festival";
    }

    @GetMapping("/delete/{id}")
    public String deleteFestivalForm(@PathVariable Long id, Model model) {
        FestivalResDto festival = festivalService.getFestival(id);
        model.addAttribute("festival", festival);
        model.addAttribute("id", id);
        return "admin/festival/festival-delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteFestival(@PathVariable Long id) {
        festivalService.deleteFestival(id);
        return "redirect:/admin/festival";
    }
}
