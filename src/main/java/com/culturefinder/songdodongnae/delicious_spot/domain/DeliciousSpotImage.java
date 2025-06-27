package com.culturefinder.songdodongnae.delicious_spot.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliciousSpotImage {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_image_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "delicious_spot_id")
    private DeliciousSpot deliciousSpot;

    @Column(length = 40000)
    private String imageUrl;

    public DeliciousSpotImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
