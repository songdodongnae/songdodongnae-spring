package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Entity
public class Creator {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private String imageUrl;


    public CreatorResDto fromEntity() {
        return CreatorResDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .build();
    }
}

