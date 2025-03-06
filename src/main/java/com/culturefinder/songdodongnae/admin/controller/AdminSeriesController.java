package com.culturefinder.songdodongnae.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/series")
public class AdminSeriesController {

    @GetMapping
    public String series_get() {
        return "admin/series";
    }

    @GetMapping("/create")
    public String series_create_get() {
        return "admin/series_create";
    }

}
