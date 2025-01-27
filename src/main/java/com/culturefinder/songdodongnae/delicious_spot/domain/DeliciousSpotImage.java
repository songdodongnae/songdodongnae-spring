package com.culturefinder.songdodongnae.delicious_spot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliciousSpotImage {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delicious_spot_id")
    private DeliciousSpot deliciousSpot;

    private String imageUrl;

    public DeliciousSpotImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDeliciousSpot(DeliciousSpot deliciousSpot) {
        this.deliciousSpot = deliciousSpot;
    }
}
