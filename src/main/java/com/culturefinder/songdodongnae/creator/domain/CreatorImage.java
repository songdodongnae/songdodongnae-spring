package com.culturefinder.songdodongnae.creator.domain;

import com.culturefinder.songdodongnae.series.domain.Series;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CreatorImage {

    @Id @GeneratedValue
    @Column(name = "creator_image_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    private String creatorImage;

}
