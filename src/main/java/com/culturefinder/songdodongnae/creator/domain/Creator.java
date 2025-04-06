package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.series.domain.Series;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "creator")
    private List<Series> series = new ArrayList<>();

    @Column(length = 40000)
    private String imageUrl;

    public CreatorResDto fromEntity() {
        return CreatorResDto.builder()
                .id(this.id)
                .name(this.name)
                .introduction(this.introduction)
                .description(this.description)
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

