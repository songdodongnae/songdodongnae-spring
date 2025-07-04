package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class FestivalReqDto {

    @NotNull
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

    @NotNull
    private String description;

    private String onelineDescription;

    public Festival toEntity() {
        return Festival.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .timeDescription(this.timeDescription)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .address(this.address)
                .fee(this.fee)
                .contact(this.contact)
                .homePageUrl(this.homePageUrl)
                .reservationUrl(this.reservationUrl)
                .description(this.description)
                .onelineDescription(this.onelineDescription)
                .build();
    }
}

