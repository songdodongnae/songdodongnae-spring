package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;
import java.util.*;

@Builder
@Getter
@AllArgsConstructor
public class DeliciousSpotReqDto {

    @NotNull
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

    @NotNull
    private String description;

    private String thumbnailImageUrl;

    private List<String> imageUrls;

    private String onelineDescription;

    private String instagram;

    private String contact;

    public static DeliciousSpot toEntity(DeliciousSpotReqDto dto, Creator creator) {
        return DeliciousSpot.builder()
                .title(dto.getTitle())
                .creator(creator)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .address(dto.getAddress())
                .price(dto.getPrice())
                .naverRating(dto.getNaverRating())
                .kakaoRating(dto.getKakaoRating())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .timeDescription(dto.getTimeDescription())
                .waiting(dto.getWaiting())
                .parking(dto.getParking())
                .suggestionMenu(dto.getSuggestionMenu())
                .description(dto.getDescription())
                .thumbnailImageUrl(dto.getThumbnailImageUrl())
                .imageUrls(dto.getImageUrls())
                .onelineDescription(dto.getOnelineDescription())
                .instagram(dto.getInstagram())
                .contact(dto.getContact())
                .build();
    }

}
