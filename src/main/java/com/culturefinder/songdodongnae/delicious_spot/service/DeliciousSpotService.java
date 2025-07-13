package com.culturefinder.songdodongnae.delicious_spot.service;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotReqDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotResponseDto;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotService {

    public final DeliciousSpotRepository deliciousSpotRepository;

    public DeliciousSpotResponseDto createDeliciousSpot(DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot deliciousSpot = deliciousSpotReqDto.toEntity();
        DeliciousSpot savedDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        deliciousSpotRepository.saveImageUrls(savedDeliciousSpot.getId(), deliciousSpotReqDto.getImageUrls());
        return DeliciousSpotResponseDto.fromEntity(savedDeliciousSpot, deliciousSpotReqDto.getImageUrls());
    }

    public DeliciousSpotResponseDto getDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = deliciousSpotRepository.findDeliciousSpotById(id);
        List<String> imageUrls = deliciousSpotRepository.getImageUrlsByDeliciousSpotId(id);
        return DeliciousSpotResponseDto.fromEntity(deliciousSpot, imageUrls);
    }

    public DeliciousSpotResponseDto updateDeliciousSpot(Long id, DeliciousSpotReqDto deliciousSpotReqDto) {
        DeliciousSpot updatedDeliciousSpot = deliciousSpotRepository.updateDeliciousSpot(id, deliciousSpotReqDto.toEntity());
        List<String> imageUrls = deliciousSpotRepository.getImageUrlsByDeliciousSpotId(id);
        return DeliciousSpotResponseDto.fromEntity(updatedDeliciousSpot, imageUrls);
    }

    public void deleteDeliciousSpot(Long id) {
        deliciousSpotRepository.deleteDeliciousSpot(id);
    }

    public List<DeliciousSpotResponseDto> getAllDeliciousSpots() {
        List<DeliciousSpotResponseDto> ret = new ArrayList<>();
        List<DeliciousSpot> deliciousSpots = deliciousSpotRepository.findAll();
        for (DeliciousSpot deliciousSpot : deliciousSpots) {
            List<String> imageUrls = deliciousSpotRepository.getImageUrlsByDeliciousSpotId(deliciousSpot.getId());
            DeliciousSpotResponseDto dto = DeliciousSpotResponseDto.fromEntity(deliciousSpot, imageUrls);
            ret.add(dto);
        }
        return ret;
    }

}
