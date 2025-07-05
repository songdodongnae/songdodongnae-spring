package com.culturefinder.songdodongnae.curation.domain;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curation {

    @Id @GeneratedValue
    @Column(name = "curation_id")
    private Long id;

    @OneToMany
    private List<DeliciousSpot> deliciousSpots = new ArrayList<>();

    @OneToMany
    private List<Festival> festivals = new ArrayList<>();

    @OneToOne(mappedBy = "curation")
    private Creator creator;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String title;

    private String description;

    private String imageUrl;

}
