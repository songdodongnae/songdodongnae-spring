package com.culturefinder.songdodongnae.creator.domain;

import jakarta.persistence.*;
import lombok.*;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

