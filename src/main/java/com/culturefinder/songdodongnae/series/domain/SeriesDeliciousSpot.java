package com.culturefinder.songdodongnae.series.domain;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDeliciousSpot {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "delicious_spot_id")
    private DeliciousSpot deliciousSpot;

}
