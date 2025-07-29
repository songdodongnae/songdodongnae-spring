package com.culturefinder.songdodongnae.curation.domain;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curation_id")
    private Long id;

    private CurationType type;

    @Builder.Default
    @OneToMany(mappedBy = "curation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurationDeliciousSpot> curationDeliciousSpots = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "curation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurationFestival> curationFestivals = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String title;

    private String description;

    private String imageUrl;


    public void addDeliciousSpot(DeliciousSpot deliciousSpot) {
        CurationDeliciousSpot curationDeliciousSpot = new CurationDeliciousSpot(this, deliciousSpot);
        curationDeliciousSpots.add(curationDeliciousSpot);
    }

    public void addFestival(Festival festival) {
        CurationFestival curationFestival = new CurationFestival(this, festival);
        curationFestivals.add(curationFestival);
    }

    public void update(Curation curation) {
        this.type = curation.type;
        this.curationDeliciousSpots = curation.curationDeliciousSpots;
        this.curationFestivals = curation.curationFestivals;
        this.creator = curation.creator;
        this.title = curation.title;
        this.description = curation.description;
        this.imageUrl = curation.imageUrl;
    }
}
