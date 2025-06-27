package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatorThumbnailResDto {
    private Long id;
    private String name;
    private String introduction;
    private String imageUrl;

    public static CreatorThumbnailResDto fromThumbEntity(Creator creator) {
        return CreatorThumbnailResDto.builder()
                .id(creator.getId())
                .name(creator.getName())
                .introduction(creator.getIntroduction())
                .build();
    }
}
