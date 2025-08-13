package com.culturefinder.songdodongnae.bookmark.controller;

import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
import com.culturefinder.songdodongnae.bookmark.service.BookmarkService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Bookmark API", description = "북마크 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final AuthService authService;

    @PostMapping
    @Operation(summary = "북마크 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 추가 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 타겟 ID"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    public ResponseEntity<ResponseContainer<BookmarkResDto>> createBookmark(@Valid @RequestBody BookmarkReqDto bookmarkReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        BookmarkResDto bookmark = bookmarkService.createBookmark(bookmarkReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "북마크 생성 성공", bookmark);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "북마크 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "북마크 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 북마크를 찾을 수 없거나 북마크의 유저와 접속자가 불일치")
    })
    public ResponseEntity<ResponseContainer<BookmarkResDto>> deleteBookmark(
            @Parameter(description = "북마크 ID", required = true) @PathVariable Long id) {
        Long userId = authService.getAuthenticatedUserId();
        BookmarkResDto bookmark = bookmarkService.deleteBookmark(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "북마크 삭제 성공", bookmark);
    }

}
