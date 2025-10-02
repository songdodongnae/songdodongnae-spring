package com.culturefinder.songdodongnae.board.repository;

import com.culturefinder.songdodongnae.board.domain.Board;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Repository
public class BoardRepository {

    @PersistenceContext
    private final EntityManager em;

    public Board saveBoard(Board board) {
        em.persist(board);
        return board;
    }


    public List<Board> findAll() {
        return em.createQuery("SELECT b from Board b", Board.class)
                .getResultList();
    }




}
