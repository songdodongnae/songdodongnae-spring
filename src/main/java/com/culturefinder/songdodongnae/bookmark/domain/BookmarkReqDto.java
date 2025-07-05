package com.culturefinder.songdodongnae.bookmark.domain;

import lombok.*;

@Data
@NoArgsConstructor
public class BookmarkReqDto {
    private BookmarkType bookmarkType;
    private Long targetId;
}
