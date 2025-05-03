package com.culturefinder.songdodongnae.admin.delicious_spot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class AdminDeliciousSpotCreateRequestDto {

    private String name;

    private String location;

    private Integer price;

    private Float naverRating;

    private Float kakaoRating;

    private LocalTime startTime;

    private LocalTime endTime;

    private String waiting;

    private String parking;

    private String suggestionMenu;

    private String description;

    private String onelineDescription;

    private String instagram;

    private String contact;

    private Integer likes;

    private MultipartFile image;

    private List<MultipartFile> files;
}
