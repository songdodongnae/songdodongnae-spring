package com.culturefinder.songdodongnae.chat.service;

import com.culturefinder.songdodongnae.chat.domain.ChatMessage;
import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import com.culturefinder.songdodongnae.chat.dto.ChatMessageDto;
import com.culturefinder.songdodongnae.chat.repository.ChatMessageRepository;
import com.culturefinder.songdodongnae.chat.repository.ChatRoomRepository;
import com.culturefinder.songdodongnae.common.exception.CustomException;
import com.culturefinder.songdodongnae.common.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class chatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void saveMessage(ChatMessageDto chatMessageDto) {
        User user = userRepository.findById(chatMessageDto.getSenderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        ChatMessage chatMessage = ChatMessageDto.toEntity(chatMessageDto, chatRoom, user);
        chatMessageRepository.save(chatMessage);
    }

    public void checkPermission(ChatMessageDto chatMessageDto) {
        userRepository.findById(chatMessageDto.getSenderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        chatRoomRepository.findById(chatMessageDto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    }
}
