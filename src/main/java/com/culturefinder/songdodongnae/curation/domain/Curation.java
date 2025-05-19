package com.culturefinder.songdodongnae.curation.domain;

import com.culturefinder.songdodongnae.delicious_spot.domain.CurationDeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.CurationFestival;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
public class Curation {

    @Id
    @GeneratedValue
    @Column(name = "curation_id")
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Category category;

    private String imageUrl;

    private CurationType curationType;

    @OneToMany
    private List<CurationDeliciousSpot> curationDeliciousSpotList = new ArrayList<>();

    @OneToMany
    private List<CurationFestival> curationFestivalList = new ArrayList<>();

}
