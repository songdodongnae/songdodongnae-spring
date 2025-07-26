package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
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

    public Curation toEntity(Creator creator) {
        return Curation.builder()
                .type(this.type)
                .deliciousSpots(
                        this.deliciousSpots.stream()
                                .map(deliciousSpot -> deliciousSpot.toEntity(null))
                                .toList()
                )
                .festivals(
                        this.festivals.stream()
                                .map(festival -> FestivalReqDto.toEntity(festival, null))
                                .toList()
                )
                .creator(creator)
                .title(this.title)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .build();
    }
}
