package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatorReqDto {
    private String name;
    private String details;
    private String description;

    public Creator toEntity() {
        return Creator.builder()
                .name(name)
                .details(details)
                .description(description)
                .build();
    }
}
