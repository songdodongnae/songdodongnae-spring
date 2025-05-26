package com.culturefinder.songdodongnae.delicious_spot.service;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliciousSpotService {

    public final DeliciousSpotRepository deliciousSpotRepository;


    public DeliciousSpotResponseDto createDeliciousSpot(DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot deliciousSpot = deliciousSpotReqDto.toEntity();
        LocalDateTime createdAt = LocalDateTime.now();
        deliciousSpot.setCreatedAt(createdAt);
        deliciousSpot.setUpdatedAt(createdAt);
        DeliciousSpot savedDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        return savedDeliciousSpot.fromEntity();
    }

    public DeliciousSpotResponseDto getDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);

        if (deliciousSpot == null) {
            throw new IllegalArgumentException("해당 맛집이 존재하지 않습니다");
        }

        return deliciousSpot.fromEntity();
    }
}
