package com.culturefinder.songdodongnae.search.service;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.search.repository.FestivalSearchRepository;
import com.culturefinder.songdodongnae.search.repository.SearchRepository;
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
    private final FestivalSearchRepository festivalRepository;

    public SearchSummaryResDto getSearchSummary(String query) {
        List<Festival> top3Festival = searchRepository.findTop3Festival(query);
        List<DeliciousSpot> top3DeliciousSpot = searchRepository.findTop3DeliciousSpot(query);
        List<Curation> top3Curation = searchRepository.findTop3Curation(query);

        return SearchSummaryResDto.from(query, top3Festival, top3DeliciousSpot, top3Curation);
    }

    public Page<FestivalResDto> getFestivalPage(String query) {
        return null;
    }
}
