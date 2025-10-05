package com.culturefinder.songdodongnae.chat.dto;

import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ChatRoomResDto {
    private Long id;

    public static ChatRoomResDto fromEntity(ChatRoom chatRoom) {
        return ChatRoomResDto.builder()
                .id(chatRoom.getId())
                .build();
    }

}
