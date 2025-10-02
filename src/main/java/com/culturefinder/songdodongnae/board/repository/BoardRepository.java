package com.culturefinder.songdodongnae.board.repository;

import com.culturefinder.songdodongnae.board.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    public void deleteBoard(Board findBoard) {
        em.remove(findBoard);
    }


}
