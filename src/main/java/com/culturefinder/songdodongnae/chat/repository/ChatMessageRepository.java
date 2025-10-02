package com.culturefinder.songdodongnae.chat.repository;

import com.culturefinder.songdodongnae.chat.domain.ChatMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(ChatMessage chatMessage) {
        em.persist(chatMessage);
    }
}
