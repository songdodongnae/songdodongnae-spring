package com.culturefinder.songdodongnae.delicious_spot.domain;

import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotInputDto;
import com.culturefinder.songdodongnae.series.domain.Series;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString(exclude = "series")
@AllArgsConstructor
@NoArgsConstructor
public class DeliciousSpot {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_id")
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

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    private Integer likes;

    @OneToMany(mappedBy = "deliciousSpot", cascade = CascadeType.ALL)
    private List<DeliciousSpotImage> deliciousSpotImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    public DeliciousSpot(AdminDeliciousSpotInputDto deliciousSpot) {
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
    }

    public void setDeliciousSpotImages(List<DeliciousSpotImage> deliciousSpotImages) {
        this.deliciousSpotImages = deliciousSpotImages;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
