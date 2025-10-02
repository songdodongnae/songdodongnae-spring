package com.culturefinder.songdodongnae.chat.repository;

import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    @PersistenceContext
    private final EntityManager em;

    public Optional<ChatRoom> findById(Long id) {
        ChatRoom chatRoom = em.find(ChatRoom.class, id);
        return Optional.ofNullable(chatRoom);
    }

}
