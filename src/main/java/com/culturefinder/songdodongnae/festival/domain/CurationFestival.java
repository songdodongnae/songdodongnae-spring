package com.culturefinder.songdodongnae.festival.domain;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import jakarta.persistence.*;

@Entity
public class CurationFestival {

    @Id @GeneratedValue
    @Column(name = "curation_festival_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @ManyToOne
    @JoinColumn(name = "curation_id")
    private Curation curation;

}
