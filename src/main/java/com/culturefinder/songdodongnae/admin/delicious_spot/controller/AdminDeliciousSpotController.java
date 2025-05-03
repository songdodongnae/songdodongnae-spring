package com.culturefinder.songdodongnae.admin.delicious_spot.controller;

import com.culturefinder.songdodongnae.admin.delicious_spot.dto.AdminDeliciousSpotCreateRequestDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/delicious_spot")
@RequiredArgsConstructor
public class AdminDeliciousSpotController {

    private final DeliciousSpotRepository deliciousSpotRepository;
    private final S3UploadService uploadService;

    @GetMapping
    public String delicious_spot_get() {
        return "admin/delicious_spot";
    }

    @GetMapping("/list")
    public String delicious_spot_list_get(Model model) {
        List<DeliciousSpot> deliciousSpotList = deliciousSpotRepository.findAll();
        model.addAttribute("deliciousSpotList", deliciousSpotList);
        return "admin/delicious_spot_list";
    }

    @GetMapping("/create")
    public String delicious_spot_create_get() {
        return "admin/delicious_spot_create";
    }

    @PostMapping("/create")
    public String delicious_spot_create_post(AdminDeliciousSpotCreateRequestDto dto) throws IOException {
        List<DeliciousSpotImage> imageList = new ArrayList<>();
        for (MultipartFile file: dto.getFiles()) {
            String url = uploadService.saveFile(file);
            imageList.add(new DeliciousSpotImage(url));
        }
        String url = uploadService.saveFile(dto.getImage());
        DeliciousSpot deliciousSpot = new DeliciousSpot(dto);
        deliciousSpot.setImageUrl(url);
        deliciousSpot.setDeliciousSpotImages(imageList);
        deliciousSpotRepository.addDeliciousSpot(deliciousSpot);
        return "redirect:/admin/delicious_spot/list";
    }

}
