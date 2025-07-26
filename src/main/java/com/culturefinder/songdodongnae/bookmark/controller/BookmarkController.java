package com.culturefinder.songdodongnae.bookmark.controller;

import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
import com.culturefinder.songdodongnae.bookmark.service.BookmarkService;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "북마크 생성")
    public ResponseEntity<ResponseContainer<BookmarkResDto>> createBookmark(@Valid @RequestBody BookmarkReqDto bookmarkReqDto) {

        Authentication authentication = isAuthenticatedUser();

        Long userId = Long.parseLong(authentication.getName());
        BookmarkResDto bookmark = bookmarkService.createBookmark(bookmarkReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "북마크 생성 성공", bookmark);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "북마크 삭제")
    public ResponseEntity<ResponseContainer<BookmarkResDto>> deleteBookmark(@PathVariable Long id) {

        Authentication authentication = isAuthenticatedUser();

        Long userId = Long.parseLong(authentication.getName());
        BookmarkResDto bookmark = bookmarkService.deleteBookmark(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "북마크 삭제 성공", bookmark);
    }

    private static Authentication isAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticatedUser = authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String &&
                        authentication.getPrincipal().equals("anonymousUser"));
        if(!isAuthenticatedUser) throw new CustomException(ErrorCode.FORBIDDEN);
        return authentication;
    }
}
