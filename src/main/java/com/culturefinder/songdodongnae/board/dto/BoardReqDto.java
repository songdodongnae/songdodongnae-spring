package com.culturefinder.songdodongnae.board.dto;

import com.culturefinder.songdodongnae.board.domain.Board;
import lombok.Getter;


@Getter
public class BoardReqDto {
    private String title;
    private String content;

    public static Board toEntity(BoardReqDto reqDto) {
        return Board.builder()
                .build();
    }
}
