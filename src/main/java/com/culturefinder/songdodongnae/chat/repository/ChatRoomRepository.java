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

    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom;
    }

    public Optional<ChatRoom> findByTwoUsersAndBoard(Long userId1, Long userId2, Long boardId) {
        String jpql = "SELECT cr FROM ChatRoom cr " +
                      "JOIN cr.chatRoomUsers cru1 " +
                      "JOIN cr.chatRoomUsers cru2 " +
                      "WHERE cru1.user.id = :userId1 " +
                      "AND cru2.user.id = :userId2 " +
                      "AND cru1.id <> cru2.id " +
                      "AND cr.board.id = :boardId " +
                      "AND SIZE(cr.chatRoomUsers) = 2";

        return em.createQuery(jpql, ChatRoom.class)
                .setParameter("userId1", userId1)
                .setParameter("userId2", userId2)
                .setParameter("boardId", boardId)
                .getResultStream()
                .findFirst();
    }

}
