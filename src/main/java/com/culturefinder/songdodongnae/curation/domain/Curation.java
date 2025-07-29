package com.culturefinder.songdodongnae.curation.domain;

import com.culturefinder.songdodongnae.creator.domain.Creator;
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

    @Id @GeneratedValue
    @Column(name = "curation_id")
    private Long id;

    private CurationType type;

    @OneToMany(mappedBy = "curation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurationDeliciousSpot> curationDeliciousSpots = new ArrayList<>();

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
