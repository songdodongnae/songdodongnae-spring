package com.culturefinder.songdodongnae.curation.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Schema(description = "큐래이션 생성/수정 요청 DTO")
@Getter
public class CurationReqDto {

    @Schema(description = "큐래이션 타입", example = "DELICIOUS_SPOT")
    @NotNull
    private CurationType type;

    @Schema(description = "큐래이션에 포함할 콘텐츠 ID 목록")
    private List<Long> ids = new ArrayList<>();

    @Schema(description = "크리에이터 이름", example = "송이")
    @NotNull
    private String creatorName;

    @Schema(description = "큐래이션 제목", example = "송도 1만원 대 맛집 5개")
    @NotNull
    private String title;

    @Schema(description = "큐래이션 설명")
    @NotNull
    private String description;

    @Schema(description = "큐래이션 대표 이미지 URL")
    private String imageUrl;


    public static Curation toEntity(CurationReqDto dto, Creator creator, List<DeliciousSpot> deliciousSpots, List<Festival> festivals) {

        Curation curation = Curation.builder()
                .type(dto.getType())
                .creator(creator)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();

        if (dto.getType() == CurationType.DELICIOUS_SPOT) {
            deliciousSpots.forEach(curation::addDeliciousSpot);
        }

        if (dto.getType() == CurationType.FESTIVAL) {
            festivals.forEach(curation::addFestival);
        }

        return curation;
    }

}
