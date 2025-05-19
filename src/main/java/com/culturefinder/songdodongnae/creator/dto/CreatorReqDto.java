package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatorReqDto {

    private String name;

    private String introduction;

    private String description;

    public Creator toEntity() {
        return Creator.builder()
                .name(name)
                .introduction(introduction)
                .description(description)
                .build();
    }
}
