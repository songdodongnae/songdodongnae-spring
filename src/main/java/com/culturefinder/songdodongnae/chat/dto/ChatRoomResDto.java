package com.culturefinder.songdodongnae.chat.dto;

import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import com.culturefinder.songdodongnae.user.dto.UserResDto;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ChatRoomResDto {
    private Long id;
    private String title;
    private UserResDto user;

    public static ChatRoomResDto fromEntity(ChatRoom chatRoom, UserResDto user) {
        return ChatRoomResDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getBoard().getTitle())
                .user(user)
                .build();
    }

}
