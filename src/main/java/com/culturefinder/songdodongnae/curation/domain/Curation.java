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
        CurationDeliciousSpot curationDeliciousSpot = CurationDeliciousSpot.of(this, deliciousSpot);
        curationDeliciousSpots.add(curationDeliciousSpot);
    }

    public void addFestival(Festival festival) {
        CurationFestival curationFestival = CurationFestival.of(this, festival);
        curationFestivals.add(curationFestival);
    }

    public void update(String title, String description, CurationType type, String imageUrl, Creator creator, List<Festival> festivals, List<DeliciousSpot> deliciousSpots) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.creator = creator;

        this.curationFestivals.clear();
        this.curationDeliciousSpots.clear();

        festivals.forEach(this::addFestival);
        deliciousSpots.forEach(this::addDeliciousSpot);
    }
}
