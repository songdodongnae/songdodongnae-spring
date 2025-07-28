package com.culturefinder.songdodongnae.bookmark.dto;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class BookmarkResDto {

    private Long bookmarkId;

    private BookmarkType bookmarkType;

    private Long targetId;

    public static BookmarkResDto fromEntity(Bookmark bookmark) {
        return BookmarkResDto.builder()
                .bookmarkId(bookmark.getId())
                .bookmarkType(bookmark.getBookmarkType())
                .targetId(bookmark.getTargetId())
                .build();
    }
}
