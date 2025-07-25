package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;
import java.util.*;

@Data
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

    public DeliciousSpot toEntity(Creator creator) {
        return DeliciousSpot.builder()
                .title(this.getTitle())
                .creator(creator)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .address(this.address)
                .price(this.price)
                .naverRating(this.naverRating)
                .kakaoRating(this.kakaoRating)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .timeDescription(this.timeDescription)
                .waiting(this.waiting)
                .parking(this.parking)
                .suggestionMenu(this.suggestionMenu)
                .description(this.description)
                .thumbnailImageUrl(this.thumbnailImageUrl)
                .onelineDescription(this.onelineDescription)
                .instagram(this.instagram)
                .contact(this.contact)
                .imageUrls(this.imageUrls)
                .build();
    }

}
