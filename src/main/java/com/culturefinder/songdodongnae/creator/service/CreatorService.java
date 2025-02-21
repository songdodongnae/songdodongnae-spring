package com.culturefinder.songdodongnae.creator.service;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public CreatorResDto createCreator(CreatorReqDto creatorReqDto) {
        Creator creator = creatorReqDto.toEntity();
        log.info("creator = {}", creator);
        Creator savedCreator = creatorRepository.saveCreator(creator);
        log.info("saved creator = {}", savedCreator);
        return savedCreator.fromEntity();
    }
}
