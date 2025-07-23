package com.culturefinder.songdodongnae.bookmark.controller;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
import com.culturefinder.songdodongnae.bookmark.service.BookmarkService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseContainer<BookmarkResDto>> createBookmark(@Valid @RequestBody BookmarkReqDto bookmarkReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        Bookmark bookmark = bookmarkService.createBookmark(bookmarkReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "북마크 생성 성공", BookmarkResDto.fromEntity(bookmark));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "북마크 삭제")
    public ResponseEntity<ResponseContainer<BookmarkResDto>> deleteBookmark(@PathVariable Long id) {
        Bookmark bookmark = bookmarkService.deleteBookmark(id);
        return ResponseContainer.create(HttpStatus.OK, "북마크 삭제 성공", BookmarkResDto.fromEntity(bookmark));
    }

//    @GetMapping
//    @Operation(summary = "유저의 북마크 조회")
//    public ResponseEntity<ResponseContainer<List<BookmarkResDto>>> getUserBookmarks() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long userId = Long.parseLong(authentication.getName());
//        List<BookmarkResDto> dtos = bookmarkService.findUserBookmarks(userId).stream().map(BookmarkResDto::fromEntity).toList();
//        return ResponseContainer.create(HttpStatus.OK, "유저 북마크 조회 성공", dtos);
//    }
}
