package com.culturefinder.songdodongnae.admin.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.series.domain.Series;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminCreatorResponseDto {

    private Long id;

    private String name;

    private String introduction;

    private String description;

    private List<Series> series = new ArrayList<>();

    private String imageUrl;

    public AdminCreatorResponseDto(Creator creator) {
        this.id = creator.getId();
        this.name = creator.getName();
        this.introduction = creator.getIntroduction();
        this.description = creator.getDescription();
        this.series = creator.getSeries();
        this.imageUrl = creator.getImageUrl();
    }
}
