package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CurationThumbnailResDto {

    private Long id;
    private CurationType type;

    private LocalDateTime createdAt;
    private String title;
    private String imageUrl;
    private Boolean isBookmarked;

    @JsonProperty("isBookmarked")
    public boolean getIsBookmarked() {
        return isBookmarked;
    }

    public static CurationThumbnailResDto fromEntity(Curation curation, Boolean isBookmarked) {
        return CurationThumbnailResDto.builder()
                .id(curation.getId())
                .type(curation.getType())
                .createdAt(curation.getCreatedAt())
                .title(curation.getTitle())
                .imageUrl(curation.getImageUrl())
                .isBookmarked(isBookmarked)
                .build();
    }
}