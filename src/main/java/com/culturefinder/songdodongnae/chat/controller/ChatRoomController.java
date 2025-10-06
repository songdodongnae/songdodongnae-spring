package com.culturefinder.songdodongnae.chat.controller;


import com.culturefinder.songdodongnae.chat.dto.ChatRoomResDto;
import com.culturefinder.songdodongnae.chat.service.ChatRoomService;
import com.culturefinder.songdodongnae.common.utils.ResponseContainer;
import com.culturefinder.songdodongnae.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final AuthService authService;

    @PostMapping("/{boardId}")
    public ResponseEntity<ResponseContainer<ChatRoomResDto>> createChatRoom(
            @PathVariable Long boardId
    ) {
        Long userId = authService.getAuthenticatedUserId();
        ChatRoomResDto dto = chatRoomService.createChatRoom(userId, boardId);
        return ResponseContainer.create(HttpStatus.OK, "채팅방 생성 성공", dto);
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<ResponseContainer<ChatRoomResDto>> leaveChatRoom(
            @PathVariable Long chatRoomId
    ) {
        Long userId = authService.getAuthenticatedUserId();
        ChatRoomResDto dto = chatRoomService.leaveChatRoom(userId, chatRoomId);
        return ResponseContainer.create(HttpStatus.OK, "채팅방 나가기 성공", dto);
    }



}
