package com.culturefinder.songdodongnae.board.controller;

import com.culturefinder.songdodongnae.board.dto.BoardReqDto;
import com.culturefinder.songdodongnae.board.dto.BoardResDto;
import com.culturefinder.songdodongnae.board.service.BoardService;
import com.culturefinder.songdodongnae.common.utils.ResponseContainer;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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






}
