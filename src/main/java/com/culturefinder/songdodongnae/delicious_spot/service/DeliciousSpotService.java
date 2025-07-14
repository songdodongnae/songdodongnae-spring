package com.culturefinder.songdodongnae.delicious_spot.service;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotService {

    public final DeliciousSpotRepository deliciousSpotRepository;

    public DeliciousSpotResponseDto createDeliciousSpot(DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot deliciousSpot = deliciousSpotReqDto.toEntity();
        DeliciousSpot savedDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        return DeliciousSpotResponseDto.fromEntity(savedDeliciousSpot);
    }

    public DeliciousSpotResponseDto getDeliciousSpotById(Long id) {
        return DeliciousSpotResponseDto.fromEntity(deliciousSpotRepository.findDeliciousSpotById(id));
    }

    public DeliciousSpotResponseDto updateDeliciousSpot(Long id, DeliciousSpotReqDto deliciousSpotReqDto) {
        return DeliciousSpotResponseDto.fromEntity(deliciousSpotRepository.updateDeliciousSpot(id, deliciousSpotReqDto.toEntity()));
    }

    public void deleteDeliciousSpot(Long id) {
        deliciousSpotRepository.deleteDeliciousSpot(id);
    }

    public List<DeliciousSpotResponseDto> getAllDeliciousSpots() {
        return deliciousSpotRepository.findAll().stream()
                .map(DeliciousSpotResponseDto::fromEntity)
                .collect(Collectors.toList());
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
