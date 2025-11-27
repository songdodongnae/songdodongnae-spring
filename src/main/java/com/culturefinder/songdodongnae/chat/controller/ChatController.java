package com.culturefinder.songdodongnae.chat.controller;

import com.culturefinder.songdodongnae.chat.dto.ChatMessageDto;
import com.culturefinder.songdodongnae.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto message) {
        chatMessageService.saveMessage(message);
    }

}