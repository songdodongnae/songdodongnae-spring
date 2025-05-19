package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.curation.domain.CurationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CurationThumbnailResDto {

    private Long id;

    private String title;

    private String introduction;

    private String imageUrl;

    private LocalDateTime createdTime;

    private CurationType curationType;

}
