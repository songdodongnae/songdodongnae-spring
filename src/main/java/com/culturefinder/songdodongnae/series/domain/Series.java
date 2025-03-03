package com.culturefinder.songdodongnae.series.domain;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Series {

    @Id @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private String imageUrl;

    private Boolean isOnMain;

    private Integer orderNumber;

    @Enumerated(value = EnumType.STRING)
    private SeriesCategory category;
}
