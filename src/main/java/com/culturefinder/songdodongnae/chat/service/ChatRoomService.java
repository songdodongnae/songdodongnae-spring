package com.culturefinder.songdodongnae.chat.service;

import com.culturefinder.songdodongnae.chat.dto.ChatRoomResDto;
import com.culturefinder.songdodongnae.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomResDto createChatRoom(Long userId, Long boardId) {
        return null;
    }
}
