package com.culturefinder.songdodongnae.creator.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatorThumbnailResDto {
    private Long id;
    private String name;
    private String introduction;
    private String imageUrl;
}
