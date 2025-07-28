package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class CurationReqDto {

    @NotNull
    private CurationType type;

    private List<DeliciousSpotReqDto> deliciousSpots = new ArrayList<>();

    private List<FestivalReqDto> festivals = new ArrayList<>();

    @NotNull
    private String creatorName;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String imageUrl;

    public static Curation toEntity(CurationReqDto dto, Creator creator) {
        List<DeliciousSpot> deliciousSpots = List.of();
        List<Festival> festivals = List.of();

        if (dto.getType() == CurationType.DELICIOUS_SPOT) {
            deliciousSpots = dto.getDeliciousSpots().stream()
                    .map(deliciousSpotDto -> DeliciousSpotReqDto.toEntity(deliciousSpotDto, null))
                    .toList();
        }
        if (dto.getType() == CurationType.FESTIVAL) {
            festivals = dto.getFestivals().stream()
                    .map(festivalDto -> FestivalReqDto.toEntity(festivalDto, null))
                    .toList();
        }

        return Curation.builder()
                .type(dto.getType())
                .deliciousSpots(deliciousSpots)
                .festivals(festivals)
                .creator(creator)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
    }
}
