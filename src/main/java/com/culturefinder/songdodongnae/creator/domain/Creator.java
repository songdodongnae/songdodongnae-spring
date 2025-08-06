package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Entity
public class Creator {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 40000)
    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Curation> curations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Festival> festivals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DeliciousSpot> deliciousSpots = new ArrayList<>();

    public void update(Creator entity) {
        this.name = entity.getName();
        this.introduction = entity.getIntroduction();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
    }
}

