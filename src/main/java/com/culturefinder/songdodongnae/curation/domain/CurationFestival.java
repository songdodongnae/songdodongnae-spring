package com.culturefinder.songdodongnae.curation.domain;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class CurationFestival {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    public CurationFestival(Curation curation, Festival festival) {
        this.curation = curation;
        this.festival = festival;
    }

}
