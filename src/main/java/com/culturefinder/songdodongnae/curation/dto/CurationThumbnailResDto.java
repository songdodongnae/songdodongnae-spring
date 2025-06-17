package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CurationThumbnailResDto {

    private Long id;

    private String title;

    private String introduction;

    private String imageUrl;

    private LocalDateTime createdTime;

    private CurationType curationType;

    public static CurationThumbnailResDto fromEntity(Curation curation) {
        return CurationThumbnailResDto.builder()
                .id(curation.getId())
                .title(curation.getTitle())
                .imageUrl(curation.getImageUrl())
                .introduction(curation.getIntroduction())
                .createdTime(curation.getCreatedAt())
                .curationType(curation.getCurationType())
                .build();
    }
}
