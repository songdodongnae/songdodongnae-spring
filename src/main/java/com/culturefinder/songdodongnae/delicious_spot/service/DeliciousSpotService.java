package com.culturefinder.songdodongnae.delicious_spot.service;

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

    public CustomPage<DeliciousSpotResponseDto> getAllDeliciousSpots(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<DeliciousSpotResponseDto> dtos = deliciousSpotRepository.findAll(offset, pageSize).stream()
                .map(DeliciousSpotResponseDto::fromEntity)
                .toList();
        long totalElements = deliciousSpotRepository.countDeliciousSpot();

        return CustomPage.of(dtos, currentPage, pageSize, totalElements);
    }

}
