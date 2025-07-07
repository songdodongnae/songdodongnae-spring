package com.culturefinder.songdodongnae.bookmark.controller;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 생성")
    @PostMapping("/create")
    public void createBookmark(@Valid @RequestBody BookmarkReqDto bookmarkReqDto) {

    }

}
