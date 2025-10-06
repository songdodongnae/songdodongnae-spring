package com.culturefinder.songdodongnae.chat.service;

import com.culturefinder.songdodongnae.board.domain.Board;
import com.culturefinder.songdodongnae.board.repository.BoardRepository;
import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import com.culturefinder.songdodongnae.chat.domain.ChatRoomUser;
import com.culturefinder.songdodongnae.chat.dto.ChatRoomResDto;
import com.culturefinder.songdodongnae.chat.repository.ChatRoomRepository;
import com.culturefinder.songdodongnae.common.exception.CustomException;
import com.culturefinder.songdodongnae.common.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.dto.UserResDto;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public ChatRoomResDto createChatRoom(Long userId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByTwoUsersAndBoard(userId, board.getUser().getId(), boardId);
        if (existingChatRoom.isPresent()) {
            return ChatRoomResDto.fromEntity(existingChatRoom.get(), UserResDto.fromEntity(findUser));
        }

        ChatRoom newChatRoom = ChatRoom.builder()
                .board(board)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.saveChatRoom(newChatRoom);

        ChatRoomUser userA = ChatRoomUser.builder()
                .chatRoom(savedChatRoom)
                .user(findUser)
                .build();
        savedChatRoom.saveMember(userA);
        ChatRoomUser userB = ChatRoomUser.builder()
                .chatRoom(savedChatRoom)
                .user(board.getUser())
                .build();
        savedChatRoom.saveMember(userB);
        return ChatRoomResDto.fromEntity(savedChatRoom, UserResDto.fromEntity(findUser));
    }

    public ChatRoomResDto leaveChatRoom(Long userId, Long chatRoomId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        ChatRoomUser chatRoomUser = findChatRoom.getChatRoomUsers().stream()
                .filter(cru -> cru.getUser().equals(findUser))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        findChatRoom.leave(chatRoomUser);
        return ChatRoomResDto.fromEntity(findChatRoom, null);
    }

    public List<ChatRoomResDto> getAllChatRoom(Long userId) {
        return chatRoomRepository.findByUserId(userId).stream()
                .map(cr -> {
                    User otherUser = cr.getChatRoomUsers().stream()
                            .filter(cru -> !cru.getUser().getId().equals(userId))
                            .findFirst()
                            .map(ChatRoomUser::getUser)
                            .orElse(null);
                    return ChatRoomResDto.fromEntity(cr, otherUser != null ? UserResDto.fromEntity(otherUser) : null);
                })
                .toList();
    }

    public ChatRoomResDto getChatRoom(Long userId, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        chatRoom.getChatRoomUsers().stream()
                .filter(cru -> cru.getUser().equals(findUser))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        User otherUser = chatRoom.getChatRoomUsers().stream()
                .filter(cru -> !cru.getUser().getId().equals(userId))
                .findFirst()
                .map(ChatRoomUser::getUser)
                .orElse(null);

        return ChatRoomResDto.fromEntity(chatRoom, otherUser != null ? UserResDto.fromEntity(otherUser) : null);
    }
}
