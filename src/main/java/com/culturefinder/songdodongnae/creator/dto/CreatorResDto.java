package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<Curation> curationList;

}
