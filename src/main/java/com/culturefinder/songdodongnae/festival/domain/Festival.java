package com.culturefinder.songdodongnae.festival.domain;

import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Festival {

    @Id @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FestivalCategory category;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String timeDescription;

    private String location;

    private String fee;

    private String contact;

    private String homePageUrl;

    private String reservationUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String onelineDescription;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalPosterImage> festivalPosterImages = new ArrayList<>();

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalImage> festivalImages = new ArrayList<>();

    public FestivalResDto fromEntity() {
        return FestivalResDto.builder()
                .id(this.id)
                .name(this.name)
                .category(this.category)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .timeDescription(this.timeDescription)
                .location(this.location)
                .fee(this.fee)
                .contact(this.contact)
                .homePageUrl(this.homePageUrl)
                .reservationUrl(this.reservationUrl)
                .description(this.description)
                .onelineDescription(this.onelineDescription)
                .build();
    }
}
