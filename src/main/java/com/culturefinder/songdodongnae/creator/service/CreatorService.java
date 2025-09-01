package com.culturefinder.songdodongnae.creator.service;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.curation.dto.CurationThumbnailResDto;
import com.culturefinder.songdodongnae.delicious_spot.dto.DeliciousSpotThumbnailResDto;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.dto.FestivalThumbnailResDto;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import com.culturefinder.songdodongnae.utils.CursorPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final S3UploadService s3UploadService;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    public CreatorResDto createCreator(CreatorReqDto creatorReqDto, Long userId) {
        isAdmin(userId);

        Creator creator = CreatorReqDto.toEntity(creatorReqDto);
        Creator savedCreator = creatorRepository.saveCreator(creator);
        return CreatorResDto.fromEntity(savedCreator);
    }

    @Transactional(readOnly = true)
    public CursorPage<CreatorThumbnailResDto> getAllCreators(Long cursor, int size) {
        List<CreatorThumbnailResDto> dtos = creatorRepository.findAll(cursor, size)
                                        .stream()
                                        .map(CreatorThumbnailResDto::fromThumbEntity)
                                        .toList();

        boolean hasNext = dtos.size() > size;
        if (hasNext) {
            dtos = dtos.subList(0, size);
        }

        String nextCursor = hasNext && !dtos.isEmpty() ?
            String.valueOf(dtos.get(dtos.size() - 1).getId()) : null;

        return CursorPage.of(dtos, nextCursor, hasNext);
    }

    public CreatorResDto getCreator(Long id) {
        Creator findCreator = creatorRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        List<DeliciousSpotThumbnailResDto> deliciousSpots = findCreator.getDeliciousSpots().stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot, findCreator.getName(), false))
                .toList();

        List<FestivalThumbnailResDto> festivals = findCreator.getFestivals().stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival, findCreator.getName(), false))
                .toList();

        List<CurationThumbnailResDto> curations = findCreator.getCurations().stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, false))
                .toList();

        return CreatorResDto.fromEntity(findCreator, deliciousSpots, festivals, curations);
    }

    public CreatorResDto getCreator(Long id, Long userId) {

        Creator findCreator = creatorRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        List<Long> targetIdsByDeliciousSpot = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.DELICIOUS_SPOT);
        Set<Long> deliciousIds = new HashSet<>(targetIdsByDeliciousSpot);

        List<DeliciousSpotThumbnailResDto> deliciousSpots = findCreator.getDeliciousSpots().stream()
                .map(deliciousSpot -> DeliciousSpotThumbnailResDto.fromEntity(deliciousSpot, findCreator.getName(), deliciousIds.contains(deliciousSpot.getId())))
                .toList();

        List<Long> targetIdsByFestival = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.FESTIVAL);
        Set<Long> festivalIds = new HashSet<>(targetIdsByFestival);

        List<FestivalThumbnailResDto> festivals = findCreator.getFestivals().stream()
                .map(festival -> FestivalThumbnailResDto.fromEntity(festival, findCreator.getName(), festivalIds.contains(festival.getId())))
                .toList();

        List<Long> targetIdsByCuration = bookmarkRepository.findTargetIdsByUserAndType(userId, BookmarkType.CURATION);
        Set<Long> curationIds = new HashSet<>(targetIdsByCuration);

        List<CurationThumbnailResDto> curations = findCreator.getCurations().stream()
                .map(curation -> CurationThumbnailResDto.fromEntity(curation, curationIds.contains(curation.getId())))
                .toList();

        return CreatorResDto.fromEntity(findCreator, deliciousSpots, festivals, curations);
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
