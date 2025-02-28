package com.culturefinder.songdodongnae.creator.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatorResDto {
    private Long id;
    private String name;
    private String details;
    private String description;
}
