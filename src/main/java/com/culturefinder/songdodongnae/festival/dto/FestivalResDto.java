package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.festival.domain.CurationFestival;
import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
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

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String timeDescription;

    private double latitude;

    private double longitude;

    private String fee;

    private String contact;

    private String homePageUrl;

    private String reservationUrl;

    private String description;

    private String onelineDescription;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String imageUrl;

    private List<FestivalImage> festivalImages;

    private List<CurationFestival> curationFestivalList;
}
