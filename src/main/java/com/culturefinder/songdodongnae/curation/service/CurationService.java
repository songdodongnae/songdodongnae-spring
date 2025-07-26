package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
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
    private CurationRepository curationRepository;

    public CurationResDto getCuration(Long id) {
        Curation curationById = curationRepository.findCurationById(id);
        CurationResDto curationResDto = CurationResDto.fromEntity(curationById);
        return curationResDto;
    }

    public CurationResDto getUserCuration(Long userId, Long id) {
        Boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.CURATION, id);
        Set<Long> bookmarkedDeliciousSpots = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));
        Set<Long> bookmarkedFestivals = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));
        Curation curationById = curationRepository.findCurationById(id);
        CurationResDto curationResDto = CurationResDto.fromEntity(curationById, bookmarkedDeliciousSpots, bookmarkedFestivals, isBookmarked);
        return curationResDto;
    }

    public CurationResDto createCuration(Long userId, CurationReqDto curationReqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        if (user.getRole() != Role.ROLE_ADMIN) new CustomException(ErrorCode.FORBIDDEN);

        Curation curation = curationReqDto.toEntity();
        Curation savedCuration = curationRepository.saveCuration(curation);
        return CurationResDto.fromEntity(savedCuration);
    }

    public CurationResDto updateCuration(Long userId, Long id, CurationReqDto curationReqDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        if (user.getRole() != Role.ROLE_ADMIN) new CustomException(ErrorCode.FORBIDDEN);

        if (curationReqDto.getImageUrl() != null) {
            s3UploadService.deleteFile(curationReqDto.getImageUrl());
        }

        Curation curation = curationRepository.findCurationById(id);
        curation.update(curationReqDto.toEntity());
        return CurationResDto.fromEntity(curation);
    }

    public CurationResDto deleteCuration(Long userId, Long id) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        if (user.getRole() != Role.ROLE_ADMIN) throw new CustomException(ErrorCode.FORBIDDEN);

        Curation curationById = curationRepository.findCurationById(id);
        if(curationById == null) throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        bookmarkRepository.deleteBookmarkByTypeAndTargetId(BookmarkType.CURATION, id);


        curationRepository.deleteById(id);
        if (curationById.getImageUrl() != null) {
            s3UploadService.deleteFile(curationById.getImageUrl());
        }

        return CurationResDto.fromEntity(curationById);
    }

    public CustomPage<CurationResDto> getAllCuration(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Curation> curations = curationRepository.findAll(offset, pageSize);
        List<CurationResDto> curationsDto = curations.stream()
                .map(CurationResDto::fromEntity)
                .toList();
        long totalElements = curationRepository.countCuration();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }

    public CustomPage<CurationResDto> getAllUserCuration(Long userId, int currentPage, int pageSize) {
        List<Long> targetIdsByUserAndType = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION);
        Set<Long> bookmarkedSet = new HashSet<>(targetIdsByUserAndType);
        Set<Long> bookmarkedDeliciousSpots = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT));
        Set<Long> bookmarkedFestivals = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        int offset = (currentPage - 1) * pageSize;

        List<Curation> curations = curationRepository.findAll(offset, pageSize);
        List<CurationResDto> curationsDto = curations.stream()
                .map(curation -> {
                    return CurationResDto.fromEntity(curation,
                            bookmarkedDeliciousSpots,
                            bookmarkedFestivals,
                            bookmarkedSet.contains(curation.getId()));
                })
                .toList();
        long totalElements = curationRepository.countCuration();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }
}
