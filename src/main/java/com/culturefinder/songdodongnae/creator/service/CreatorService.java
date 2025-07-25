package com.culturefinder.songdodongnae.creator.service;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final S3UploadService s3UploadService;

    public CreatorResDto createCreator(CreatorReqDto creatorReqDto) {
        String image = s3UploadService.generatePresignedUrl(creatorReqDto.getImage());
        Creator creator = creatorReqDto.toEntity(image);
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

    public CreatorResDto updateCreator(Long id, CreatorReqDto creatorReqDto) {
        Creator findCreator = creatorRepository.findById(id);
        String image = s3UploadService.generatePresignedUrl(creatorReqDto.getImage());

        findCreator.update(creatorReqDto.toEntity(image));
        return CreatorResDto.fromEntity(findCreator);
    }

    public CreatorResDto deleteCreator(Long id) {
        Creator findCreator = creatorRepository.findById(id);
        s3UploadService.deleteFile(findCreator.getImageUrl());
        creatorRepository.deleteById(id);
        return CreatorResDto.fromEntity(findCreator);
    }
}
