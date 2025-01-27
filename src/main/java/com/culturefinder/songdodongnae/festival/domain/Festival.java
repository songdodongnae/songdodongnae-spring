package com.culturefinder.songdodongnae.festival.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString
@AllArgsConstructor
public class Festival {

    @Id @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FestivalCategory category;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private String fee;

    private String contact;

    private String homePageUrl;

    private String reservationUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalPosterImage> festivalPosterImages = new ArrayList<>();

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalImage> festivalImages = new ArrayList<>();
}
