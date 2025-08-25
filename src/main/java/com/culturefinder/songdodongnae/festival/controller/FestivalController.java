package com.culturefinder.songdodongnae.festival.controller;

import com.culturefinder.songdodongnae.exception.ErrorDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
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

import java.util.List;


@Tag(name = "Festival API", description = "축제 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    private final FestivalService festivalService;
    private final AuthService authService;

    @Operation(summary = "축제 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "축제 생성 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "400", description = "필수 필드 누락 또는 잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음 (ADMIN 권한 필요)", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "크리에이터를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseContainer<FestivalResDto>> createFestival(
            @Valid @RequestBody FestivalReqDto festivalReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        FestivalResDto dto = festivalService.createFestival(festivalReqDto, userId);
        return ResponseContainer.create(HttpStatus.CREATED, "축제 생성 성공", dto);
    }

    @Operation(summary = "축제 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "축제 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "404", description = "축제를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> getFestival(
            @Parameter(description = "축제 ID", required = true) @PathVariable Long id) {
        if (!authService.isAuthenticatedUser()) {
            FestivalResDto dto = festivalService.getFestival(id);
            return ResponseContainer.create(HttpStatus.OK, "축제 조회 성공", dto);
        }
        else {
            Long userId = authService.getAuthenticatedUserId();
            FestivalResDto dto = festivalService.getUserFestival(id, userId);
            return ResponseContainer.create(HttpStatus.OK, "축제 조회 성공", dto);
        }
    }


    @Operation(summary = "해당 년/월 축제 조회")
    @ApiResponse(responseCode = "200", description = "해당 월 축제 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping("/day")
    public ResponseEntity<ResponseContainer<List<FestivalThumbnailResDto>>> getFestivalsByYearAndMonth(
            @Parameter(description = "연도", required = true, example = "2024") @RequestParam int year,
            @Parameter(description = "월", required = true, example = "12") @RequestParam int month) {
        if (!authService.isAuthenticatedUser()) {
            List<FestivalThumbnailResDto> dtos =  festivalService.getFestivalsByYearAndMonth(year, month);
            return ResponseContainer.create(HttpStatus.OK, "년/월 해당 축제 조회 성공", dtos);
        } else {
            Long userId = authService.getAuthenticatedUserId();
            List<FestivalThumbnailResDto> dtos =  festivalService.getFestivalsUserByYearAndMonth(year, month, userId);
            return ResponseContainer.create(HttpStatus.OK, "년/월 해당 축제 조회 성공", dtos);
        }
    }

    @Operation(summary = "모든 축제 조회")
    @ApiResponse(responseCode = "200", description = "모든 축제 조회 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class)))
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<FestivalThumbnailResDto>>> getAllFestivals(
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllFestival(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllUserFestival(currentPage, pageSize, userId);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }

    }

    @GetMapping("/v2")
    public ResponseEntity<ResponseContainer<CustomPage<FestivalThumbnailResDto>>> getAllFestivalsV2(
            @Parameter(description = "현재 페이지 번호", example = "1") @RequestParam(defaultValue = "1") int currentPage,
            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int pageSize) {
        if (!authService.isAuthenticatedUser()) {
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllFestivalV2(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }else {
            Long userId = authService.getAuthenticatedUserId();
            CustomPage<FestivalThumbnailResDto> dtos = festivalService.getAllUserFestivalV2(currentPage, pageSize, userId);
            return ResponseContainer.create(HttpStatus.OK, "모든 축제 조회 성공", dtos);
        }

    }

    @Operation(summary = "축제 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "축제 수정 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "400", description = "필수 필드 누락 또는 잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음 (ADMIN 권한 필요)", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "축제 또는 크리에이터를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> updateFestival(
            @Parameter(description = "축제 ID", required = true) @PathVariable Long id,
            @Valid @RequestBody FestivalReqDto festivalReqDto)  {
        Long userId = authService.getAuthenticatedUserId();
        FestivalResDto dto = festivalService.updateFestival(id, festivalReqDto, userId);
        return ResponseContainer.create(HttpStatus.OK, "축제 수정 성공", dto);
    }

    @Operation(summary = "축제 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "축제 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseContainer.class))),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없음 (ADMIN 권한 필요)", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "축제를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<FestivalResDto>> deleteFestival(
            @Parameter(description = "축제 ID", required = true) @PathVariable Long id) {
        Long userId = authService.getAuthenticatedUserId();
        FestivalResDto dto = festivalService.deleteFestival(id, userId);
        return ResponseContainer.create(HttpStatus.OK, "축제 삭제 성공", dto);
    }

}