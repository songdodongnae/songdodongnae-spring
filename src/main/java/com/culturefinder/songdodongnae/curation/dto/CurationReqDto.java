package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CurationReqDto {

    @NotNull
    private Integer type;

    private List<DeliciousSpotReqDto> deliciousSpots = new ArrayList<>();

    private List<FestivalReqDto> festivals = new ArrayList<>();

    @NotNull
    private CreatorReqDto creator;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String imageUrl;

    public Curation toEntity() {
        return Curation.builder()
                .type(this.type)
                .deliciousSpots(
                        this.deliciousSpots.stream()
                                .map(DeliciousSpotReqDto::toEntity)
                                .toList()
                )
                .festivals(
                        this.festivals.stream()
                                .map(FestivalReqDto::toEntity)
                                .toList()
                )
                .creator(
                        this.creator != null ? this.creator.toEntity(creator.getImage()) :null
                )
                .title(this.title)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .build();
    }
}
