package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;
import java.util.*;


@Builder
@Getter
@AllArgsConstructor
public class DeliciousSpotReqDto {

    @NotBlank(message = "맛집 제목은 필수입니다")
    @Schema(description = "맛집 제목", example = "송도 파스타 전문점")
    private String title;

    @NotBlank(message = "크리에이터 이름은 필수입니다")
    @Schema(description = "크리에이터 이름", example = "송이")
    private String creatorName;

    @Schema(description = "위도", example = "37.3855")
    private double latitude;

    @Schema(description = "경도", example = "126.6544")
    private double longitude;

    @Schema(description = "주소", example = "인천시 연수구 송도동 123-45")
    private String address;

    @Schema(description = "가격 (원)", example = "15000")
    private Integer price;

    @Schema(description = "네이버 평점", example = "4.2")
    private Float naverRating;

    @Schema(description = "카카오 평점", example = "4.1")
    private Float kakaoRating;

    @Schema(description = "영업 시작 시간", example = "09:00")
    private LocalTime startTime;

    @Schema(description = "영업 종료 시간", example = "22:00")
    private LocalTime endTime;

    @Schema(description = "영업시간 설명", example = "월~금 09:00-22:00, 주말 10:00-23:00")
    private String timeDescription;

    @Schema(description = "대기시간 정보", example = "주말 30분 대기")
    private String waiting;

    @Schema(description = "주차 정보", example = "전용 주차장 이용 가능")
    private String parking;

    @Schema(description = "추천 메뉴", example = "크림파스타, 오일파스타")
    private String suggestionMenu;

    @NotBlank(message = "맛집 설명은 필수입니다")
    @Schema(description = "맛집 상세 설명", example = "송도에서 가장 유명한 파스타 전문점으로, 수제 파스타와 신선한 소스가 인상적인 곳입니다.")
    private String description;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumbnail.jpg")
    private String thumbnailImageUrl;

    @Schema(description = "추가 이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private List<String> imageUrls;

    @Schema(description = "한 줄 소개", example = "송도 최고의 파스타 맛집!")
    private String onelineDescription;

    @Schema(description = "인스타그램 계정", example = "@songdo_pasta")
    private String instagram;

    @Schema(description = "연락처", example = "032-123-4567")
    private String contact;

    public static DeliciousSpot toEntity(DeliciousSpotReqDto dto, Creator creator) {
        return DeliciousSpot.builder()
                .title(dto.getTitle())
                .creator(creator)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .address(dto.getAddress())
                .price(dto.getPrice())
                .naverRating(dto.getNaverRating())
                .kakaoRating(dto.getKakaoRating())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .timeDescription(dto.getTimeDescription())
                .waiting(dto.getWaiting())
                .parking(dto.getParking())
                .suggestionMenu(dto.getSuggestionMenu())
                .description(dto.getDescription())
                .thumbnailImageUrl(dto.getThumbnailImageUrl())
                .imageUrls(dto.getImageUrls())
                .onelineDescription(dto.getOnelineDescription())
                .instagram(dto.getInstagram())
                .contact(dto.getContact())
                .build();
    }

}
