package com.culturefinder.songdodongnae.creator.service;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    public CreatorResDto createCreator(CreatorReqDto creatorReqDto, Long userId) {
        isAdmin(userId);

        Creator creator = CreatorReqDto.toEntity(creatorReqDto);
        Creator savedCreator = creatorRepository.saveCreator(creator);
        return CreatorResDto.fromEntity(savedCreator);
    }

    public List<CreatorThumbnailResDto> getAllCreator() {
        return creatorRepository.findAll().stream()
                .map(CreatorThumbnailResDto::fromThumbEntity)
                .collect(Collectors.toList());
    }

    public CreatorResDto getCreator(Long id) {
        Creator findCreator = creatorRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        return CreatorResDto.fromEntity(findCreator);
    }

    public CreatorResDto updateCreator(Long id, CreatorReqDto creatorReqDto, Long userId) {
        isAdmin(userId);
        Creator findCreator = creatorRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        if (findCreator.getImageUrl() != null) {
            s3UploadService.deleteFile(findCreator.getImageUrl());
        }

        findCreator.update(CreatorReqDto.toEntity(creatorReqDto));
        return CreatorResDto.fromEntity(findCreator);
    }

    public CreatorResDto deleteCreator(Long id, Long userId) {
        isAdmin(userId);
        Creator findCreator = creatorRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        if (findCreator.getImageUrl() != null) {
            s3UploadService.deleteFile(findCreator.getImageUrl());
        }

        creatorRepository.deleteById(id);
        return CreatorResDto.fromEntity(findCreator);
    }

    private void isAdmin(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        if (user.getRole() != Role.ROLE_ADMIN) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

}
