package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;


    public FestivalResDto createFestival(FestivalReqDto festivalReqDto) {
        Festival festival = festivalReqDto.toEntity();
        LocalDateTime createdAt = LocalDateTime.now();
        festival.setCreatedAt(createdAt);
        festival.setUpdatedAt(createdAt);
        Festival savedFestival = festivalRepository.saveFestival(festival);
        return FestivalResDto.fromEntity(savedFestival);
    }

    public List<FestivalResDto> getFestivalsByYearAndMonth(int year, int month) {

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        return festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth)
                .stream()
                .map(FestivalResDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FestivalResDto> getAllFestival(int page, int size) {
        int offset = page * size;
        List<Festival> festivals = festivalRepository.findAll(offset, size);
        return festivals.stream()
                .map(FestivalResDto::fromEntity)
                .collect(Collectors.toList());
    }

    public FestivalResDto getFestival(Long id) {
        Festival festival = festivalRepository.findById(id);
        if (festival == null) {
            throw new IllegalArgumentException("해당 축제가 존재하지 않습니다");
        }
        return FestivalResDto.fromEntity(festival);
    }

    public FestivalResDto deleteFestival(Long id) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        festivalRepository.deleteFestival(id);
        return FestivalResDto.fromEntity(findFestival);
    }

    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalUpdateReqDto) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        findFestival.update(festivalUpdateReqDto.toEntity());
        festivalRepository.saveFestival(findFestival);
        return FestivalResDto.fromEntity(findFestival);
    }
}
