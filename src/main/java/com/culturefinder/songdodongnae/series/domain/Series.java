package com.culturefinder.songdodongnae.series.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Series {

    @Id @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    @OneToMany(mappedBy = "series")
    private List<SeriesDeliciousSpot> seriesDeliciousSpotList = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<SeriesFestival> seriesFestivalList = new ArrayList<>();

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private String imageUrl;

    private Integer orderNumber;

    @Enumerated(value = EnumType.STRING)
    private SeriesCategory category;
}
