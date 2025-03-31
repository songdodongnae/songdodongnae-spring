package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public List<FestivalResDto> getFestivalsByYearAndMonth(int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        return festivalRepository.findAll().stream()
                .filter(festival -> !festival.getEndDate().isBefore(startOfMonth)
                        && !festival.getStartDate().isAfter(endOfMonth))
                .map(Festival::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FestivalResDto> getAllFestival() {
        return festivalRepository.findAll().stream()
                .map(Festival::fromEntity)
                .collect(Collectors.toList());
    }

    public FestivalResDto getFestival(Long id) {
        Festival festival = festivalRepository.findById(id);
        if(festival == null) {
            throw new IllegalArgumentException("해당 축제가 존재하지 않습니다");
        }
        return festival.fromEntity();
    }

}
