package com.culturefinder.songdodongnae.board.repository;

import com.culturefinder.songdodongnae.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
