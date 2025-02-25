package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeliciousSpotThumbnailResponseDto {

    private Long id;

    private String title;

    private String imageUrl;

    public DeliciousSpotThumbnailResponseDto(DeliciousSpotList deliciousSpotList) {
        this.id = deliciousSpotList.getId();
        this.title = deliciousSpotList.getTitle();
        this.imageUrl = deliciousSpotList.getImageUrl();;
    }
}
