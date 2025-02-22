package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SingleDeliciousSpotResponseDto {

    private Long id;

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

    private List<DeliciousSpotImageResponseDto> deliciousSpotImages = new ArrayList<>();

    public SingleDeliciousSpotResponseDto(DeliciousSpot deliciousSpot) {
        this.id = deliciousSpot.getId();
        this.name = deliciousSpot.getName();
        this.location = deliciousSpot.getLocation();
        this.price = deliciousSpot.getPrice();
        this.naverRating = deliciousSpot.getNaverRating();
        this.kakaoRating = deliciousSpot.getKakaoRating();
        this.startTime = deliciousSpot.getStartTime();
        this.endTime = deliciousSpot.getEndTime();
        this.waiting = deliciousSpot.getWaiting();
        this.parking = deliciousSpot.getParking();
        this.suggestionMenu = deliciousSpot.getSuggestionMenu();
        this.description = deliciousSpot.getDescription();
        this.onelineDescription = deliciousSpot.getOnelineDescription();
        this.instagram = deliciousSpot.getInstagram();
        this.contact = deliciousSpot.getContact();
        this.likes = deliciousSpot.getLikes();
        this.deliciousSpotImages = deliciousSpot.getDeliciousSpotImages()
                .stream()
                .map(DeliciousSpotImageResponseDto::new)
                .toList();
    }
}
