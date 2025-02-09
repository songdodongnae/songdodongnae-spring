package com.culturefinder.songdodongnae.admin.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@ToString
@NoArgsConstructor
public class AdminDeliciousSpotDto {

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

    private String[] imageLinks;

    public AdminDeliciousSpotDto(DeliciousSpot deliciousSpot) {
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
        this.likes = deliciousSpot.getLikes();
        this.suggestionMenu = deliciousSpot.getSuggestionMenu();
        this.description = deliciousSpot.getDescription();
        this.onelineDescription = deliciousSpot.getOnelineDescription();
        this.instagram = deliciousSpot.getInstagram();
        this.contact = deliciousSpot.getContact();
        this.imageLinks = deliciousSpot.getDeliciousSpotImages().stream().map(DeliciousSpotImage::getImageUrl).toArray(String[]::new);
    }
}
