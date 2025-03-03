package com.culturefinder.songdodongnae.series.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SeriesImage {

    @Id @GeneratedValue
    @Column(name = "series_image_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "series_id")
    private Series series;

    private String seriesImage;

    public SeriesImage(String seriesImage) {
        this.seriesImage = seriesImage;
    }

}
