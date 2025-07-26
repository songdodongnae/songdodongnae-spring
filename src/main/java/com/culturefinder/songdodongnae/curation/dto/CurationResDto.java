package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public class CurationResDto {
    private Long id;
    private CurationType type;
    private List<DeliciousSpotResDto> deliciousSpots;
    private List<FestivalResDto> festivals;
    private CreatorResDto creator;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private String imageUrl;
    private Boolean isBookmarked;

    public static CurationResDto fromEntity(Curation curation, Creator creator, Boolean isBookmarked) {
        List<DeliciousSpotResDto> deliciousSpotDto = curation.getDeliciousSpots().stream()
                .map(deliciousSpot -> DeliciousSpotResDto.fromEntity(deliciousSpot, false))
                .toList();

        List<FestivalResDto> festivalDto = curation.getFestivals().stream()
                .map((Festival festival) -> FestivalResDto.fromEntity(festival, false))
                .toList();

        CreatorResDto creatorDto = CreatorResDto.fromEntity(creator);

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
                .isBookmarked(isBookmarked)
                .build();
    }

    public static CurationResDto fromEntity(Curation curation, Creator creator, Set<Long> bookmarkedDeliciousSpots, Set<Long> bookmarkedFestivals, Boolean isBookmarked) {
        List<DeliciousSpotResDto> deliciousSpotDto = curation.getDeliciousSpots().stream()
                .map(deliciousSpot -> {
                    return DeliciousSpotResDto.fromEntity(deliciousSpot, bookmarkedDeliciousSpots.contains(deliciousSpot.getId()));
                })
                .toList();

        List<FestivalResDto> festivalDto = curation.getFestivals().stream()
                .map(festival -> {
                    return FestivalResDto.fromEntity(festival, bookmarkedFestivals.contains(festival.getId()));
                })
                .toList();

        CreatorResDto creatorDto = CreatorResDto.fromEntity(creator);

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
                .isBookmarked(isBookmarked)
                .build();
    }

    @JsonProperty("isBookmarked")
    public boolean getIsBookmarked() {
        return isBookmarked;
    }
}
