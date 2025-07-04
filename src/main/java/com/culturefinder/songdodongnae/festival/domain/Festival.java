package com.culturefinder.songdodongnae.festival.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Festival {

    @Id @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String timeDescription;

    private double latitude;

    private double longitude;

    private String address;

    private String fee;

    private String contact;

    private String homePageUrl;

    private String reservationUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String imageUrl;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalImage> festivalImages = new ArrayList<>();

    public void update(Festival festival) {
        this.title = festival.getTitle();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.startTime = festival.getStartTime();
        this.endTime = festival.getEndTime();
        this.timeDescription = festival.getTimeDescription();
        this.latitude = festival.getLatitude();
        this.longitude = festival.getLongitude();
        this.address = festival.getAddress();
        this.fee = festival.getFee();
        this.contact = festival.getContact();
        this.homePageUrl = festival.getHomePageUrl();
        this.reservationUrl = festival.getReservationUrl();
        this.description = festival.getDescription();
        this.onelineDescription = festival.getOnelineDescription();
        this.createdAt = festival.getCreatedAt();
        this.updatedAt = festival.getUpdatedAt();
        this.imageUrl = festival.getImageUrl();
        this.festivalImages = festival.getFestivalImages();
    }

}
