package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotInputDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/delicious_spot")
@RequiredArgsConstructor
public class AdminDeliciousSpotController {

    private final DeliciousSpotRepository deliciousSpotRepository;

    @GetMapping("/delicious-list-list")
    public String delicious_list_get(Model model) {
//        List<AdminDeliciousSpotListDto> deliciousSpotList = deliciousSpotRepository.findAllDeliciousSpotList()
//                .stream()
//                .map(AdminDeliciousSpotListDto::new)
//                .toList();
//        model.addAttribute("deliciousSpotList", deliciousSpotList);
        return "admin/delicious_spot/delicious-list-list";
    }

    @PostMapping("/delicious-list-list")
    public String delicious_list_post(String title, String imageUrl) {
//        DeliciousSpotList deliciousSpotList = new DeliciousSpotList(title, imageUrl);
//        deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);
        return "redirect:/admin/delicious_spot/delicious-list-list";
    }

    @GetMapping("/delicious-list")
    public String delicious_get(@RequestParam Long id, Model model) {
//        List<AdminDeliciousSpotDto> deliciousSpotList = deliciousSpotRepository
//                .findAllDeliciousSpotById(id)
//                .getDeliciousSpots()
//                .stream()
//                .map(AdminDeliciousSpotDto::new)
//                .toList();
//        String deliciousSpotTitle = deliciousSpotRepository.findDeliciousSpotListById(id).getTitle();
//        model.addAttribute("deliciousSpotId", id);
//        model.addAttribute("deliciousSpotList", deliciousSpotList);
//        model.addAttribute("deliciousSpotTitle", deliciousSpotTitle);
        return "admin/delicious_spot/delicious-list";
    }

    @PostMapping("/delicious-list")
    public String delicious_list_post(AdminDeliciousSpotInputDto delicious, @RequestParam Long id) {
//        if (!delicious.getName().isBlank()) {
//            deliciousSpotRepository.addDeliciousSpot(id, new DeliciousSpot(delicious));
//        }
        return "redirect:/admin/delicious_spot/delicious-list?id=" + id;
    }

    @PostMapping("/delicious-list-list/delete")
    public String delicious_list_delete(Long id) {
//        deliciousSpotRepository.removeDeliciousSpotList(id);
        return "redirect:/admin/delicious_spot/delicious-list-list";
    }
}
