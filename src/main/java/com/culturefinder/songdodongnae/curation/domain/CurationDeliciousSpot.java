package com.culturefinder.songdodongnae.curation.domain;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class CurationDeliciousSpot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @ManyToOne
    @JoinColumn(name = "delicious_spot_id")
    private DeliciousSpot deliciousSpot;

    public CurationDeliciousSpot(Curation curation, DeliciousSpot deliciousSpot) {
        this.curation = curation;
        this.deliciousSpot = deliciousSpot;
    }
}

