package com.culturefinder.songdodongnae.bookmark.dto;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Schema(description = "북마크 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class BookmarkReqDto {

    @Schema(description = "북마크 타입", example = "FESTIVAL")
    @NotNull
    private BookmarkType bookmarkType;

    @Schema(description = "북마크 대상 ID", example = "1")
    @NotNull
    private Long targetId;
}
