package com.culturefinder.songdodongnae.board.dto;

import com.culturefinder.songdodongnae.board.domain.Board;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardResDto {

    private Long id;
    private String title;
    private String content;
    private Long userId;

    public static BoardResDto from(Board board) {
        return BoardResDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUser().getId())
                .build();
    }

}
