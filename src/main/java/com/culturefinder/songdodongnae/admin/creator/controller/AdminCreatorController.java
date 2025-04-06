package com.culturefinder.songdodongnae.admin.creator.controller;

import com.culturefinder.songdodongnae.admin.creator.dto.AdminCreatorCreateRequestDto;
import com.culturefinder.songdodongnae.admin.creator.dto.AdminCreatorResponseDto;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/list")
    public String creator_list_get(Model model) {
        // TODO: 6
//        List<AdminCreatorResponseDto> creatorList = creatorRepository.findAllCreator()
//                .stream().map(AdminCreatorResponseDto::new).toList();
//        model.addAttribute("creatorList", creatorList);
        return "admin/creator_list";
    }

    @GetMapping("/create")
    public String creator_create_get() {
        return "admin/creator_create";
    }

    @PostMapping("/create")
    public String creator_create_post(AdminCreatorCreateRequestDto dto) throws IOException {
        if (dto.getName() == null|| dto.getIntroduction() == null || dto.getDescription() == null) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        if (dto.getName().isBlank() || dto.getIntroduction().isBlank() || dto.getDescription().isBlank()) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        if (dto.getFile().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_FILE);
        }

        String imageUrl = uploadService.saveFile(dto.getFile());
        Creator creator = Creator.builder()
                .name(dto.getName())
                .introduction(dto.getIntroduction())
                .description(dto.getDescription())
                .imageUrl(imageUrl)
                .build();
        creatorRepository.saveCreator(creator);
        return "redirect:/admin/creator";
    }

}
