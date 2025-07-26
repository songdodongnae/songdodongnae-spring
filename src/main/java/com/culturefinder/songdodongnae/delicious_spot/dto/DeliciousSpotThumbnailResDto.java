package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DeliciousSpotThumbnailResDto {
    private Long id;

    private String title;

    private String creatorName;

    private boolean isBookmarked;

    private LocalDateTime createdAt;

    private String thumbnailImageUrl;


    @JsonProperty("isBookmarked")
    public boolean getIsBookmarked() {
        return isBookmarked;
    }

    public static DeliciousSpotThumbnailResDto fromEntity(DeliciousSpot deliciousSpot, String creatorName, boolean isBookmarked) {
        return DeliciousSpotThumbnailResDto.builder()
                .id(deliciousSpot.getId())
                .title(deliciousSpot.getTitle())
                .creatorName(creatorName)
                .isBookmarked(isBookmarked)
                .createdAt(deliciousSpot.getCreatedAt())
                .thumbnailImageUrl(deliciousSpot.getThumbnailImageUrl())
                .build();
    }
}