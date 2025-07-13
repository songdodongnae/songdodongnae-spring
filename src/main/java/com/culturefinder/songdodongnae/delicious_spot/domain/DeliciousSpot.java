package com.culturefinder.songdodongnae.delicious_spot.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliciousSpot {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_id")
    private Long id;

    private String title;

    private double latitude;

    private double longitude;

    private String address;

    private Integer price;

    private Float naverRating;

    private Float kakaoRating;

    private String startTime;

    private String endTime;

    private String timeDescription;

    private String waiting;

    private String parking;

    private String suggestionMenu;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String thumbnailImageUrl;

    public DeliciousSpot updateDeliciousSpot(DeliciousSpot deliciousSpot) {
        this.title = deliciousSpot.getTitle();
        this.latitude = deliciousSpot.getLatitude();
        this.longitude = deliciousSpot.getLongitude();
        this.address = deliciousSpot.getAddress();
        this.price = deliciousSpot.getPrice();
        this.naverRating = deliciousSpot.getNaverRating();
        this.kakaoRating = deliciousSpot.getKakaoRating();
        this.startTime = deliciousSpot.getStartTime();
        this.endTime = deliciousSpot.getEndTime();
        this.timeDescription = deliciousSpot.getTimeDescription();
        this.waiting = deliciousSpot.getWaiting();
        this.parking = deliciousSpot.getParking();
        this.suggestionMenu = deliciousSpot.getSuggestionMenu();
        this.description = deliciousSpot.getDescription();
        this.onelineDescription = deliciousSpot.getOnelineDescription();
        this.instagram = deliciousSpot.getInstagram();
        this.contact = deliciousSpot.getContact();
        this.thumbnailImageUrl = deliciousSpot.getThumbnailImageUrl();
        return this;
    }

}
