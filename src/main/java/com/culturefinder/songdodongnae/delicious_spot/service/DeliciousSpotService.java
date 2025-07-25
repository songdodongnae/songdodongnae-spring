package com.culturefinder.songdodongnae.delicious_spot.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotService {

    private final DeliciousSpotRepository deliciousSpotRepository;
    private final BookmarkRepository bookmarkRepository;
    private final S3UploadService s3UploadService;

    public DeliciousSpotResDto createDeliciousSpot(DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot deliciousSpot = deliciousSpotReqDto.toEntity();
        DeliciousSpot savedDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        return DeliciousSpotResDto.fromEntity(savedDeliciousSpot);
    }

    public DeliciousSpotResDto getDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);
        return DeliciousSpotResDto.fromEntity(deliciousSpot);
    }

    public DeliciousSpotResDto getUserDeliciousSpot(Long userId, Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);
        boolean isBookmarked = bookmarkRepository.existsByUserAndTypeAndTargetId(userId, BookmarkType.DELICIOUS_SPOT, id);
        return DeliciousSpotResDto.fromEntity(deliciousSpot, isBookmarked);
    }

    public DeliciousSpotResDto updateDeliciousSpot(Long id, DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot updatedDeliciousSpot = deliciousSpotRepository.updateDeliciousSpot(id, deliciousSpotReqDto.toEntity());
        return DeliciousSpotResDto.fromEntity(updatedDeliciousSpot);
    }

    public void deleteDeliciousSpot(Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);
        if (deliciousSpot.getThumbnailImageUrl() != null) {
            s3UploadService.deleteFile(deliciousSpot.getThumbnailImageUrl());
        }
        if (deliciousSpot.getImageUrls() != null) {
            for (String imageUrl : deliciousSpot.getImageUrls()) {
                s3UploadService.deleteFile(imageUrl);
            }
        }
        deliciousSpotRepository.deleteDeliciousSpot(id);
    }

    public List<DeliciousSpotResDto> getAllDeliciousSpots() {
        return deliciousSpotRepository.findAll().stream()
                .map(DeliciousSpotResDto::fromEntity)
                .toList();
    }

    public CustomPage<DeliciousSpotResDto> getAllDeliciousSpots(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<DeliciousSpotResDto> dtos = deliciousSpotRepository.findAll(offset, pageSize).stream()
                .map(DeliciousSpotResDto::fromEntity)
                .toList();
        long totalElements = deliciousSpotRepository.countDeliciousSpot();

        return CustomPage.of(dtos, currentPage, pageSize, totalElements);
    }

    public CustomPage<DeliciousSpotResDto> getAllDeliciousSpots(Long userId, int currentPage, int pageSize) {
        List<Long> deliciousSpotIds = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT);
        Set<Long> bookmarkedSet = new HashSet<>(deliciousSpotIds);

        int offset = (currentPage - 1) * pageSize;

        List<DeliciousSpot> deliciousSpots = deliciousSpotRepository.findAll(offset, pageSize);
        List<DeliciousSpotResDto> deliciousSpotsDto = deliciousSpots.stream()
                .map(deliciousSpot -> DeliciousSpotResDto.fromEntity(deliciousSpot, bookmarkedSet.contains(deliciousSpot.getId())))
                .toList();
        long totalElements = deliciousSpotRepository.countDeliciousSpot();

        return CustomPage.of(
                deliciousSpotsDto,
                currentPage,
                pageSize,
                totalElements
        );

    }

}
