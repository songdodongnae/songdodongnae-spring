package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
import com.culturefinder.songdodongnae.festival.domain.FestivalPosterImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class FestivalResDto {
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
    private String description;
    private String onelineDescription;
    private LocalDateTime createdTime;
    private List<FestivalPosterImage> posterImages;
    private List<FestivalImage> images;
}
