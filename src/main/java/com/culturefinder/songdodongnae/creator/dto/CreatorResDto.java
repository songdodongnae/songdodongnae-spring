package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreatorResDto {

    private Long id;

    private String name;

    private String introduction;

    private String description;

    private String imageUrl;

    private List<DeliciousSpotThumbnailResDto> deliciousSpotThumbnails;

    private List<FestivalThumbnailResDto> festivalThumbnails;

    private List<CurationThumbnailResDto> curationThumbnails;

    public static CreatorResDto fromEntity(Creator creator) {
        return CreatorResDto.builder()
                .id(creator.getId())
                .name(creator.getName())
                .introduction(creator.getIntroduction())
                .description(creator.getDescription())
                .imageUrl(creator.getImageUrl())
                .build();
    }

    public static CreatorResDto fromEntity(Creator creator, List<DeliciousSpotThumbnailResDto> deliciousSpotThumbnails, List<FestivalThumbnailResDto> festivalThumbnails, List<CurationThumbnailResDto> curationThumbnails) {
        return CreatorResDto.builder()
                .id(creator.getId())
                .name(creator.getName())
                .introduction(creator.getIntroduction())
                .description(creator.getDescription())
                .imageUrl(creator.getImageUrl())
                .deliciousSpotThumbnails(deliciousSpotThumbnails)
                .festivalThumbnails(festivalThumbnails)
                .curationThumbnails(curationThumbnails)
                .build();
    }
}
