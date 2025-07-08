package com.culturefinder.songdodongnae.mypage;

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

    @Operation(summary = "닉네임 변경", description = "닉네임을 변경합니다.")
    @ApiResponse(responseCode = "200", description = "닉네임 변경 성공")
    @PutMapping("/nickname")
    public ResponseEntity<ResponseContainer<String>> updateNickName(
            @Valid @RequestBody NickNameReqDto nickNameReqDto) {

        String dto = myPageService.updateNickName(nickNameReqDto);
        return new ResponseContainer<>(HttpStatus.OK, "닉네임 변경 성공", dto).toResponseEntity();
    }



}
