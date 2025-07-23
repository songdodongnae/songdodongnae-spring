package com.culturefinder.songdodongnae.search.dto;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import lombok.Builder;

import java.util.List;

@Builder
public class SearchSummaryResDto {

    String query;
    List<FestivalResDto> festivalResDtoList;
    List<DeliciousSpotResDto> deliciousSpotResDtos;
    List<CurationResDto> curationResDtos;

    public static SearchSummaryResDto from(
            String query,
            List<Festival> festivals,
            List<DeliciousSpot> deliciousSpots,
            List<Curation> curations) {

        List<FestivalResDto> festivalDtos = festivals.stream()
                .map(FestivalResDto::fromEntity)
                .toList();
        List<DeliciousSpotResDto> deliciousSpotDtos = deliciousSpots.stream()
                .map(DeliciousSpotResDto::fromEntity)
                .toList();
        List<CurationResDto> curationDtos = curations.stream()
                .map(CurationResDto::fromEntity)
                .toList();
        return SearchSummaryResDto.builder()
                .query(query)
                .festivalResDtoList(festivalDtos)
                .deliciousSpotResDtos(deliciousSpotDtos)
                .curationResDtos(curationDtos)
                .build();
    }
}
