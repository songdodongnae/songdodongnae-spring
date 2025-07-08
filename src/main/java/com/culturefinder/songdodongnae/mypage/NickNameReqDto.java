package com.culturefinder.songdodongnae.mypage;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NickNameReqDto {

    @NotNull
    private String nickName;
}
