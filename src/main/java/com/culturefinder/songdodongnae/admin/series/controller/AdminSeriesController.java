package com.culturefinder.songdodongnae.admin.series.controller;

import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/series")
public class AdminSeriesController {

    private final SeriesRepository seriesRepository;
    private final S3UploadService uploadService;

    @GetMapping
    public String series_get() {
        return "admin/series";
    }

    @GetMapping("/list")
    public String series_list_get(Model model) {
        List<Series> seriesList = seriesRepository.findAllSeries();
        model.addAttribute("seriesList", seriesList);
        return "admin/series_list";
    }

    @GetMapping("/create")
    public String series_create_get() {
        return "admin/series_create";
    }

    @PostMapping("/create")
    public String series_create_post(
            @RequestParam("title") String title,
            @RequestParam("orderNumber") int orderNumber,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file.isEmpty()) throw new CustomException(ErrorCode.EMPTY_FILE);
        String imageUrl = uploadService.saveFile(file);
        Series series = Series.builder()
                .title(title)
                .orderNumber(orderNumber)
                .imageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        seriesRepository.addSeries(series);
        return "redirect:/admin/series";
    }
}
