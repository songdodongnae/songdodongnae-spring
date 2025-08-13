package com.culturefinder.songdodongnae.curation.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
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


@Tag(name = "curation API", description = "큐레이션 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/curations")
@RestController
public class CurationController {

    private final CurationService curationService;
    private final AuthService authService;

    @Operation(summary = "큐레이션 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "큐레이션 생성 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseContainer<CurationResDto>> createCuration(@Valid @RequestBody CurationReqDto curationReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        CurationResDto curationResDto = curationService.createCuration(userId, curationReqDto);
        return ResponseContainer.create(HttpStatus.CREATED, "큐레이션 생성 성공", curationResDto);
    }

    @Operation(summary = "큐레이션 조회", description = "큐레이션 하나를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "큐레이션 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "404", description = "큐레이션을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> getCuration(
            @Parameter(description = "큐레이션 ID", required = true) @PathVariable Long id) {
        boolean authentication = authService.isAuthenticatedUser();

        if (!authentication) {
            CurationResDto curationResDto = curationService.getCuration(id);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 조회 성공", curationResDto);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CurationResDto userCuration = curationService.getUserCuration(userId, id);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 조회 성공", userCuration);
        }
    }

    @Operation(summary = "큐레이션 모두 조회", description = "큐레이션을 모두 조회합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 모두 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<CurationThumbnailResDto>>> getCuration(
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize
    ) {
        boolean authentication = authService.isAuthenticatedUser();

        if (!authentication) {
            CustomPage<CurationThumbnailResDto> allCurations = curationService.getAllCuration(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 모두 조회 성공", allCurations);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<CurationThumbnailResDto> allUserCuration = curationService.getAllUserCuration(userId, currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 모두 조회 성공", allUserCuration);
        }
    }

    @Operation(summary = "큐레이션 수정", description = "큐레이션 하나를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "큐레이션 수정 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "큐레이션을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> updateCuration(
            @Parameter(description = "큐레이션 ID", required = true) @PathVariable Long id,
            @Valid @RequestBody CurationReqDto curationReqDto){
        Long userId = authService.getAuthenticatedUserId();
        CurationResDto curationResDto = curationService.updateCuration(userId, id, curationReqDto);
        return ResponseContainer.create(HttpStatus.OK, "큐레이션 수정 성공", curationResDto);
    }

    @Operation(summary = "큐레이션 삭제", description = "큐레이션 하나를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "큐레이션 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "큐레이션을 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> deleteCuration(
            @Parameter(description = "큐레이션 ID", required = true) @PathVariable Long id){
        Long userId = authService.getAuthenticatedUserId();
        CurationResDto curationResDto = curationService.deleteCuration(userId, id);
        return ResponseContainer.create(HttpStatus.OK, "큐레이션 삭제 성공", curationResDto);
    }
}
