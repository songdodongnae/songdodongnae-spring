package com.culturefinder.songdodongnae.creator.dto;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Schema(description = "크리에이터 생성/수정 요청 DTO")
@AllArgsConstructor
@Getter
public class CreatorReqDto {

    @Schema(description = "크리에이터 이름", example = "송도맛집탐험가")
    @NotNull
    private String name;

    @Schema(description = "크리에이터 소개", example = "송도지역 맛집을 전문적으로 소개하는 크리에이터")
    private String introduction;

    @Schema(description = "크리에이터 상세 설명")
    private String description;

    @Schema(description = "크리에이터 이미지 URL")
    private String image;

    public static Creator toEntity(CreatorReqDto creatorReqDto) {
        return Creator.builder()
                .name(creatorReqDto.getName())
                .introduction(creatorReqDto.getIntroduction())
                .description(creatorReqDto.getDescription())
                .imageUrl(creatorReqDto.getImage())
                .build();
    }
}
