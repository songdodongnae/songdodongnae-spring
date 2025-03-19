package com.culturefinder.songdodongnae.curation;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CurationService {
    private final FestivalRepository festivalRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;

    public List<CurationThumbnailResDto> getAllCurationThumbnails() {
        List<CurationThumbnailResDto> festivals = festivalRepository.findAll().stream()
                .map(Festival::fromThumbEntity)
                .toList();

        List<CurationThumbnailResDto> deliciousSpots = deliciousSpotRepository.findAll().stream()
                .map(DeliciousSpot::fromThumbEntity)
                .toList();

        List<CurationThumbnailResDto> curation = new ArrayList<>();
        curation.addAll(festivals);
        curation.addAll(deliciousSpots);

        return curation;
    }

    public List<CurationThumbnailResDto> getTopCurationThumbnails() {
        List<CurationThumbnailResDto> festivals = festivalRepository.findTopByOrderByCreatedTimeDesc().stream()
                .map(Festival::fromThumbEntity)
                .toList();

        List<CurationThumbnailResDto> deliciousSpots = deliciousSpotRepository.findTopByOrderByCreatedTimeDesc().stream()
                .map(DeliciousSpot::fromThumbEntity)
                .toList();

        List<CurationThumbnailResDto> curation = new ArrayList<>();
        curation.addAll(festivals);
        curation.addAll(deliciousSpots);

        return curation.stream()
                .sorted(Comparator.comparing(CurationThumbnailResDto::getCreatedTime).reversed())
                .limit(20)
                .collect(Collectors.toList());

    }

}
