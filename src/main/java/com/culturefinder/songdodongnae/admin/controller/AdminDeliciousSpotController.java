package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotInputDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotListDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/delicious_spot")
@RequiredArgsConstructor
public class AdminDeliciousSpotController {

    private final DeliciousSpotRepository deliciousSpotRepository;
    private final S3UploadService uploadService;

    @GetMapping("/series_list")
    public String delicious_list_get(Model model) {
        List<AdminDeliciousSpotListDto> deliciousSpotList = deliciousSpotRepository.findAllDeliciousSpotList()
                .stream()
                .map(AdminDeliciousSpotListDto::new)
                .toList();
        model.addAttribute("deliciousSpotList", deliciousSpotList);
        return "admin/delicious_spot/series_list";
    }

    @PostMapping("/series_list")
    public String delicious_list_post(String title, MultipartFile file) throws IOException {
        String imageUrl = uploadService.saveFile(file);
        DeliciousSpotList deliciousSpotList = new DeliciousSpotList(title, imageUrl);
        deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);
        return "redirect:/admin/delicious_spot/series_list";
    }

    @GetMapping("/series")
    public String delicious_get(@RequestParam Long id, Model model) {
        List<AdminDeliciousSpotDto> deliciousSpotList = deliciousSpotRepository
                .findAllDeliciousSpotById(id)
                .getDeliciousSpots()
                .stream()
                .map(AdminDeliciousSpotDto::new)
                .toList();
        String deliciousSpotTitle = deliciousSpotRepository.findDeliciousSpotListById(id).getTitle();
        model.addAttribute("deliciousSpotId", id);
        model.addAttribute("deliciousSpotList", deliciousSpotList);
        model.addAttribute("deliciousSpotTitle", deliciousSpotTitle);
        return "admin/delicious_spot/series";
    }

    @PostMapping("/series")
    public String delicious_list_post(AdminDeliciousSpotInputDto delicious, @RequestParam Long id) {
        if (!delicious.getName().isBlank()) {
            deliciousSpotRepository.addDeliciousSpot(id, new DeliciousSpot(delicious));
        }
        return "redirect:/admin/delicious_spot/series?id=" + id;
    }

    @PostMapping("/series_list/delete")
    public String delicious_list_delete(Long id) {
        deliciousSpotRepository.removeDeliciousSpotList(id);
        return "redirect:/admin/delicious_spot/series_list";
    }
}
