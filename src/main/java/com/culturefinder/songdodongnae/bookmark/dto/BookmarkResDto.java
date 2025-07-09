package com.culturefinder.songdodongnae.bookmark.dto;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import lombok.*;

@Data
@NoArgsConstructor
public class BookmarkResDto {

    private Long bookmarkId;

    private BookmarkType bookmarkType;

    private Long targetId;

    public static BookmarkResDto fromEntity(Bookmark bookmark) {
        BookmarkResDto dto = new BookmarkResDto();
        dto.setBookmarkId(bookmark.getId());
        dto.setBookmarkType(bookmark.getBookmarkType());
        dto.setTargetId(bookmark.getTargetId());
        return dto;
    }

}
