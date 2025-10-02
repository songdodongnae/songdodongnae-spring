package com.culturefinder.songdodongnae.board.controller;

import com.culturefinder.songdodongnae.board.dto.BoardReqDto;
import com.culturefinder.songdodongnae.board.dto.BoardResDto;
import com.culturefinder.songdodongnae.board.service.BoardService;
import com.culturefinder.songdodongnae.common.utils.ResponseContainer;
import com.culturefinder.songdodongnae.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseContainer<BoardResDto>> createBoard(@Valid @RequestBody BoardReqDto boardReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        BoardResDto dto = boardService.createBoard(boardReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "게시글 생성 성공", dto);
    }

    @GetMapping
    public ResponseEntity<ResponseContainer<List<BoardResDto>>> getAllBoard() {
        List<BoardResDto> dto = boardService.getAllBoard();
        return ResponseContainer.create(HttpStatus.OK, "게시글 모두 조회 성공", dto);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseContainer<BoardResDto>> getBoard(
            @PathVariable Long boardId
    ){
        BoardResDto dto = boardService.getBoard(boardId);
        return ResponseContainer.create(HttpStatus.OK, "게시글 조회 성공", dto);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseContainer<BoardResDto>> updateBoard(
            @PathVariable Long boardId, @Valid @RequestBody BoardReqDto boardReqDto
    ){
        Long userId = authService.getAuthenticatedUserId();
        BoardResDto dto = boardService.updateBoard(boardId, boardReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "게시글 수정 성공", dto);
    }


    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseContainer<Void>> deleteBoard(
            @PathVariable Long boardId
    ){
        Long userId = authService.getAuthenticatedUserId();
        boardService.deleteBoard(boardId, userId);
        return ResponseContainer.create(HttpStatus.OK, "게시글 삭제 성공", null);
    }








}
