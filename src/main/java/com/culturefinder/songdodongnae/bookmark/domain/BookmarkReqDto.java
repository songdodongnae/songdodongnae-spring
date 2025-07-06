package com.culturefinder.songdodongnae.bookmark.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class BookmarkReqDto {

    @NotNull
    private BookmarkType bookmarkType;

    @NotNull
    private Long targetId;
}
