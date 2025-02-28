package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
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
    private String details;

    @Column
    private String description;

    @OneToMany(mappedBy = "creator")
    private List<Series> series = new ArrayList<>();

    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    private CreatorImage creatorImage;

    public CreatorResDto fromEntity() {
        return CreatorResDto.builder()
                .id(this.id)
                .name(this.name)
                .details(this.details)
                .description(this.description)
                .build();
    }
}

