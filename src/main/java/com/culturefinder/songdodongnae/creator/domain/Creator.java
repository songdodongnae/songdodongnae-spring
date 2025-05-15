package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.*;
import lombok.*;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany
    private List<Curation> curationList = new ArrayList<>();

    public CreatorResDto fromEntity() {
        return CreatorResDto.builder()
                .id(this.id)
                .name(this.name)
                .introduction(this.introduction)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .curationList(this.curationList)
                .build();
    }

    public CreatorThumbnailResDto fromThumbEntity() {
        return CreatorThumbnailResDto.builder()
                .id(this.id)
                .name(this.name)
                .introduction(this.introduction)
                .build();
    }
}

