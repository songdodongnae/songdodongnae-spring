package com.culturefinder.songdodongnae.user.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class NickNameReqDto {

    @NotBlank(message = "닉네임은 필수입니다")
    @Schema(description = "변경할 닉네임", example = "도동")
    private String nickName;
}
