package com.culturefinder.songdodongnae.board.dto;

import com.culturefinder.songdodongnae.board.domain.Board;
import com.culturefinder.songdodongnae.user.domain.User;
import lombok.Getter;


@Getter
public class BoardReqDto {

    private String title;
    private String content;

    public static Board toEntity(BoardReqDto reqDto, User user) {
        return Board.builder()
                .title(reqDto.getTitle())
                .content(reqDto.getContent())
                .user(user)
                .build();
    }
}
