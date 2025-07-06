package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class CurationService {

    private CurationRepository curationRepository;

    public CurationResDto getCuration(Long id) {
        Curation curationById = curationRepository.findCurationById(id);
        CurationResDto curationResDto = CurationResDto.fromEntity(curationById);
        return curationResDto;
    }
}
