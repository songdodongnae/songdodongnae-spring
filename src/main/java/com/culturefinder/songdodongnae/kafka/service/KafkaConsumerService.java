package com.culturefinder.songdodongnae.kafka.service;

import com.culturefinder.songdodongnae.chat.dto.ChatMessageDto;
import com.culturefinder.songdodongnae.chat.dto.ChatRoomResDto;
import com.culturefinder.songdodongnae.chat.service.chatMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;
    private final chatMessageService chatMessageService;

    @KafkaListener(topics = "chat.messages", groupId = "main-server-group")
    public void consumeChatMessage(String message) {
        try {
            log.info("Received message from chat.messages topic: {}", message);

            ChatMessageDto chatMessageDto = objectMapper.readValue(message, ChatMessageDto.class);
            chatMessageService.checkPermission(chatMessageDto);
            chatMessageService.saveMessage(chatMessageDto);

            String roomId = String.valueOf(chatMessageDto.getRoomId());
            kafkaProducerService.sendMessage("chat.messages", roomId, message);

        } catch (Exception e) {
            log.error("Failed to process chat message: {}", e.getMessage());
        }
    }

}