package com.culturefinder.songdodongnae.delicious_spot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliciousSpotList {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_list_id")
    private Long id;

    private String title;

    private String likes;

    @OneToMany(mappedBy = "deliciousSpotList", cascade = CascadeType.ALL)
    private List<DeliciousSpot> deliciousSpots = new ArrayList<>();

    public void setDeliciousSpots(List<DeliciousSpot> deliciousSpots) {
        this.deliciousSpots = deliciousSpots;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
