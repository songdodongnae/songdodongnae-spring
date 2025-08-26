package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class FestivalThumbnailResDto {
    private Long id;

    private String creatorName;

    private boolean isBookmarked;

    private String title;

    private LocalDateTime createdAt;

    private String imageUrl;


    @JsonProperty("isBookmarked")
    public boolean getIsBookmarked() {
        return isBookmarked;
    }

    public static FestivalThumbnailResDto fromEntity(Festival festival, String creatorName, boolean isBookmarked) {
        return FestivalThumbnailResDto.builder()
                .id(festival.getId())
                .creatorName(creatorName)
                .isBookmarked(isBookmarked)
                .title(festival.getTitle())
                .createdAt(festival.getCreatedAt())
                .imageUrl(festival.getThumbnailImageUrl())
                .build();
    }

    public FestivalThumbnailResDto withLiked(boolean liked) {
        return new FestivalThumbnailResDto(id, creatorName, liked, title, createdAt, imageUrl);
    }

}
