package com.culturefinder.songdodongnae.curation.controller;

import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.service.CurationService;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.ResponseContainer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "curation API", description = "큐레이션 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/curations")
@RestController
public class CurationController {

    private final CurationService curationService;

    @Operation(summary = "큐레이션 모두 조회", description = "큐레이션을 모두 조회합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 모두 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseContainer<CustomPage<CurationResDto>>> getCuration(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            CustomPage<CurationResDto> allCurations = curationService.getAllCuration(currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 모두 조회 성공", allCurations);
        } else {
            Long userId = Long.parseLong(authentication.getName());
            CustomPage<CurationResDto> allUserCuration = curationService.getAllUserCuration(userId, currentPage, pageSize);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 모두 조회 성공", allUserCuration);
        }
    }

    @Operation(summary = "큐레이션 조회", description = "큐레이션 하나를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> getCuration(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            CurationResDto curationResDto = curationService.getCuration(id);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 조회 성공", curationResDto);
        } else {
            Long userId = Long.parseLong(authentication.getName());
            CurationResDto userCuration = curationService.getUserCuration(userId, id);
            return ResponseContainer.create(HttpStatus.OK, "큐레이션 조회 성공", userCuration);
        }
    }

    @Operation(summary = "큐레이션 생성", description = "큐레이션 하나를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 생성 성공")
    @PostMapping
    public ResponseEntity<ResponseContainer<CurationResDto>> createCuration(@Valid @RequestBody CurationReqDto curationReqDto) {
        CurationResDto curationResDto = curationService.createCuration(curationReqDto);
        return ResponseContainer.create(HttpStatus.CREATED, "큐레이션 생성 성공", curationResDto);
    }

    @Operation(summary = "큐레이션 수정", description = "큐레이션 하나를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 수정 성공")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> updateCuration(@PathVariable Long id, @Valid @RequestBody CurationReqDto curationReqDto){
        CurationResDto curationResDto = curationService.updateCuration(id, curationReqDto);
        return ResponseContainer.create(HttpStatus.OK, "큐레이션 수정 성공", curationResDto);
    }

    @Operation(summary = "큐레이션 삭제", description = "큐레이션 하나를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "큐레이션 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseContainer<CurationResDto>> deleteCuration(@PathVariable Long id){
        CurationResDto curationResDto = curationService.deleteCuration(id);
        return ResponseContainer.create(HttpStatus.OK, "큐레이션 삭제 성공", curationResDto);
    }
}
