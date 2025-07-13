package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final S3UploadService s3UploadService;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final CreatorRepository creatorRepository;

    public FestivalResDto createFestival(FestivalReqDto festivalReqDto, MultipartFile mainImage, List<MultipartFile> images, Long userId) throws IOException {
        String imageUrl = null;
        if (mainImage != null) {
            imageUrl = s3UploadService.saveFile(mainImage);
        }

        List<FestivalImage> festivalImages = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String savedImage = s3UploadService.saveFile(image);
                festivalImages.add(new FestivalImage(savedImage));
            }
        }
        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new EntityNotFoundException("Creator not found"));
        Festival festival = FestivalReqDto.toEntity(festivalReqDto ,imageUrl, festivalImages, findCreator);
        Festival savedFestival = festivalRepository.saveFestival(festival);

        User findUser = userRepository.findById(userId)
                        .orElseThrow(()-> new EntityNotFoundException("User not found"));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, savedFestival.getId());
        return FestivalResDto.fromEntity(savedFestival, isBookmarked);
    }

    public List<FestivalResDto> getFestivalsByYearAndMonth(int year, int month, Long userId) {

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Festival> findFestivalsByYearAndMonth = festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        Set<Long> bookmarkedFestivalIds = bookmarkRepository.findBookmarkedFestivalIdsByUser(user);
        return findFestivalsByYearAndMonth.stream()
                .map(festival -> FestivalResDto.fromEntity(
                        festival,
                        bookmarkedFestivalIds.contains(festival.getId())
                ))
                .toList();
    }

    public List<FestivalResDto> getAllFestival(int page, int size, Long userId) {
        int offset = page * size;
        List<Festival> festivals = festivalRepository.findAll(offset, size);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        Set<Long> bookmarkedFestivalIds = bookmarkRepository.findBookmarkedFestivalIdsByUser(user);
        return festivals.stream()
                .map(festival -> FestivalResDto.fromEntity(
                        festival,
                        bookmarkedFestivalIds.contains(festival.getId())
                ))
                .toList();
    }

    public FestivalResDto getFestival(Long id, Long userId) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("해당 축제가 존재하지 않습니다");
        }
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, findFestival.getId());
        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }

    public FestivalResDto deleteFestival(Long id, Long userId) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        festivalRepository.deleteFestival(id);
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, findFestival.getId());
        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }

    @Transactional
    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalReqDto, MultipartFile mainImage, List<MultipartFile> images, Long userId) throws IOException {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        String imageUrl = null;
        if (mainImage != null) {
            imageUrl = s3UploadService.saveFile(mainImage);
        }

        List<FestivalImage> festivalImages = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String savedImage = s3UploadService.saveFile(image);
                festivalImages.add(new FestivalImage(savedImage));
            }
        }
        Creator findCreator = creatorRepository.findByName(festivalReqDto.getCreatorName())
                .orElseThrow(()-> new EntityNotFoundException("Creator not found"));
        findFestival.update(FestivalReqDto.toEntity(festivalReqDto, imageUrl, festivalImages, findCreator));
        festivalRepository.saveFestival(findFestival);
        festivalRepository.deleteFestival(id);
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        boolean isBookmarked = bookmarkRepository.existsByUserAndFestival(findUser, findFestival.getId());
        return FestivalResDto.fromEntity(findFestival, isBookmarked);
    }
}
