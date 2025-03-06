package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/admin/creator")
@RequiredArgsConstructor
public class AdminCreatorController {

    private final CreatorRepository creatorRepository;
    private final S3UploadService uploadService;

    @GetMapping
    public String creator_get() {
        return "admin/creator";
    }

    @GetMapping("/create")
    public String creator_create_get() {
        return "admin/creator_create";
    }

    @PostMapping("/create")
    public String creator_create_post(
            @RequestPart("name") String name,
            @RequestPart("introduction") String introduction,
            @RequestPart("description") String description,
            @RequestPart("file") MultipartFile file
    ) throws Exception {
        String imageUrl = uploadService.saveFile(file);
        Creator creator = Creator.builder()
                .name(name)
                .introduction(introduction)
                .description(description)
                .imageUrl(imageUrl)
                .build();
        creatorRepository.saveCreator(creator);
        return "redirect:/admin/creator";
    }

}
