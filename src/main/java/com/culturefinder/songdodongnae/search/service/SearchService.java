package com.culturefinder.songdodongnae.search.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.search.repository.SearchRepository;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final FestivalRepository festivalRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final CurationRepository curationRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    public SearchSummaryResDto getSearchSummary(@Nullable Long userId, String query) {
        List<Festival> top3Festival = searchRepository.findTop3Festival(query);
        List<DeliciousSpot> top3DeliciousSpot = searchRepository.findTop3DeliciousSpot(query);
        List<Curation> top3Curation = searchRepository.findTop3Curation(query);

        if (userId == null) return SearchSummaryResDto.from(query, top3Festival, top3DeliciousSpot, top3Curation);
        else {
            Set<Long> deliciousSpotIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));
            Set<Long> festivalIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));
            Set<Long> curationIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION));
            return SearchSummaryResDto.from(query, top3Festival, top3DeliciousSpot, top3Curation, deliciousSpotIds, festivalIds, curationIds);
        }
    }

    public CustomPage<FestivalResDto> searchFestivals(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Festival> festivals = festivalRepository.searchFestivals(keyword, offset, pageSize);
        long totalElements = festivalRepository.countSearchFestivals(keyword);

        List<FestivalResDto> dtos = festivals.stream()
                .map(festival -> FestivalResDto.fromEntity(festival, false))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<FestivalResDto> searchUserFestivals(String keyword, int currentPage, int pageSize, Long userId) {
        if(userId == null) throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);

        int offset = (currentPage - 1) * pageSize;

        List<Festival> festivals = festivalRepository.searchFestivals(keyword, offset, pageSize);
        long totalElements = festivalRepository.countSearchFestivals(keyword);

        Set<Long> festivalIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        List<FestivalResDto> dtos = festivals.stream()
                .map(festival -> FestivalResDto.fromEntity(festival, festivalIds.contains(festival.getId())))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<DeliciousSpotResDto> searchDeliciousSpots(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<DeliciousSpot> deliciousSpotList = deliciousSpotRepository.searchDeliciousSpots(keyword, offset, pageSize);
        long totalElements = deliciousSpotRepository.countSearchDeliciousSpot(keyword);
        List<DeliciousSpotResDto> dtos = deliciousSpotList.stream()
                .map(deliciousSpot -> DeliciousSpotResDto.fromEntity(deliciousSpot, false))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<DeliciousSpotResDto> searchUserDeliciousSpots(String keyword, int currentPage, int pageSize, Long userId) {
        if(userId == null) throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);

        int offset = (currentPage - 1) * pageSize;

        List<DeliciousSpot> deliciousSpotList = deliciousSpotRepository.searchDeliciousSpots(keyword, offset, pageSize);
        long totalElements = deliciousSpotRepository.countSearchDeliciousSpot(keyword);
        Set<Long> deliciousSpotIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));

        List<DeliciousSpotResDto> dtos = deliciousSpotList.stream()
                .map(deliciousSpot -> DeliciousSpotResDto.fromEntity(deliciousSpot, deliciousSpotIds.contains(deliciousSpot.getId())))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationThumbnailResDto> searchCuration(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Curation> curations  = curationRepository.searchCurations(keyword, offset, pageSize);
        long totalElements = curationRepository.countSearchCurations(keyword);
        List<CurationThumbnailResDto> dtos = curations.stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, false))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationThumbnailResDto> searchUserCuration(String keyword, int currentPage, int pageSize, Long userId) {
        if(userId == null) throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);

        int offset = (currentPage - 1) * pageSize;

        List<Curation> curations  = curationRepository.searchCurations(keyword, offset, pageSize);
        long totalElements = curationRepository.countSearchCurations(keyword);
        Set<Long> curationIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION));

        List<CurationThumbnailResDto> dtos = curations.stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, curationIds.contains(curation.getId())))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }
}
