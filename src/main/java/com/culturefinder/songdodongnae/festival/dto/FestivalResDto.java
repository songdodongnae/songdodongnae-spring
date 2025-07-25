package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String creatorName;

    private boolean isBookmarked;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String timeDescription;

    private double latitude;

    private double longitude;

    private String address;

    private String fee;

    private String contact;

    private String homePageUrl;

    private String reservationUrl;

    private String description;

    private String onelineDescription;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String imageUrl;

    private List<String> festivalImages;

    @JsonProperty("isBookmarked")
    public boolean getIsBookmarked() {
        return isBookmarked;
    }

    public static FestivalResDto fromEntity(Festival festival, boolean isBookmarked) {
        return FestivalResDto.builder()
                .id(festival.getId())
                .creatorName(festival.getCreator() == null ? null : festival.getCreator().getName())
                .isBookmarked(isBookmarked)
                .title(festival.getTitle())
                .startDate(festival.getStartDate())
                .endDate(festival.getEndDate())
                .startTime(festival.getStartTime())
                .endTime(festival.getEndTime())
                .timeDescription(festival.getTimeDescription())
                .latitude(festival.getLatitude())
                .longitude(festival.getLongitude())
                .address(festival.getAddress())
                .fee(festival.getFee())
                .contact(festival.getContact())
                .homePageUrl(festival.getHomePageUrl())
                .reservationUrl(festival.getReservationUrl())
                .description(festival.getDescription())
                .onelineDescription(festival.getOnelineDescription())
                .createdAt(festival.getCreatedAt())
                .updatedAt(festival.getUpdatedAt())
                .imageUrl(festival.getThumbnailImageUrl())
                .festivalImages(festival.getImageUrls())
                .build();
    }

}
