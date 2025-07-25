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

    public Creator toEntity() {
        return Creator.builder()
                .name(name)
                .introduction(introduction)
                .description(description)
                .imageUrl(image)
                .build();
    }
}
