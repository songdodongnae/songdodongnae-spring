package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotInputDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotListDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDeliciousSpotController {

    private final DeliciousSpotRepository deliciousSpotRepository;

    @GetMapping("/delicious-list-list")
    public String delicious_list_get(Model model) {
        List<AdminDeliciousSpotListDto> deliciousSpotList = deliciousSpotRepository.findAllDeliciousSpotList()
                .stream()
                .map(AdminDeliciousSpotListDto::new)
                .toList();
        model.addAttribute("deliciousSpotList", deliciousSpotList);
        return "admin/delicious-list-list";
    }

    @PostMapping("/delicious-list-list")
    public String delicious_list_post(String title, String imageUrl) {
        DeliciousSpotList deliciousSpotList = new DeliciousSpotList(title, imageUrl);
        deliciousSpotRepository.saveDeliciousSpotList(deliciousSpotList);
        return "redirect:/admin/delicious-list-list";
    }

    @GetMapping("/delicious-list")
    public String delicious_get(@RequestParam Long id, Model model) {
        List<AdminDeliciousSpotDto> deliciousSpotList = deliciousSpotRepository
                .findAllDeliciousSpot(id)
                .stream()
                .map(AdminDeliciousSpotDto::new)
                .toList();
        String deliciousSpotTitle = deliciousSpotRepository.adminFindDeliciousSpotListById(id).getTitle();
        model.addAttribute("deliciousSpotId", id);
        model.addAttribute("deliciousSpotList", deliciousSpotList);
        model.addAttribute("deliciousSpotTitle", deliciousSpotTitle);
        return "admin/delicious-list";
    }

    @PostMapping("/delicious-list")
    public String delicious_post(AdminDeliciousSpotInputDto delicious, @RequestParam Long id) {
        if (!delicious.getName().isBlank()) {
            deliciousSpotRepository.addDeliciousSpot(id, delicious);
        }
        return "redirect:/admin/delicious-list?id=" + id;
    }

}
