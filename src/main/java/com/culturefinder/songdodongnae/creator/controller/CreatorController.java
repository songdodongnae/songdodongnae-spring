package com.culturefinder.songdodongnae.creator.controller;

import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.service.CreatorService;
import com.culturefinder.songdodongnae.exception.ErrorDto;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Creators API", description = "크리에이터 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/creators")
@RestController
public class CreatorController {

    private final CreatorService creatorService;
    private final AuthService authService;


    @Operation(summary = "크리에이터 생성")
    @ApiResponse(responseCode = "201", description = "크리에이터 생성 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @PostMapping
    public ResponseEntity<ResponseContainer<CreatorResDto>> createCreator(@Valid @RequestBody CreatorReqDto creatorReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        CreatorResDto dto = creatorService.createCreator(creatorReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 생성 성공", dto);
    }

    @Operation(summary = "크리에이터 모두 조회")
    @ApiResponse(responseCode = "200", description = "크리에이터 썸네일 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<CreatorThumbnailResDto>>> getAllCreator(
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize
    ) {
        CustomPage<CreatorThumbnailResDto> dtos = creatorService.getAllCreator(currentPage, pageSize);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 썸네일 목록 조회 성공", dtos);
    }

    @Operation(summary = "크리에이터 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "크리에이터 상세 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> getCreator(
            @Parameter(description = "크리에이터 ID", required = true) @PathVariable Long id) {
        if (!authService.isAuthenticatedUser()) {
            CreatorResDto dto = creatorService.getCreator(id);
            return ResponseContainer.create(HttpStatus.OK, "크리에이터 상세 조회 성공", dto);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CreatorResDto dto = creatorService.getCreator(id, userId);
            return ResponseContainer.create(HttpStatus.OK, "크리에이터 상세 조회 성공", dto);
        }
    }

    @Operation(summary = "크리에이터 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "크리에이터 상세 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/v2/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> getCreatorV2(
            @Parameter(description = "크리에이터 ID", required = true) @PathVariable Long id) {
        if (!authService.isAuthenticatedUser()) {
            CreatorResDto dto = creatorService.getCreatorV2(id);
            return ResponseContainer.create(HttpStatus.OK, "크리에이터 상세 조회 성공", dto);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CreatorResDto dto = creatorService.getCreatorV2(id, userId);
            return ResponseContainer.create(HttpStatus.OK, "크리에이터 상세 조회 성공", dto);
        }
    }

    @Operation(summary = "크리에이터 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "크리에이터 수정 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> updateCreator(
            @Parameter(description = "크리에이터 ID", required = true) @PathVariable Long id,
            @Valid @RequestBody CreatorReqDto creatorReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        CreatorResDto dto = creatorService.updateCreator(id, creatorReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 수정 성공", dto);
    }

    @Operation(summary = "크리에이터 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "크리에이터 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<CreatorResDto>> deleteCreator(
            @Parameter(description = "크리에이터 ID", required = true) @PathVariable Long id) {
        Long userId = authService.getAuthenticatedUserId();

        CreatorResDto dto = creatorService.deleteCreator(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "크리에이터 삭제 성공", dto);
    }

}
