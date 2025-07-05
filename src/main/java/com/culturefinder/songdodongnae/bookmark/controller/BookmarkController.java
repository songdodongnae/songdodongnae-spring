package com.culturefinder.songdodongnae.bookmark.controller;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.service.BookmarkService;
import com.culturefinder.songdodongnae.user.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final JwtService jwtService;

    @Operation(summary = "북마크 생성")
    @PostMapping("/create")
    public void createBookmark(HttpServletRequest request, BookmarkReqDto bookmarkReqDto) {
        String accessToken = jwtService.extractAccessToken(request).get();
        Long userId = jwtService.extractId(accessToken).get();
        bookmarkService.createBookmark(bookmarkReqDto, userId);
    }

}
