package com.culturefinder.songdodongnae.chat.dto;

import com.culturefinder.songdodongnae.chat.domain.ChatMessage;
import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import com.culturefinder.songdodongnae.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime timestamp;

    public static ChatMessage toEntity(ChatMessageDto chatMessageDto, ChatRoom chatRoom, User user) {
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .sendUser(user)
                .content(chatMessageDto.getContent())
                .timeStamp(chatMessageDto.getTimestamp())
                .build();
    }

}