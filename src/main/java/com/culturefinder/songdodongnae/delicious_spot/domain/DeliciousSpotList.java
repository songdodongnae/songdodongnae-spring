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
@ToString(exclude = "deliciousSpots")
@NoArgsConstructor
@AllArgsConstructor
public class DeliciousSpotList {

    @Id @GeneratedValue
    @Column(name = "delicious_spot_list_id")
    private Long id;

    private String title;

    private Integer likes = 0;

    private String imageUrl;

    @OneToMany(mappedBy = "deliciousSpotList", cascade = CascadeType.ALL)
    private List<DeliciousSpot> deliciousSpots = new ArrayList<>();

    public void setDeliciousSpots(List<DeliciousSpot> deliciousSpots) {
        this.deliciousSpots = deliciousSpots;
    }

    public DeliciousSpotList(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public void addDeliciousSpot(DeliciousSpot deliciousSpot) {
        this.deliciousSpots.add(deliciousSpot);
    }
}
