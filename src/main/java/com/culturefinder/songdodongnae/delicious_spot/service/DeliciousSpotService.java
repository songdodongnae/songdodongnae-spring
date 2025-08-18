package com.culturefinder.songdodongnae.delicious_spot.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.curation.repository.CurationDeliciousSpotRepository;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.culturefinder.songdodongnae.exception.ErrorCode.ENTITY_NOT_FOUND;
import static com.culturefinder.songdodongnae.exception.ErrorCode.FORBIDDEN;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotService {

    private final DeliciousSpotRepository deliciousSpotRepository;
    private final BookmarkRepository bookmarkRepository;
    private final S3UploadService s3UploadService;
    private final UserRepository userRepository;
    private final CreatorRepository creatorRepository;
    private final CurationDeliciousSpotRepository curationDeliciousSpotRepository;

    public DeliciousSpotResDto createDeliciousSpot(DeliciousSpotReqDto deliciousSpotReqDto, Long userId) {
        isAdmin(userId);
        Creator findCreator = creatorRepository.findByName(deliciousSpotReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));

        DeliciousSpot deliciousSpot = DeliciousSpotReqDto.toEntity(deliciousSpotReqDto, findCreator);
        DeliciousSpot savedDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        return DeliciousSpotResDto.fromEntity(savedDeliciousSpot, false);
    }

    public DeliciousSpotResDto getDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findById(id)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        return DeliciousSpotResDto.fromEntity(deliciousSpot, false);
    }

    public DeliciousSpotResDto getUserDeliciousSpot(Long id, Long userId) {
        validUserId(userId);

        DeliciousSpot deliciousSpot = deliciousSpotRepository.findById(id)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.DELICIOUS_SPOT, id);
        return DeliciousSpotResDto.fromEntity(deliciousSpot, isBookmarked);
    }

    public CustomPage<DeliciousSpotThumbnailResDto> getAllDeliciousSpots(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        long totalElements = deliciousSpotRepository.countDeliciousSpot();

        List<DeliciousSpotThumbnailResDto> dtos = deliciousSpotRepository.findAll(offset, pageSize).stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot,
                        deliciousSpot.getCreator().getName(),
                        false))
                .toList();

        return CustomPage.of(dtos, currentPage, pageSize, totalElements);
    }

    public CustomPage<DeliciousSpotThumbnailResDto> getUserAllDeliciousSpots(Long userId, int currentPage, int pageSize) {
        validUserId(userId);

        List<Long> deliciousSpotIds = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT);
        Set<Long> bookmarkedSet = new HashSet<>(deliciousSpotIds);

        int offset = (currentPage - 1) * pageSize;
        long totalElements = deliciousSpotRepository.countDeliciousSpot();

        List<DeliciousSpotThumbnailResDto> deliciousSpotsDto =  deliciousSpotRepository.findAll(offset, pageSize).stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot,
                        deliciousSpot.getCreator().getName(),
                        bookmarkedSet.contains(deliciousSpot.getId())))
                .toList();

        return CustomPage.of(
                deliciousSpotsDto,
                currentPage,
                pageSize,
                totalElements
        );

    }

    public DeliciousSpotResDto updateDeliciousSpot(Long id, DeliciousSpotReqDto deliciousSpotReqDto, Long userId) {
        isAdmin(userId);

        DeliciousSpot findDeliciousSpot = deliciousSpotRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        Creator findCreator = creatorRepository.findByName(deliciousSpotReqDto.getCreatorName())
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));

        log.info("findCreator = {} ", findCreator.getName());

        if (findDeliciousSpot.getThumbnailImageUrl() != null) {
            s3UploadService.deleteFile(deliciousSpotReqDto.getThumbnailImageUrl());
        }
        if (findDeliciousSpot.getImageUrls() != null) {
            for (String imageUrl : deliciousSpotReqDto.getImageUrls()) {
                s3UploadService.deleteFile(imageUrl);
            }
        }
        log.info("findCreator = {} ", findCreator.getName());

        findDeliciousSpot.updateDeliciousSpot(DeliciousSpotReqDto.toEntity(deliciousSpotReqDto, findCreator));

        return DeliciousSpotResDto.fromEntity(findDeliciousSpot, false);
    }

    public void deleteDeliciousSpot(Long id, Long userId) {
        isAdmin(userId);

        DeliciousSpot deliciousSpot = deliciousSpotRepository.findById(id)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));

        if (deliciousSpot.getThumbnailImageUrl() != null) {
            s3UploadService.deleteFile(deliciousSpot.getThumbnailImageUrl());
        }
        if (deliciousSpot.getImageUrls() != null) {
            for (String imageUrl : deliciousSpot.getImageUrls()) {
                s3UploadService.deleteFile(imageUrl);
            }
        }
        bookmarkRepository.deleteBookmarkByTypeAndTargetId(BookmarkType.DELICIOUS_SPOT, deliciousSpot.getId());
        curationDeliciousSpotRepository.deleteByDeliciousSpotId(deliciousSpot.getId());
        deliciousSpotRepository.deleteDeliciousSpot(id);
    }


    private void isAdmin(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ENTITY_NOT_FOUND));
        if (findUser.getRole() != Role.ROLE_ADMIN) {
            throw new CustomException(FORBIDDEN);
        }
    }

    private void validUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
    }

}
