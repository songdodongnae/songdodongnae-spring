package com.culturefinder.songdodongnae.user.dto;

import com.culturefinder.songdodongnae.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResDto {

    private Long id;
    private String nickName;

    public static UserResDto fromEntity(User findUser) {
        return UserResDto.builder()
                .id(findUser.getId())
                .nickName(findUser.getNickname())
                .build();
    }
}
