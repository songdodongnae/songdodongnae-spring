package com.culturefinder.songdodongnae.delicious_spot.domain;

import com.culturefinder.songdodongnae.admin.delicious_spot.dto.AdminDeliciousSpotCreateRequestDto;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.curation.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.series.domain.SeriesDeliciousSpot;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliciousSpot {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
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

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    private Integer likes;

    private LocalDateTime createdTime;

    private String imageUrl;

    @OneToMany(mappedBy = "deliciousSpot", cascade = CascadeType.ALL)
    private List<DeliciousSpotImage> deliciousSpotImages = new ArrayList<>();

    @OneToMany(mappedBy = "deliciousSpot", cascade = CascadeType.ALL)
    private List<SeriesDeliciousSpot> seriesDeliciousSpotList = new ArrayList<>();

    public DeliciousSpot(AdminDeliciousSpotCreateRequestDto deliciousSpot) {
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
        this.imageUrl = deliciousSpot.getImageUrl();
    }

    public DeliciousSpotResponseDto fromEntity() {
        return DeliciousSpotResponseDto.builder()
                .id(this.id)
                .creator(this.creator)
                .name(this.name)
                .location(this.location)
                .price(this.price)
                .naverRating(this.naverRating)
                .kakaoRating(this.kakaoRating)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .waiting(this.waiting)
                .parking(this.parking)
                .suggestionMenu(this.suggestionMenu)
                .description(this.description)
                .onelineDescription(this.onelineDescription)
                .contact(this.contact)
                .instagram(this.instagram)
                .likes(this.likes)
                .createdTime(this.createdTime)
                .deliciousSpotImages(this.deliciousSpotImages)
                .build();
    }

    public CurationThumbnailResDto fromThumbEntity() {
        return CurationThumbnailResDto.builder()
                .id(this.id)
                .title(this.name)
                .introduction(this.onelineDescription)
                .imageUrl(this.deliciousSpotImages.getFirst().getImageUrl())
                .build();
    }

}
