package com.culturefinder.songdodongnae.series.domain;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Series {

    @Id @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    private String title;

    private String imageUrl;

    private Integer count;

    private Integer order;

    @OneToMany
    private List<Curation> curations = new ArrayList<>();

}
