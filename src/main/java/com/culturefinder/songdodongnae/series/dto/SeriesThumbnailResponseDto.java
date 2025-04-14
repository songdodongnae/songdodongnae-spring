package com.culturefinder.songdodongnae.series.dto;

import com.culturefinder.songdodongnae.series.domain.Series;
import lombok.Data;

@Data
public class SeriesThumbnailResponseDto {

    private Long id;

    private String title;

    private String imageUrl;

    public SeriesThumbnailResponseDto(Series series) {
        this.id = series.getId();
        this.title = series.getTitle();
        this.imageUrl = series.getImageUrl();
    }
}
