package com.culturefinder.songdodongnae.delicious_spot.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliciousSpotImage {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_image_id")
    private Long id;

    private Long deliciousSpotId;

    @Column(length = 40000)
    private String imageUrl;

}
