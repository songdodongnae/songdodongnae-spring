package com.culturefinder.songdodongnae.search.dto;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public class SearchSummaryResDto {

    String query;
    List<FestivalResDto> festivalResDtoList;
    List<DeliciousSpotResDto> deliciousSpotResDtos;
    List<CurationThumbnailResDto> curationResDtos;

    public static SearchSummaryResDto from(
            String query,
            List<Festival> festivals,
            List<DeliciousSpot> deliciousSpots,
            List<Curation> curations) {

        List<FestivalResDto> festivalDtos = festivals.stream()
                .map((Festival festival) -> FestivalResDto.fromEntity(festival, false))
                .toList();
        List<DeliciousSpotResDto> deliciousSpotDtos = deliciousSpots.stream()
                .map(deliciousSpot -> DeliciousSpotResDto.fromEntity(deliciousSpot, false))
                .toList();
        List<CurationThumbnailResDto> curationDtos = curations.stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, false))
                .toList();
        return SearchSummaryResDto.builder()
                .query(query)
                .festivalResDtoList(festivalDtos)
                .deliciousSpotResDtos(deliciousSpotDtos)
                .curationResDtos(curationDtos)
                .build();
    }

    public static SearchSummaryResDto from(
            String query,
            List<Festival> festivals,
            List<DeliciousSpot> deliciousSpots,
            List<Curation> curations,
            Set<Long> deliciousSpotIds,
            Set<Long> festivalIds,
            Set<Long> curationIds) {

        List<FestivalResDto> festivalDtos = festivals.stream()
                .map(festival -> FestivalResDto.fromEntity(festival, festivalIds.contains(festival.getId())))
                .toList();
        List<DeliciousSpotResDto> deliciousSpotDtos = deliciousSpots.stream()
                .map(deliciousSpot -> DeliciousSpotResDto.fromEntity(deliciousSpot, deliciousSpotIds.contains(deliciousSpot.getId())))
                .toList();
        List<CurationThumbnailResDto> curationDtos = curations.stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, curationIds.contains(curation.getId())))
                .toList();
        return SearchSummaryResDto.builder()
                .query(query)
                .festivalResDtoList(festivalDtos)
                .deliciousSpotResDtos(deliciousSpotDtos)
                .curationResDtos(curationDtos)
                .build();
    }
}
