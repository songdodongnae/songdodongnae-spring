package com.culturefinder.songdodongnae.admin.series_delicious_spot.controller;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/series_delicious_spot")
public class AdminSeriesDeliciousSpotController {

    private final SeriesRepository seriesRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;

    @GetMapping
    public String series_delicious_spot_get(Model model) {
        List<DeliciousSpot> deliciousSpotList = deliciousSpotRepository.findAll();
        List<Series> seriesList = seriesRepository.findAllSeries();
        model.addAttribute("deliciousSpotList", deliciousSpotList);
        model.addAttribute("seriesList", seriesList);
        return "admin/series_delicious_spot";
    }

    @PostMapping
    public String series_delicious_spot_post(@RequestParam Long seriesId, @RequestParam Long deliciousSpotId) {
        Series series = seriesRepository.findSeriesById(seriesId);
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(deliciousSpotId);
        seriesRepository.addDeliciousSpotToSeries(series, deliciousSpot);
        return "redirect:/admin/series_delicious_spot";
    }

}
