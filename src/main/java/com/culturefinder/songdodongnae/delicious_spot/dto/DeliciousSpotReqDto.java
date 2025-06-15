package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class DeliciousSpotReqDto {
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

    public DeliciousSpot toEntity() {
        return DeliciousSpot.builder()
                .title(this.title)
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
                .onelineDescription(this.onelineDescription)
                .instagram(this.instagram)
                .contact(this.contact)
                .build();
    }
}
