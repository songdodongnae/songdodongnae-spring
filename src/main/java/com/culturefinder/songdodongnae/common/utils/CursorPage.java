package com.culturefinder.songdodongnae.common.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class CursorPage<T> {

    private final List<T> content;
    private final String nextCursor;
    private final boolean hasNext;
    private final int size;

    public static <T> CursorPage<T> of(List<T> content, String nextCursor, boolean hasNext) {
        return CursorPage.<T>builder()
                .content(content)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .size(content.size())
                .build();
    }

    public static <T> CursorPage<T> empty() {
        return CursorPage.<T>builder()
                .content(List.of())
                .nextCursor(null)
                .hasNext(false)
                .size(0)
                .build();
    }
}