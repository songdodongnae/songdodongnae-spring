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

    @Id @GeneratedValue
    @Column(name = "creator_id")
    private Long id;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "creator")
    private List<Curation> curations = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Festival> festivals = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<DeliciousSpot> deliciousSpots = new ArrayList<>();

    public void update(Creator entity) {
        this.name = entity.getName();
        this.introduction = entity.getIntroduction();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
    }
}

