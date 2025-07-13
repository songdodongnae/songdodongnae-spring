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

}
