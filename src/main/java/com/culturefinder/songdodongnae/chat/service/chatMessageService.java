package com.culturefinder.songdodongnae.chat.service;

import com.culturefinder.songdodongnae.chat.dto.ChatMessageDto;
import com.culturefinder.songdodongnae.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class chatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessageDto chatMessageDto) {

    }

    public void checkPermission(ChatMessageDto chatMessageDto) {

    }
}
