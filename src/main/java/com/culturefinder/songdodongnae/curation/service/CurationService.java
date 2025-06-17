package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CurationService {

    private final CurationRepository curationRepository;

    public List<CurationThumbnailResDto> getAllCurationThumbnails() {
        return curationRepository.findAllCurations().stream()
                .map(CurationThumbnailResDto::fromEntity)
                .toList();
    }

    public List<CurationThumbnailResDto> getTopCurationThumbnails() {
        return curationRepository.findAllCurations().stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .limit(20)
                .map(CurationThumbnailResDto::fromEntity)
                .toList();
    }

    public Curation findCurationById(Long id) {
        return curationRepository.findCurationById(id);
    }

    public List<Curation> findAllCurations() {
        return curationRepository.findAllCurations();
    }

    public Curation createCuration(Curation curation) {
        return curationRepository.createCuration(curation);
    }

    public Curation deleteCuration(Long id) {
        return curationRepository.deleteCuration(id);
    }

}
