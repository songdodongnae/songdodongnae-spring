package com.culturefinder.songdodongnae.festival.service;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.s3.S3UploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final S3UploadService s3UploadService;

    public FestivalResDto createFestival(FestivalReqDto festivalReqDto, MultipartFile mainImage, List<MultipartFile> images) throws IOException {
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

        Festival festival = FestivalReqDto.toEntity(festivalReqDto ,imageUrl, festivalImages);
        Festival savedFestival = festivalRepository.saveFestival(festival);
        return FestivalResDto.fromEntity(savedFestival);
    }

    public List<FestivalResDto> getFestivalsByYearAndMonth(int year, int month) {

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        return festivalRepository.findByYearAndMonth(startOfMonth, endOfMonth)
                .stream()
                .map(FestivalResDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FestivalResDto> getAllFestival(int page, int size) {
        int offset = page * size;
        List<Festival> festivals = festivalRepository.findAll(offset, size);
        return festivals.stream()
                .map(FestivalResDto::fromEntity)
                .collect(Collectors.toList());
    }

    public FestivalResDto getFestival(Long id) {
        Festival festival = festivalRepository.findById(id);
        if (festival == null) {
            throw new IllegalArgumentException("해당 축제가 존재하지 않습니다");
        }
        return FestivalResDto.fromEntity(festival);
    }

    public FestivalResDto deleteFestival(Long id) {
        Festival findFestival = festivalRepository.findById(id);
        if (findFestival == null) {
            throw new IllegalArgumentException("Festival not found");
        }
        festivalRepository.deleteFestival(id);
        return FestivalResDto.fromEntity(findFestival);
    }

    @Transactional
    public FestivalResDto updateFestival(Long id, FestivalReqDto festivalReqDto, MultipartFile mainImage, List<MultipartFile> images) throws IOException {
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
        findFestival.update(FestivalReqDto.toEntity(festivalReqDto, imageUrl, festivalImages));
        festivalRepository.saveFestival(findFestival);
        return FestivalResDto.fromEntity(findFestival);
    }
}
