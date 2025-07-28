package com.culturefinder.songdodongnae.utils;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomPage<T> {

    @Getter
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public static <T> CustomPage<T> of(List<T> content, int currentPage, int pageSize, long totalElements) {
        return CustomPage.<T>builder()
                .content(content)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages((int) Math.ceil((double) totalElements / pageSize))
                .build();
    }
}
