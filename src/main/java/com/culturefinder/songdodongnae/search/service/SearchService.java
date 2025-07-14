package com.culturefinder.songdodongnae.search.service;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.search.repository.SearchRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final FestivalRepository festivalRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final CurationRepository curationRepository;

    public SearchSummaryResDto getSearchSummary(String query) {
        List<Festival> top3Festival = searchRepository.findTop3Festival(query);
        List<DeliciousSpot> top3DeliciousSpot = searchRepository.findTop3DeliciousSpot(query);
        List<Curation> top3Curation = searchRepository.findTop3Curation(query);

        return SearchSummaryResDto.from(query, top3Festival, top3DeliciousSpot, top3Curation);
    }

    public CustomPage<FestivalResDto> searchFestivals(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Festival> festivals = festivalRepository.searchFestivals(keyword, offset, pageSize);
        long totalElements = festivalRepository.countSearchFestivals(keyword);
        List<FestivalResDto> dtos = festivals.stream()
                .map(FestivalResDto::fromEntity)
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<DeliciousSpotResponseDto> searchDeliciousSpots(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<DeliciousSpot> deliciousSpotList = deliciousSpotRepository.searchDeliciousSpots(keyword, offset, pageSize);
        long totalElements = deliciousSpotRepository.countSearchDeliciousSpot(keyword);
        List<DeliciousSpotResponseDto> dtos = deliciousSpotList.stream()
                .map(DeliciousSpotResponseDto::fromEntity)
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationResDto> searchCuration(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Curation> curations  = curationRepository.searchCurations(keyword, offset, pageSize);
        long totalElements = curationRepository.countSearchCurations(keyword);
        List<CurationResDto> dtos = curations.stream()
                .map(CurationResDto::fromEntity)
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }
}
