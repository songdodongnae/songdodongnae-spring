package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
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
        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        Festival festival = FestivalReqDto.toEntity(festivalReqDto, findCreator);
        Festival savedFestival = festivalRepository.saveFestival(festival);

        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, savedFestival.getId());
        return FestivalResDto.fromEntity(savedFestival, isBookmarked);
    }

    public List<FestivalResDto> getFestivalsByYearAndMonth(int year, int month, Long userId) {

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

    public FestivalResDto getFestival(Long id, Long userId) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new CustomException(ENTITY_NOT_FOUND);
        }
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, findFestival.getId());
        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }

    public FestivalResDto deleteFestival(Long id, Long userId) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new CustomException(ENTITY_NOT_FOUND);
        }
        s3UploadService.deleteFile(findFestival.getThumbnailImageUrl());
        findFestival.getImageUrls().forEach(s3UploadService::deleteFile);

        festivalRepository.deleteFestival(id);
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, findFestival.getId());
        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }

    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalReqDto, Long userId) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new CustomException(ENTITY_NOT_FOUND);
        }

        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));

        s3UploadService.deleteFile(findFestival.getThumbnailImageUrl());
        findFestival.getImageUrls().forEach(s3UploadService::deleteFile);

        findFestival.update(FestivalReqDto.toEntity(festivalReqDto, findCreator));
        festivalRepository.saveFestival(findFestival);
        festivalRepository.deleteFestival(id);
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, findFestival.getId());
        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }
}
