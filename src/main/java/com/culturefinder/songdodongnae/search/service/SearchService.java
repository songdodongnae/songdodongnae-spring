package com.culturefinder.songdodongnae.search.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.common.exception.CustomException;
import com.culturefinder.songdodongnae.common.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.search.dto.SearchSummaryResDto;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.common.utils.CustomPage;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final FestivalRepository festivalRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final CurationRepository curationRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    public SearchSummaryResDto getSearchSummary(@Nullable Long userId, String query) {
        Pageable top3 = PageRequest.of(0, 3);
        List<Festival> top3Festival = festivalRepository.findTop3Festival(query.toLowerCase(), top3);
        List<DeliciousSpot> top3DeliciousSpot = deliciousSpotRepository.findTop3DeliciousSpot(query);
        List<Curation> top3Curation = curationRepository.findTop3Curation(query);

        if (userId == null) return SearchSummaryResDto.from(query, top3Festival, top3DeliciousSpot, top3Curation);
        else {
            Set<Long> deliciousSpotIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));
            Set<Long> festivalIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));
            Set<Long> curationIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION));
            return SearchSummaryResDto.from(query, top3Festival, top3DeliciousSpot, top3Curation, deliciousSpotIds, festivalIds, curationIds);
        }
    }

    public CustomPage<FestivalThumbnailResDto> searchFestivals(String keyword, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        long totalElements = festivalRepository.countSearchFestivals(keyword);

        List<FestivalThumbnailResDto> dtos = festivalRepository.searchFestivals(keyword, pageable).stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival,
                        festival.getCreator().getName(),
                        false))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<FestivalThumbnailResDto> searchUserFestivals(String keyword, int currentPage, int pageSize, Long userId) {
        validUserId(userId);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        long totalElements = festivalRepository.countSearchFestivals(keyword);

        Set<Long> festivalIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        List<FestivalThumbnailResDto> dtos = festivalRepository.searchFestivals(keyword, pageable).stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival, festival.getCreator().getName() ,festivalIds.contains(festival.getId())))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<DeliciousSpotThumbnailResDto> searchDeliciousSpots(String keyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        long totalElements = deliciousSpotRepository.countSearchDeliciousSpot(keyword);

        List<DeliciousSpotThumbnailResDto> dtos = deliciousSpotRepository.searchDeliciousSpots(keyword, offset, pageSize).stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot, deliciousSpot.getCreator().getName(), false))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<DeliciousSpotThumbnailResDto> searchUserDeliciousSpots(String keyword, int currentPage, int pageSize, Long userId) {
        validUserId(userId);

        int offset = (currentPage - 1) * pageSize;
        long totalElements = deliciousSpotRepository.countSearchDeliciousSpot(keyword);

        Set<Long> deliciousSpotIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));

        List<DeliciousSpotThumbnailResDto> dtos = deliciousSpotRepository.searchDeliciousSpots(keyword, offset, pageSize).stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot, deliciousSpot.getCreator().getName() ,deliciousSpotIds.contains(deliciousSpot.getId())))
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
        long totalElements = curationRepository.countSearchCurations(keyword);

        List<CurationThumbnailResDto> dtos = curationRepository.searchCurations(keyword, offset, pageSize).stream()
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
        validUserId(userId);

        int offset = (currentPage - 1) * pageSize;
        long totalElements = curationRepository.countSearchCurations(keyword);

        Set<Long> curationIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION));

        List<CurationThumbnailResDto> dtos =  curationRepository.searchCurations(keyword, offset, pageSize).stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, curationIds.contains(curation.getId())))
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }

    private void validUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    }

}
