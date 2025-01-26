package com.culturefinder.songdodongnae.festival.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class FestivalImage {

    @Id @GeneratedValue
    @Column(name = "festival_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    private String imageUrl;

    public FestivalImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
