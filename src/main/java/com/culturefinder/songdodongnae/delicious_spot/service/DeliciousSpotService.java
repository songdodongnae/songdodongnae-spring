package com.culturefinder.songdodongnae.delicious_spot.service;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
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

    public DeliciousSpotResponseDto createDeliciousSpot(DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot deliciousSpot = deliciousSpotReqDto.toEntity();
        DeliciousSpot savedDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        return DeliciousSpotResponseDto.fromEntity(savedDeliciousSpot);
    }

    public DeliciousSpotResponseDto getDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);
        return DeliciousSpotResponseDto.fromEntity(deliciousSpot);
    }

    public DeliciousSpotResponseDto getUserDeliciousSpotById(Long userId, Long id) {
        List<Bookmark> bookmarks = bookmarkRepository.findUserBookmarks(userId);
        boolean isBookmarked = bookmarks.stream()
                .filter(bookmark -> bookmark.getBookmarkType() == BookmarkType.DELICIOUS_SPOT)
                .anyMatch(bookmark -> bookmark.getTargetId().equals(id));
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);
        DeliciousSpotResponseDto deliciousSpotResponseDto = DeliciousSpotResponseDto.fromEntity(deliciousSpot);
        deliciousSpotResponseDto.setBookmarked(isBookmarked);
        return deliciousSpotResponseDto;
    }

    public DeliciousSpotResponseDto updateDeliciousSpot(Long id, DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot updatedDeliciousSpot = deliciousSpotRepository.updateDeliciousSpot(id, deliciousSpotReqDto.toEntity());
        return DeliciousSpotResponseDto.fromEntity(updatedDeliciousSpot);
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

    public List<DeliciousSpotResponseDto> getAllDeliciousSpots() {
        return deliciousSpotRepository.findAll().stream()
                .map(DeliciousSpotResponseDto::fromEntity)
                .toList();
    }

    public List<DeliciousSpotResponseDto> getAllDeliciousSpots(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findUserBookmarks(userId);
        Set<Long> bookmarkedSpotIds = new HashSet<>();
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getBookmarkType() == BookmarkType.DELICIOUS_SPOT) {
                bookmarkedSpotIds.add(bookmark.getTargetId());
            }
        }
        List<DeliciousSpotResponseDto> deliciousSpotResponseDtos = deliciousSpotRepository.findAll().stream()
                .map(DeliciousSpotResponseDto::fromEntity)
                .toList();
        for (DeliciousSpotResponseDto dto : deliciousSpotResponseDtos) {
            dto.setBookmarked(bookmarkedSpotIds.contains(dto.getId()));
        }
        return deliciousSpotResponseDtos;
    }

}
