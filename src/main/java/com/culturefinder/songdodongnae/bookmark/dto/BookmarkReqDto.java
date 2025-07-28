package com.culturefinder.songdodongnae.bookmark.dto;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
public class BookmarkReqDto {

    @NotNull
    private BookmarkType bookmarkType;

    @NotNull
    private Long targetId;
}
