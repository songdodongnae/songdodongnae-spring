package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@Getter
public class FestivalReqDto {

    @NotBlank(message = "축제 제목은 필수입니다")
    @Schema(description = "축제 제목", example = "송도 원두의 꽃 축제")
    private String title;

    @NotBlank(message = "크리에이터 이름은 필수입니다")
    @Schema(description = "크리에이터 이름", example = "송이")
    private String creatorName;

    @Schema(description = "축제 시작일", example = "2024-05-15")
    private LocalDate startDate;

    @Schema(description = "축제 종료일", example = "2024-05-20")
    private LocalDate endDate;

    @Schema(description = "축제 시작 시간", example = "10:00")
    private LocalTime startTime;

    @Schema(description = "축제 종료 시간", example = "21:00")
    private LocalTime endTime;

    @Schema(description = "시간 상세 설명", example = "매일 10:00-21:00, 우천시 일정 변경")
    private String timeDescription;

    @Schema(description = "위도", example = "37.3855")
    private double latitude;

    @Schema(description = "경도", example = "126.6544")
    private double longitude;

    @Schema(description = "축제 개최 주소", example = "인천시 연수구 송도동 송도 센트럴파크")
    private String address;

    @Schema(description = "참가 비용", example = "무료 (일부 체험 유료)")
    private String fee;

    @Schema(description = "문의 연락처", example = "032-123-4567")
    private String contact;

    @Schema(description = "공식 홈페이지", example = "https://songdofestival.com")
    private String homePageUrl;

    @Schema(description = "예약 사이트", example = "https://booking.songdofestival.com")
    private String reservationUrl;

    @NotBlank(message = "축제 설명은 필수입니다")
    @Schema(description = "축제 상세 설명", example = "송도 원두의 아름다운 꽃을 주제로 한 축제로, 다양한 체험 프로그램과 공연이 준비되어 있습니다.")
    private String description;

    @Schema(description = "한 줄 소개", example = "송도의 아름다운 꽃과 함께하는 춘 축제!")
    private String onelineDescription;

    @Schema(description = "메인 이미지 URL", example = "https://example.com/festival-main.jpg")
    private String mainImage;
    @Schema(description = "추가 이미지 URL 목록", example = "[\"https://example.com/festival1.jpg\", \"https://example.com/festival2.jpg\"]")
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

