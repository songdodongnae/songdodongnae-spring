package com.culturefinder.songdodongnae.bookmark.controller;

import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
import com.culturefinder.songdodongnae.bookmark.service.BookmarkService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/create")
    @Operation(summary = "북마크 생성")
    public void createBookmark(@Valid @RequestBody BookmarkReqDto bookmarkReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        bookmarkService.createBookmark(bookmarkReqDto, userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "북마크 삭제")
    public void deleteBookmark(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
    }

    @GetMapping
    @Operation(summary = "유저의 북마크 조회")
    public void getUserBookmarks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        List<BookmarkResDto> dtos = bookmarkService.findUserBookmarks(userId).stream().map(BookmarkResDto::fromEntity).toList();
        for (BookmarkResDto dto : dtos) {
            System.out.println(dto);
        }
    }
}
