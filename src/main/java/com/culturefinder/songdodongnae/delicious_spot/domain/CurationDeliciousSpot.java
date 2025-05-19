package com.culturefinder.songdodongnae.delicious_spot.domain;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import jakarta.persistence.*;

@Entity
public class CurationDeliciousSpot {

    @Id
    @GeneratedValue
    @Column(name = "curation_deliciousSpot_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delicious_spot_id")
    DeliciousSpot deliciousSpot;

    @ManyToOne
    @JoinColumn(name = "curation_id")
    Curation curation;

}
