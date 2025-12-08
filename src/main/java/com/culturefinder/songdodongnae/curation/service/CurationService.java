package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationSortType;
import com.culturefinder.songdodongnae.curation.domain.CurationType;
import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.common.exception.CustomException;
import com.culturefinder.songdodongnae.common.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.common.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.common.utils.CustomPage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@RequiredArgsConstructor
@Service
public class CurationService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final CurationRepository curationRepository;
    private final CreatorRepository creatorRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final FestivalRepository festivalRepository;

    public CurationResDto createCuration(Long userId, CurationReqDto curationReqDto) {
        isAdmin(userId);

        Creator creator = creatorRepository.findByName(curationReqDto.getCreatorName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        List<DeliciousSpot> deliciousSpots = new ArrayList<>();
        List<Festival> festivals = new ArrayList<>();

        if (curationReqDto.getType() == CurationType.DELICIOUS_SPOT)
            deliciousSpots = deliciousSpotRepository.findAllById(curationReqDto.getIds());
        else if (curationReqDto.getType() == CurationType.FESTIVAL)
            festivals = festivalRepository.findAllById(curationReqDto.getIds());

        Curation curation = CurationReqDto.toEntity(curationReqDto, creator, deliciousSpots, festivals);
        Curation savedCuration = curationRepository.save(curation);
        return CurationResDto.fromEntity(savedCuration, creator, false);
    }

    public CurationResDto getCuration(Long id) {
        Curation curationById = curationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        Creator creator = creatorRepository.findByName(curationById.getCreator().getName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        return CurationResDto.fromEntity(curationById, creator, false);
    }

    public CurationResDto getUserCuration(Long userId, Long id) {
        validUserId(userId);

        Boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.CURATION, id);
        Set<Long> bookmarkedDeliciousSpots = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));
        Set<Long> bookmarkedFestivals = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        Curation curationById = curationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        Creator creator = creatorRepository.findByName(curationById.getCreator().getName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        return CurationResDto.fromEntity(curationById, creator, bookmarkedDeliciousSpots, bookmarkedFestivals, isBookmarked);
    }

    public CustomPage<CurationThumbnailResDto> getAllCuration(int currentPage, int pageSize, CurationSortType curationSortType) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        List<Curation> curations;

        if (curationSortType == CurationSortType.BOOKMARK) {
            curations = curationRepository.findAllByBookmarkCount(pageable);
        } else {
            curations = curationRepository.findAllByCreatedAt(pageable);
        }

        long totalElements = curationRepository.count();

        List<CurationThumbnailResDto> curationsDto = curations.stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, false))
                .toList();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationThumbnailResDto> getAllUserCuration(Long userId, int currentPage, int pageSize, CurationSortType curationSortType) {
        validUserId(userId);

        List<Long> targetIdsByUserAndType = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION);
        Set<Long> bookmarkedSet = new HashSet<>(targetIdsByUserAndType);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        List<Curation> curations;

        if (curationSortType == CurationSortType.BOOKMARK) {
            curations = curationRepository.findAllByBookmarkCount(pageable);
        } else {
            curations = curationRepository.findAllByCreatedAt(pageable);
        }

        long totalElements = curationRepository.count();

        List<CurationThumbnailResDto> curationsDto = curations.stream()
                .map(curation -> {
                    return CurationThumbnailResDto.fromEntity(curation,
                            bookmarkedSet.contains(curation.getId()));
                })
                .toList();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationThumbnailResDto> getAllCurationByType(int currentPage, int pageSize, CurationType type) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        List<Curation> curations = curationRepository.findAllByCreatedAt(pageable);
        long totalElements = curationRepository.count();

        List<CurationThumbnailResDto> curationsDto = curations.stream()
                    .filter(curation -> curation.getType() == type)
                    .map(curation -> CurationThumbnailResDto.fromEntity(curation, false))
                    .toList();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationThumbnailResDto> getAllUserCurationByType(Long userId, int currentPage, int pageSize, CurationType type) {
        validUserId(userId);

        List<Long> targetIdsByUserAndType = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION);
        Set<Long> bookmarkedSet = new HashSet<>(targetIdsByUserAndType);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        List<Curation> curations = curationRepository.findAllByCreatedAt(pageable);
        long totalElements = curationRepository.count();

        List<CurationThumbnailResDto> curationsDto = curations.stream()
                .filter(curation -> curation.getType() == type)
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, bookmarkedSet.contains(curation.getId())))
                .toList();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CurationResDto updateCuration(Long userId, Long id, CurationReqDto curationReqDto){
        isAdmin(userId);

        if (curationReqDto.getImageUrl() != null) {
            s3UploadService.deleteFile(curationReqDto.getImageUrl());
        }

        Curation curation = curationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        Creator findCreator = creatorRepository.findByName(curationReqDto.getCreatorName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        List<DeliciousSpot> deliciousSpots = new ArrayList<>();
        List<Festival> festivals = new ArrayList<>();

        if (curationReqDto.getType() == CurationType.DELICIOUS_SPOT)
            deliciousSpots = deliciousSpotRepository.findAllById(curationReqDto.getIds());
        else if (curationReqDto.getType() == CurationType.FESTIVAL)
            festivals = festivalRepository.findAllById(curationReqDto.getIds());

        curation.update(
                curationReqDto.getTitle(),
                curationReqDto.getDescription(),
                curationReqDto.getType(),
                curationReqDto.getImageUrl(),
                findCreator,
                festivals,
                deliciousSpots
        );
        return CurationResDto.fromEntity(curation, findCreator, false);
    }

    public CurationResDto deleteCuration(Long userId, Long id) {
        isAdmin(userId);

        Curation curationById = curationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        Creator findCreator = creatorRepository.findByName(curationById.getCreator().getName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        bookmarkRepository.deleteBookmarkByTypeAndTargetId(BookmarkType.CURATION, id);
        if (curationById.getImageUrl() != null) {
            s3UploadService.deleteFile(curationById.getImageUrl());
        }
        curationRepository.deleteById(id);

        return CurationResDto.fromEntity(curationById, findCreator, false);
    }

    private void isAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        if (user.getRole() != Role.ROLE_ADMIN) throw new CustomException(ErrorCode.FORBIDDEN);
    }

    private void validUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    }

}
