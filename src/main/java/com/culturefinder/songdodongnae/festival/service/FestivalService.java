package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;


    public FestivalResDto createFestival(FestivalReqDto festivalReqDto) {
        Festival festival = festivalReqDto.toEntity();
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

    public CustomPage<FestivalResDto> getAllFestival(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<FestivalResDto> festivals = festivalRepository.findAll(offset, pageSize).stream()
                .map(FestivalResDto::fromEntity)
                .toList();
        Long totalElements = festivalRepository.countFestivals();

        return CustomPage.of(
                festivals,
                currentPage,
                pageSize,
                totalElements
        );
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

    @Transactional
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
