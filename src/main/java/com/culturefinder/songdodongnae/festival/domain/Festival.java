package com.culturefinder.songdodongnae.festival.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString
public class Festival {

    @Id @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private FestivalCategory category;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    private LocalTime start_time;

    private LocalTime end_time;

    private String location;

    private String fee;

    private String contact;

    private String homePageUrl;

    private String reservationUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    @OneToMany(mappedBy = "festival")
    private List<FestivalPosterImage> festivalPosterImages = new ArrayList<>();

    @OneToMany(mappedBy = "festival")
    private List<FestivalImage> festivalImages = new ArrayList<>();
}
