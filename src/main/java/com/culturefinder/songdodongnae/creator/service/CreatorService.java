package com.culturefinder.songdodongnae.creator.service;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public CreatorResDto createCreator(CreatorReqDto creatorReqDto) {
        Creator creator = creatorReqDto.toEntity();
        Creator savedCreator = creatorRepository.saveCreator(creator);
        return CreatorResDto.fromEntity(savedCreator);
    }

    public List<CreatorThumbnailResDto> getAllCreator() {
        return creatorRepository.findAll().stream()
                .map(CreatorThumbnailResDto::fromThumbEntity)
                .collect(Collectors.toList());
    }

    public CreatorResDto getCreator(Long id) {
        return CreatorResDto.fromEntity(creatorRepository.findById(id));
    }

}
