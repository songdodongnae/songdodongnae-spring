package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.series.domain.Series;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeliciousSpotThumbnailResponseDto {

    private Long id;

    private String title;

    private String imageUrl;

    public DeliciousSpotThumbnailResponseDto(Series series) {
        this.id = series.getId();
        this.title = series.getTitle();
        this.imageUrl = series.getImageUrl();
    }
}
