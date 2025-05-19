package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.CurationDeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class DeliciousSpotResponseDto {

    private Long id;

    private String title;

    private double latitude;

    private double longitude;

    private String address;

    private Integer price;

    private Float naverRating;

    private Float kakaoRating;

    private LocalTime startTime;

    private LocalTime endTime;

    private String timeDescription;

    private String waiting;

    private String parking;

    private String suggestionMenu;

    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String imageUrl;

    private List<DeliciousSpotImage> deliciousSpotImages;

    private List<CurationDeliciousSpot> curationDeliciousSpotList;

}
