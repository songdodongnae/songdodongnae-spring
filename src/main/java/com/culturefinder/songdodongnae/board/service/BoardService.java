package com.culturefinder.songdodongnae.board.service;

import com.culturefinder.songdodongnae.board.domain.Board;
import com.culturefinder.songdodongnae.board.dto.BoardReqDto;
import com.culturefinder.songdodongnae.board.dto.BoardResDto;
import com.culturefinder.songdodongnae.board.repository.BoardRepository;
import com.culturefinder.songdodongnae.common.exception.CustomException;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.culturefinder.songdodongnae.common.exception.ErrorCode.ENTITY_NOT_FOUND;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardResDto createBoard(BoardReqDto boardReqDto, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        Board savedBoard = boardRepository.saveBoard(BoardReqDto.toEntity(boardReqDto, findUser));
        return BoardResDto.from(savedBoard);
    }

    public List<BoardResDto> getAllBoard() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResDto::from)
                .toList();
    }

    public BoardResDto getBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        return BoardResDto.from(findBoard);
    }


}
