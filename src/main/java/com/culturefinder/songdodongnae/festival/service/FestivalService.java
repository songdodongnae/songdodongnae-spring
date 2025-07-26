package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.culturefinder.songdodongnae.exception.ErrorCode.ENTITY_NOT_FOUND;
import static com.culturefinder.songdodongnae.exception.ErrorCode.FORBIDDEN;

@Transactional
@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final S3UploadService s3UploadService;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CreatorRepository creatorRepository;

    public FestivalResDto createFestival(FestivalReqDto festivalReqDto, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        if (findUser.getRole() != Role.ROLE_ADMIN)
            throw new CustomException(FORBIDDEN);

        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));

        Festival festival = FestivalReqDto.toEntity(festivalReqDto, findCreator);
        Festival savedFestival = festivalRepository.saveFestival(festival);

        return FestivalResDto.fromEntity(savedFestival, false);
    }

    public List<FestivalResDto> getFestivalsByYearAndMonth(int year, int month) {

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Festival> findFestivalsByYearAndMonth = festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth);

        return findFestivalsByYearAndMonth.stream()
                .map(festival -> FestivalResDto.fromEntity(
                        festival,
                        false
                ))
                .toList();
    }

    public List<FestivalResDto> getFestivalsUserByYearAndMonth(int year, int month, Long userId) {

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Festival> findFestivalsByYearAndMonth = festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        Set<Long> bookmarkedFestivalIds = bookmarkRepository.findBookmarkedFestivalIdsByUser(user);
        return findFestivalsByYearAndMonth.stream()
                .map(festival -> FestivalResDto.fromEntity(
                        festival,
                        bookmarkedFestivalIds.contains(festival.getId())
                ))
                .toList();
    }


    public CustomPage<FestivalResDto> getAllFestival(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<FestivalResDto> festivals = festivalRepository.findAll(offset, pageSize).stream()
                .map(festival -> FestivalResDto.fromEntity(festival, false))
                .toList();
        Long totalElements = festivalRepository.countFestivals();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );

    }

    public CustomPage<FestivalResDto> getAllUserFestival(int currentPage, int pageSize, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        Set<Long> bookmarkedFestivalIds = bookmarkRepository.findBookmarkedFestivalIdsByUser(user);

        int offset = (currentPage - 1) * pageSize;
        List<FestivalResDto> festivals = festivalRepository.findAll(offset, pageSize).stream()
                .map(festival -> FestivalResDto.fromEntity(festival, bookmarkedFestivalIds.contains(festival.getId())))
                .toList();


        Long totalElements = festivalRepository.countFestivals();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );

    }

    public FestivalResDto getFestival(Long id) {
        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        return FestivalResDto.fromEntity(findFestival, false);
    }

    public FestivalResDto getUserFestival(Long id, Long userId) {
        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.FESTIVAL, findFestival.getId());

        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }

    public FestivalResDto deleteFestival(Long id, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        if (findUser.getRole() != Role.ROLE_ADMIN)
            throw new CustomException(FORBIDDEN);

        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        if (findFestival.getThumbnailImageUrl() != null) {
            s3UploadService.deleteFile(findFestival.getThumbnailImageUrl());
        }
        if (findFestival.getImageUrls() != null) {
            for (String imageUrl : findFestival.getImageUrls()) {
                s3UploadService.deleteFile(imageUrl);
            }
        }
        bookmarkRepository.deleteBookmarkByTypeAndTargetId(BookmarkType.FESTIVAL, findFestival.getId());
        festivalRepository.deleteFestival(id);
        return FestivalResDto.fromEntity(findFestival, false);
    }

    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalReqDto, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        if (findUser.getRole() != Role.ROLE_ADMIN)
            throw new CustomException(FORBIDDEN);

        Festival findFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));

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
}
