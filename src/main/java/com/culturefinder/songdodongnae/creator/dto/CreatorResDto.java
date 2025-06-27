package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatorResDto {

    private Long id;

    private String name;

    private String introduction;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String imageUrl;

    public static CreatorResDto fromEntity(Creator creator) {
        return CreatorResDto.builder()
                .id(creator.getId())
                .name(creator.getName())
                .introduction(creator.getIntroduction())
                .description(creator.getDescription())
                .createdAt(creator.getCreatedAt())
                .updatedAt(creator.getUpdatedAt())
                .imageUrl(creator.getImageUrl())
                .build();
    }
}
