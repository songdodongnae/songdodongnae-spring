package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class CurationResDto {
    private Long id;
    private Integer type;
    private List<DeliciousSpotResDto> deliciousSpots;
    private List<FestivalResDto> festivals;
    private CreatorResDto creator;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private String imageUrl;

    public static CurationResDto fromEntity(Curation curation) {
        List<DeliciousSpotResDto> deliciousSpotDto = curation.getDeliciousSpots().stream()
                .map(DeliciousSpotResDto::fromEntity)
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
