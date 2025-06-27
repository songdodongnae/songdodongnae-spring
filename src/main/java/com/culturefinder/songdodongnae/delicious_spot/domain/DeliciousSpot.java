package com.culturefinder.songdodongnae.delicious_spot.domain;

import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    private LocalTime startTime;

    private LocalTime endTime;

    private String timeDescription;

    private String waiting;

    private String parking;

    private String suggestionMenu;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime updatedAt;

    private String imageUrl;

    @OneToMany(mappedBy = "deliciousSpot", cascade = CascadeType.ALL)
    private List<DeliciousSpotImage> deliciousSpotImages = new ArrayList<>();

    @OneToMany(mappedBy = "deliciousSpot", cascade = CascadeType.ALL)
    private List<CurationDeliciousSpot> curationDeliciousSpotList = new ArrayList<>();

    public DeliciousSpotResponseDto fromEntity() {
        return DeliciousSpotResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .address(this.address)
                .price(this.price)
                .naverRating(this.naverRating)
                .kakaoRating(this.kakaoRating)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .timeDescription(timeDescription)
                .waiting(this.waiting)
                .parking(this.parking)
                .suggestionMenu(this.suggestionMenu)
                .description(this.description)
                .onelineDescription(this.onelineDescription)
                .contact(this.contact)
                .instagram(this.instagram)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .imageUrl(this.imageUrl)
                .deliciousSpotImages(this.deliciousSpotImages)
                .curationDeliciousSpotList(this.curationDeliciousSpotList)
                .build();
    }

    public CurationThumbnailResDto fromThumbEntity() {
        return CurationThumbnailResDto.builder()
                .id(this.id)
                .title(this.title)
                .introduction(this.onelineDescription)
                .imageUrl(this.imageUrl)
                .build();
    }

    public void updateDeliciousSpot(DeliciousSpot deliciousSpot) {
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
        this.imageUrl = deliciousSpot.getImageUrl();
    }

}
