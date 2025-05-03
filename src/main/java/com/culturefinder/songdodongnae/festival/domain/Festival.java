package com.culturefinder.songdodongnae.festival.domain;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.curation.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.series.domain.SeriesFestival;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Festival {

    @Id @GeneratedValue
    @Column(name = "festival_id")
    private Long id;

    private String name;

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

    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalPosterImage> festivalPosterImages = new ArrayList<>();

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<FestivalImage> festivalImages = new ArrayList<>();

    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL)
    private List<SeriesFestival> seriesFestivalList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Creator creator;

    public FestivalResDto fromEntity() {
        return FestivalResDto.builder()
                .id(this.id)
                .name(this.name)
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
                .createdTime(this.createdTime)
                .posterImages(this.festivalPosterImages)
                .images(this.festivalImages)
                .creator(this.creator != null ? this.creator.fromEntity() : null)
                .build();
    }

    public void update(Festival festival) {
        this.name = festival.getName();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.startTime = festival.getStartTime();
        this.endTime = festival.getEndTime();
        this.timeDescription = festival.getTimeDescription();
        this.location = festival.getLocation();
        this.fee = festival.getFee();
        this.contact = festival.getContact();
        this.homePageUrl = festival.getHomePageUrl();
        this.reservationUrl = festival.getReservationUrl();
        this.description = festival.getDescription();
        this.onelineDescription = festival.getOnelineDescription();
        if (festival.getCreator() != null) {
            this.creator = festival.getCreator();
        }
    }

    public CurationThumbnailResDto fromThumbEntity() {
        return CurationThumbnailResDto.builder()
                .id(this.id)
                .title(this.name)
                .introduction(this.onelineDescription)
                .imageUrl(this.festivalPosterImages.toString())
                .build();
    }

}
