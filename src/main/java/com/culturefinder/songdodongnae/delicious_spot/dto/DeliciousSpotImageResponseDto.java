package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliciousSpotImageResponseDto {

    private Long id;

    private String imageUrl;

    public DeliciousSpotImageResponseDto(DeliciousSpotImage deliciousSpotImage) {
        this.id = deliciousSpotImage.getId();
        this.imageUrl = deliciousSpotImage.getImageUrl();
    }
}
