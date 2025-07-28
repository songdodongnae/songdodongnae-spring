package com.culturefinder.songdodongnae.mypage;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final AuthService authService;

    @Operation(summary = "북마크 조회", description = "북마크 타입별로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "북마크 조회 성공")
    @GetMapping("/bookmark/{type}")
    public ResponseEntity<ResponseContainer<CustomPage<ThumbnailResDto>>> getPostByType(
            @PathVariable BookmarkType type,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {

        Long userId = authService.getAuthenticatedUserId();
        CustomPage<ThumbnailResDto> dto = myPageService.getPostByType(userId, type, currentPage, pageSize);
        return ResponseContainer.create(HttpStatus.OK, "북마크 조회 성공", dto);
    }

    @Operation(summary = "닉네임 변경", description = "닉네임을 변경합니다.")
    @ApiResponse(responseCode = "200", description = "닉네임 변경 성공")
    @PutMapping("/nickname")
    public ResponseEntity<ResponseContainer<String>> updateNickName(
            @Valid @RequestBody NickNameReqDto nickNameReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        String dto = myPageService.updateNickName(nickNameReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "닉네임 변경 성공", dto);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 합니다.")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseContainer<String>> deleteUser() {
        Long userId = authService.getAuthenticatedUserId();
        myPageService.deleteUser(userId);
        return ResponseContainer.create(HttpStatus.OK, "회원 탈퇴 성공", "회원 탈퇴가 완료되었습니다.");
    }

}
