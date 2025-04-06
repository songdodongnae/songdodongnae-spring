package com.culturefinder.songdodongnae.admin.series.controller;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.series.domain.SeriesCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/series")
public class AdminSeriesController {

    private final CreatorRepository creatorRepository;

    @GetMapping
    public String series_get() {
        return "admin/series";
    }

    @GetMapping("/create")
    public String series_create_get(Model model) {
        // TODO: 3
//        List<Creator> creatorList = creatorRepository.findAllCreator();
//        List<SeriesCategory> categoryList = List.of(SeriesCategory.values());
//        model.addAttribute("creatorList", creatorList);
//        model.addAttribute("categoryList", categoryList);
        return "admin/series_create";
    }

    @PostMapping("/create")
    public String series_create_post() {
        return "redirect:/admin/series";
    }
}
