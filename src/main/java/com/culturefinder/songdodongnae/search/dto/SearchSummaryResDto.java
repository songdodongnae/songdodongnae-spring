package com.culturefinder.songdodongnae.search.dto;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class SearchSummaryResDto {

    String query;
    List<FestivalThumbnailResDto> festivalResDtoList;
    List<DeliciousSpotThumbnailResDto> deliciousSpotResDtos;
    List<CurationThumbnailResDto> curationResDtos;

    public static SearchSummaryResDto from(
            String query,
            List<Festival> festivals,
            List<DeliciousSpot> deliciousSpots,
            List<Curation> curations) {

        List<FestivalThumbnailResDto> festivalDtos = festivals.stream()
                .map((Festival festival) -> FestivalThumbnailResDto.fromEntity(festival, null, false))
                .toList();
        List<DeliciousSpotThumbnailResDto> deliciousSpotDtos = deliciousSpots.stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot, null, false))
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

        List<FestivalThumbnailResDto> festivalDtos = festivals.stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival, null, festivalIds.contains(festival.getId())))
                .toList();
        List<DeliciousSpotThumbnailResDto> deliciousSpotDtos = deliciousSpots.stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot, null, deliciousSpotIds.contains(deliciousSpot.getId())))
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
