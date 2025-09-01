package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.curation.repository.CurationFestivalRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final S3UploadService s3UploadService;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CreatorRepository creatorRepository;
    private final CurationFestivalRepository curationFestivalRepository;

    @Transactional
    public FestivalResDto createFestival(FestivalReqDto festivalReqDto, Long userId) {
        isAdmin(userId);

        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        Festival festival = FestivalReqDto.toEntity(festivalReqDto, findCreator);
        Festival savedFestival = festivalRepository.saveFestival(festival);

        return FestivalResDto.fromEntity(savedFestival, false);
    }

    @Transactional(readOnly = true)
    public FestivalResDto getFestival(Long id) {
        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        return FestivalResDto.fromEntity(findFestival, false);
    }

    @Transactional(readOnly = true)
    public FestivalResDto getUserFestival(Long id, Long userId) {
        validUserId(userId);

        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.FESTIVAL, findFestival.getId());

        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }
  
    @Transactional(readOnly = true)
    public FestivalResDto getFestivalV2(Long id) {
        Festival findFestival = festivalRepository.findByIdWithCreator(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        return FestivalResDto.fromEntity(findFestival, false);
    }

    @Transactional(readOnly = true)
    public FestivalResDto getUserFestivalV2(Long id, Long userId) {
        validUserId(userId);
      
        Festival findFestival = festivalRepository.findByIdWithCreator(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.FESTIVAL, findFestival.getId());

        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }

    @Transactional(readOnly = true)
    public List<FestivalThumbnailResDto> getFestivalsByYearAndMonth(int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        List<Festival> findFestivalsByYearAndMonth = festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth);

        return findFestivalsByYearAndMonth.stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(
                        festival,
                        festival.getCreator().getName(), false
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FestivalThumbnailResDto> getFestivalsUserByYearAndMonth(int year, int month, Long userId) {
        validUserId(userId);

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        List<Festival> findFestivalsByYearAndMonth = festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth);

        Set<Long> bookmarkedFestivalIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        return findFestivalsByYearAndMonth.stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(
                        festival,
                        festival.getCreator().getName(),
                        bookmarkedFestivalIds.contains(festival.getId())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomPage<FestivalThumbnailResDto> getAllFestival(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        long totalElements = festivalRepository.countFestivals();

        List<FestivalThumbnailResDto> festivals = festivalRepository.findAll(offset, pageSize).stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival, festival.getCreator().getName(), false))
                .toList();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );

    }

    @Transactional(readOnly = true)
    public CustomPage<FestivalThumbnailResDto> getAllUserFestival(int currentPage, int pageSize, Long userId) {
        validUserId(userId);

        Set<Long> bookmarkedFestivalIds = new HashSet<>(bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL));

        int offset = (currentPage - 1) * pageSize;
        long totalElements = festivalRepository.countFestivals();

        List<FestivalThumbnailResDto> festivals = festivalRepository.findAll(offset, pageSize).stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival,
                        festival.getCreator().getName(),
                        bookmarkedFestivalIds.contains(festival.getId())))
                .toList();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );

    }

    @Transactional(readOnly = true)
    public CustomPage<FestivalThumbnailResDto> getAllFestivalV2(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        long totalElements = festivalRepository.countFestivals();

        List<FestivalThumbnailResDto> festivals = festivalRepository.findAllWithCreator(offset, pageSize).stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival, festival.getCreator().getName(), false))
                .toList();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );
    }

    @Transactional(readOnly = true)
    public CustomPage<FestivalThumbnailResDto> getAllUserFestivalV2(int currentPage, int pageSize, Long userId) {
        validUserId(userId);

        int offset = (currentPage - 1) * pageSize;
        long totalElements = festivalRepository.countFestivals();

        List<FestivalThumbnailResDto> festivals = festivalRepository.findAllWithCreatorAndBookmarkStatus(offset, pageSize, userId)
                .stream()
                .map(result -> FestivalThumbnailResDto.fromEntity(
                        result.getFestival(),
                        result.getFestival().getCreator().getName(),
                        result.getIsBookmarked()))
                .toList();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );
    }

    @Transactional
    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalReqDto, Long userId) {
        isAdmin(userId);

        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        if (findFestival.getThumbnailImageUrl() != null) {
            s3UploadService.deleteFile(findFestival.getThumbnailImageUrl());
        }
        if (findFestival.getImageUrls() != null) {
            for (String imageUrl : findFestival.getImageUrls()) {
                s3UploadService.deleteFile(imageUrl);
            }
        }

        findFestival.update(FestivalReqDto.toEntity(festivalReqDto, findCreator));
        return FestivalResDto.fromEntity(findFestival, false);
    }

    @Transactional
    public FestivalResDto deleteFestival(Long id, Long userId) {
        isAdmin(userId);

        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        if (findFestival.getThumbnailImageUrl() != null) {
            s3UploadService.deleteFile(findFestival.getThumbnailImageUrl());
        }
        if (findFestival.getImageUrls() != null) {
            for (String imageUrl : findFestival.getImageUrls()) {
                s3UploadService.deleteFile(imageUrl);
            }
        }
        bookmarkRepository.deleteBookmarkByTypeAndTargetId(BookmarkType.FESTIVAL, findFestival.getId());
        curationFestivalRepository.deleteByFestivalId(findFestival.getId());
        festivalRepository.deleteFestival(id);
        return FestivalResDto.fromEntity(findFestival, false);
    }

    private void isAdmin(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        if (findUser.getRole() != Role.ROLE_ADMIN)
            throw new CustomException(ErrorCode.FORBIDDEN);
    }

    private void validUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    }

}
