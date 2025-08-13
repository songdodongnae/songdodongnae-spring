package com.culturefinder.songdodongnae.mypage;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "MyPage API", description = "마이페이지 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final AuthService authService;


    @Operation(summary = "북마크 조회")
    @ApiResponse(responseCode = "200", description = "북마크 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping("/bookmark/{type}")
    public ResponseEntity<ResponseContainer<CustomPage<ThumbnailResDto>>> getPostByType(
            @Parameter(description = "북마크 타입", required = true) @PathVariable BookmarkType type,
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize) {

        Long userId = authService.getAuthenticatedUserId();
        CustomPage<ThumbnailResDto> dto = myPageService.getPostByType(userId, type, currentPage, pageSize);
        return ResponseContainer.create(HttpStatus.OK, "북마크 조회 성공", dto);
    }

    @Operation(summary = "닉네임 변경")
    @ApiResponse(responseCode = "200", description = "닉네임 변경 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @PutMapping("/nickname")
    public ResponseEntity<ResponseContainer<String>> updateNickName(
            @Valid @RequestBody NickNameReqDto nickNameReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        String dto = myPageService.updateNickName(nickNameReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "닉네임 변경 성공", dto);
    }

    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseContainer<String>> deleteUser() {
        Long userId = authService.getAuthenticatedUserId();
        myPageService.deleteUser(userId);
        return ResponseContainer.create(HttpStatus.OK, "회원 탈퇴 성공", "회원 탈퇴가 완료되었습니다.");
    }

}
