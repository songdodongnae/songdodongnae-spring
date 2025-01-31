package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalResDto createFestival(FestivalReqDto festivalReqDto) {
        if (festivalReqDto.getName() == null || festivalReqDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Festival festival = festivalRepository.saveFestival(festivalReqDto.toEntity());
        return festival.fromEntity();
    }

    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalReqDto) {
        if (festivalReqDto.getName() == null || festivalReqDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        Festival updatedFestival = festivalRepository.updateFestival(id, festivalReqDto.toEntity());
        return updatedFestival.fromEntity();
    }

    public FestivalResDto deleteFestival(Long id) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        festivalRepository.deleteFestival(id);
        return findFestival.fromEntity();
    }

    public List<FestivalResDto> getAllFestival() {
        return festivalRepository.findAll().stream()
                .map(Festival::fromEntity)
                .collect(Collectors.toList());
    }

    public FestivalResDto getFestival(Long id) {
        return festivalRepository.findById(id).fromEntity();
    }
}
