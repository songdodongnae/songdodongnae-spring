package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.dto.CurationReqDto;
import com.culturefinder.songdodongnae.curation.dto.CurationResDto;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CurationResDto createCuration(CurationReqDto curationReqDto) {
        Curation curation = curationReqDto.toEntity();
        Curation savedCuration = curationRepository.saveCuration(curation);
        return CurationResDto.fromEntity(savedCuration);
    }

    public CurationResDto updateCuration(Long id, CurationReqDto curationReqDto){
        Curation curation = curationRepository.findCurationById(id);
        curation.update(curationReqDto.toEntity());
        return CurationResDto.fromEntity(curation);
    }

    public CurationResDto deleteCuration(Long id) {
        Curation curation = curationRepository.deleteById(id);
        return CurationResDto.fromEntity(curation);
    }

    public CustomPage<CurationResDto> getAllCuration(int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Curation> curations = curationRepository.findAll(offset, pageSize);
        List<CurationResDto> curationsDto = curations.stream()
                .map(CurationResDto::fromEntity)
                .toList();
        long totalElements = curationRepository.countCuration();

        return CustomPage.of(
                curationsDto,
                currentPage,
                pageSize,
                totalElements
        );
    }
}
