package com.culturefinder.songdodongnae.series.domain;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
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

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    private String title;

    private String details;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @OneToOne(mappedBy = "series", cascade = CascadeType.ALL)
    private SeriesImage seriesImage;

    @OneToMany(mappedBy = "series")
    private List<DeliciousSpot> deliciousSpots = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<Festival> festivals = new ArrayList<>();

    public void addDeliciousSpot(DeliciousSpot deliciousSpot){
        deliciousSpots.add(deliciousSpot);
    }
}
