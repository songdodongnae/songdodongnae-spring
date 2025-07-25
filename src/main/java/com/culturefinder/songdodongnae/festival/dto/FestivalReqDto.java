package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class FestivalReqDto {

    @NotNull
    private String title;

    private String creatorName;

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

    @NotNull
    private String description;

    private String onelineDescription;

    private String mainImage;
    private List<String> images;

    public static Festival toEntity(FestivalReqDto festivalReqDto, Creator creator) {
        return Festival.builder()
                .title(festivalReqDto.title)
                .startDate(festivalReqDto.startDate)
                .endDate(festivalReqDto.endDate)
                .startTime(festivalReqDto.startTime)
                .endTime(festivalReqDto.endTime)
                .timeDescription(festivalReqDto.timeDescription)
                .latitude(festivalReqDto.latitude)
                .longitude(festivalReqDto.longitude)
                .address(festivalReqDto.address)
                .fee(festivalReqDto.fee)
                .contact(festivalReqDto.contact)
                .homePageUrl(festivalReqDto.homePageUrl)
                .reservationUrl(festivalReqDto.reservationUrl)
                .description(festivalReqDto.description)
                .onelineDescription(festivalReqDto.onelineDescription)
                .creator(creator)
                .thumbnailImageUrl(festivalReqDto.mainImage)
                .imageUrls(festivalReqDto.images)
                .build();
    }
}

