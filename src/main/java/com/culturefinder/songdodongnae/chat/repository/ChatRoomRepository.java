package com.culturefinder.songdodongnae.chat.repository;

import com.culturefinder.songdodongnae.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT cr FROM ChatRoom cr " +
            "JOIN cr.chatRoomUsers cru1 " +
            "JOIN cr.chatRoomUsers cru2 " +
            "WHERE cru1.user.id = :userId1 " +
            "AND cru2.user.id = :userId2 " +
            "AND cru1.id <> cru2.id " +
            "AND cr.board.id = :boardId " +
            "AND SIZE(cr.chatRoomUsers) = 2")
    Optional<ChatRoom> findByTwoUsersAndBoard(Long userId1, Long userId2, Long boardId);

    @Query("SELECT DISTINCT cr FROM ChatRoom cr " +
            "JOIN FETCH cr.chatRoomUsers cru " +
            "WHERE cru.user.id = :userId " +
            "ORDER BY cr.updatedAt DESC")
    List<ChatRoom> findByUserId(Long userId);

}
