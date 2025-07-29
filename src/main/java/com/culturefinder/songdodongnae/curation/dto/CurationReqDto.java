package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class CurationReqDto {

    @NotNull
    private CurationType type;

    private List<Long> ids = new ArrayList<>();

    @NotNull
    private String creatorName;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String imageUrl;


    public static Curation toEntity(CurationReqDto dto, Creator creator, List<DeliciousSpot> deliciousSpots, List<Festival> festivals) {

        Curation curation = Curation.builder()
                .type(dto.getType())
                .creator(creator)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();

        if (dto.getType() == CurationType.DELICIOUS_SPOT) {
            deliciousSpots.forEach(curation::addDeliciousSpot);
        }

        if (dto.getType() == CurationType.FESTIVAL) {
            festivals.forEach(curation::addFestival);
        }

        return curation;
    }

}
