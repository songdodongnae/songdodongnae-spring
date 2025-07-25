package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class CurationResDto {
    private Long id;
    private Integer type;
    private List<DeliciousSpotResponseDto> deliciousSpots;
    private List<FestivalResDto> festivals;
    private CreatorResDto creator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private String imageUrl;
    @Setter
    private Boolean isBookmarked;

    public static CurationResDto fromEntity(Curation curation) {
        List<DeliciousSpotResponseDto> deliciousSpotDto = curation.getDeliciousSpots().stream()
                .map(DeliciousSpotResponseDto::fromEntity)
                .toList();

        List<FestivalResDto> festivalDto = curation.getFestivals().stream()
                .map(FestivalResDto::fromEntity)
                .toList();

        CreatorResDto creatorDto = CreatorResDto.fromEntity(curation.getCreator());

        return CurationResDto.builder()
                .id(curation.getId())
                .type(curation.getType())
                .deliciousSpots(deliciousSpotDto)
                .festivals(festivalDto)
                .creator(creatorDto)
                .createdAt(curation.getCreatedAt())
                .updatedAt(curation.getUpdatedAt())
                .title(curation.getTitle())
                .description(curation.getDescription())
                .imageUrl(curation.getImageUrl())
                .build();
    }
}
