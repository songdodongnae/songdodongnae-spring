package com.culturefinder.songdodongnae.creator.controller;

import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.service.CreatorService;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Creators API", description = "크리에이터 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/creators")
@RestController
public class CreatorController {

    private final CreatorService creatorService;

    @Operation(summary = "크리에이터 생성", description = "크리에이터를 생성합니다.")
    @ApiResponse(responseCode = "201", description = "크리에이터 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<CreatorResDto>> createCreator(@Valid @RequestBody CreatorReqDto creatorReqDto) {
        Authentication authentication = isAuthenticatedUser();

        Long userId = Long.parseLong(authentication.getName());
        CreatorResDto dto = creatorService.createCreator(creatorReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 생성 성공", dto);
    }

    @Operation(summary = "크리에이터 모두 조회", description = "크리에이터 썸네일 목록 조회합니다.")
    @ApiResponse(responseCode = "200", description = "크리에이터 썸네일 목록 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<List<CreatorThumbnailResDto>>> getAllCreator() {
        List<CreatorThumbnailResDto> dtos = creatorService.getAllCreator();
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 썸네일 목록 조회 성공", dtos);
    }

    @Operation(summary = "크리에이터 상세 조회", description = "크리에이터 상세 조회합니다.")
    @ApiResponse(responseCode = "200", description = "크리에이터 상세 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> getCreator(@PathVariable Long id) {
        CreatorResDto dto = creatorService.getCreator(id);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 상세 조회 성공", dto);
    }

    @Operation(summary = "크리에이터 수정", description = "크리에이터 수정합니다.")
    @ApiResponse(responseCode = "200", description = "크리에이터 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> updateCreator(@PathVariable Long id,@Valid @RequestBody CreatorReqDto creatorReqDto) {
        Authentication authentication = isAuthenticatedUser();

        Long userId = Long.parseLong(authentication.getName());
        CreatorResDto dto = creatorService.updateCreator(id, creatorReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 수정 성공", dto);
    }

    @Operation(summary = "크리에이터 삭제", description = "크리에이터 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "크리에이터 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> deleteCreator(@PathVariable Long id) {

        Authentication authentication = isAuthenticatedUser();

        Long userId = Long.parseLong(authentication.getName());
        CreatorResDto dto = creatorService.deleteCreator(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 삭제 성공", dto);
    }

    private static Authentication isAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticatedUser = authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String &&
                        authentication.getPrincipal().equals("anonymousUser"));
        if(!isAuthenticatedUser) throw new CustomException(ErrorCode.FORBIDDEN);
        return authentication;
    }
}
