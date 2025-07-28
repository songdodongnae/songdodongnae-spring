package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatorReqDto {

    @NotNull
    private String name;

    private String introduction;

    private String description;

    private String image;

    public static Creator toEntity(CreatorReqDto creatorReqDto) {
        return Creator.builder()
                .name(creatorReqDto.getName())
                .introduction(creatorReqDto.getIntroduction())
                .description(creatorReqDto.getDescription())
                .imageUrl(creatorReqDto.getImage())
                .build();
    }
}
