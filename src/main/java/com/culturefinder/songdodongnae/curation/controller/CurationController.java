package com.culturefinder.songdodongnae.curation.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.user.service.AuthService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "큐레이션 생성", description = "큐레이션 하나를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<CurationResDto>> createCuration(@Valid @RequestBody CurationReqDto curationReqDto) {
        Long userId = authService.getAuthenticatedUserId();
        CurationResDto curationResDto = curationService.createCuration(userId, curationReqDto);
        return ResponseContainer.create(HttpStatus.CREATED, "큐레이션 생성 성공", curationResDto);
    }

    @Operation(summary = "큐레이션 조회", description = "큐레이션 하나를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> getCuration(@PathVariable Long id) {
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
    @ApiResponse(responseCode = "200", description = "큐레이션 모두 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<CurationThumbnailResDto>>> getCuration(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
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
    @ApiResponse(responseCode = "200", description = "큐레이션 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> updateCuration(@PathVariable Long id, @Valid @RequestBody CurationReqDto curationReqDto){
        Long userId = authService.getAuthenticatedUserId();
        CurationResDto curationResDto = curationService.updateCuration(userId, id, curationReqDto);
        return ResponseContainer.create(HttpStatus.OK, "큐레이션 수정 성공", curationResDto);
    }

    @Operation(summary = "큐레이션 삭제", description = "큐레이션 하나를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> deleteCuration(@PathVariable Long id){
        Long userId = authService.getAuthenticatedUserId();
        CurationResDto curationResDto = curationService.deleteCuration(userId, id);
        return ResponseContainer.create(HttpStatus.OK, "큐레이션 삭제 성공", curationResDto);
    }
}
