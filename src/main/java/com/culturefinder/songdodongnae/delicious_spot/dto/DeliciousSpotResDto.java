package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class DeliciousSpotResDto {

    private Long id;

    private String title;

    private String creatorName;

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

    private boolean isBookmarked;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String thumbnailImageUrl;

    private List<String> imageUrls;

    @JsonProperty("isBookmarked")
    public boolean getIsBookmarked() {
        return isBookmarked;
    }

    public static DeliciousSpotResDto fromEntity(DeliciousSpot deliciousSpot, boolean isBookmarked) {
        return DeliciousSpotResDto.builder()
                .id(deliciousSpot.getId())
                .title(deliciousSpot.getTitle())
                .latitude(deliciousSpot.getLatitude())
                .longitude(deliciousSpot.getLongitude())
                .address(deliciousSpot.getAddress())
                .price(deliciousSpot.getPrice())
                .naverRating(deliciousSpot.getNaverRating())
                .kakaoRating(deliciousSpot.getKakaoRating())
                .startTime(deliciousSpot.getStartTime())
                .endTime(deliciousSpot.getEndTime())
                .timeDescription(deliciousSpot.getTimeDescription())
                .waiting(deliciousSpot.getWaiting())
                .parking(deliciousSpot.getParking())
                .suggestionMenu(deliciousSpot.getSuggestionMenu())
                .description(deliciousSpot.getDescription())
                .onelineDescription(deliciousSpot.getOnelineDescription())
                .instagram(deliciousSpot.getInstagram())
                .contact(deliciousSpot.getContact())
                .isBookmarked(isBookmarked)
                .createdAt(deliciousSpot.getCreatedAt())
                .updatedAt(deliciousSpot.getUpdatedAt())
                .thumbnailImageUrl(deliciousSpot.getThumbnailImageUrl())
                .build();
    }
}
