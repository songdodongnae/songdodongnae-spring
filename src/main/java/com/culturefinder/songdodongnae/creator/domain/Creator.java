package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

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

    @OneToOne
    @JoinColumn(name = "curation_id")
    private Curation curation;

    public void update(Creator entity) {
        this.name = entity.getName();
        this.introduction = entity.getIntroduction();
        this.description = entity.getDescription();
        this.imageUrl = entity.getImageUrl();
        this.curation = entity.getCuration();
    }
}

