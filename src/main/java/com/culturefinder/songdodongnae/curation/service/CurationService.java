package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public CurationResDto createCuration(Long userId, CurationReqDto curationReqDto) {
        isAdmin(userId);

        Creator creator = creatorRepository.findByName(curationReqDto.getCreatorName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        Curation curation = curationReqDto.toEntity(creator);
        Curation savedCuration = curationRepository.saveCuration(curation);
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
        Boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.CURATION, id);
        Set<Long> bookmarkedDeliciousSpots = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));
        Set<Long> bookmarkedFestivals = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        Curation curationById = curationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        Creator creator = creatorRepository.findByName(curationById.getCreator().getName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        return CurationResDto.fromEntity(curationById, creator, bookmarkedDeliciousSpots, bookmarkedFestivals, isBookmarked);
    }

    public CustomPage<CurationThumbnailResDto> getAllCuration(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<Curation> curations = curationRepository.findAll(offset, pageSize);
        long totalElements = curationRepository.countCuration();

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

    public CustomPage<CurationThumbnailResDto> getAllUserCuration(Long userId, int currentPage, int pageSize) {
        List<Long> targetIdsByUserAndType = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION);
        Set<Long> bookmarkedSet = new HashSet<>(targetIdsByUserAndType);

        int offset = (currentPage - 1) * pageSize;
        List<Curation> curations = curationRepository.findAll(offset, pageSize);
        long totalElements = curationRepository.countCuration();

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

    public CurationResDto updateCuration(Long userId, Long id, CurationReqDto curationReqDto){
        isAdmin(userId);

        if (curationReqDto.getImageUrl() != null) {
            s3UploadService.deleteFile(curationReqDto.getImageUrl());
        }

        Curation curation = curationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        Creator findCreator = creatorRepository.findByName(curationReqDto.getCreatorName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        curation.update(curationReqDto.toEntity(findCreator));
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

}
