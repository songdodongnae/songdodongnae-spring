package com.culturefinder.songdodongnae.bookmark.dto;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BookmarkDto {

    private Long targetId;
    private BookmarkType bookmarkType;

}
