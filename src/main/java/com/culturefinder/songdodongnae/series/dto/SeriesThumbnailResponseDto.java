package com.culturefinder.songdodongnae.series.dto;

import com.culturefinder.songdodongnae.series.domain.Series;

public class SeriesThumbnailResponseDto {

    private String title;

    private String imageUrl;

    private String creatorImageUrl;

    private String category;

    public SeriesThumbnailResponseDto(Series series) {
        this.title = series.getTitle();
        this.imageUrl = series.getImageUrl();
        this.creatorImageUrl = series.getCreator().getImageUrl();
        this.category = series.getCategory().toString();
    }
}
