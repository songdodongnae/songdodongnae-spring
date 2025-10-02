package com.culturefinder.songdodongnae.chat.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    @PersistenceContext
    private final EntityManager em;

}
