package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.series.domain.SeriesDeliciousSpot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class DeliciousSpotResponseDto {

    private Long id;

    private Creator creator;

    private String name;

    private String location;

    private Integer price;

    private Float naverRating;

    private Float kakaoRating;

    private LocalTime startTime;

    private LocalTime endTime;

    private String waiting;

    private String parking;

    private String suggestionMenu;

    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    private Integer likes;

    private LocalDateTime createdTime;

    private List<DeliciousSpotImage> deliciousSpotImages;

}
