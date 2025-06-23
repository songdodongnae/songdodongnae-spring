package com.culturefinder.songdodongnae.curation.service;

import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
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
    private final BookmarkRepository bookmarkRepository;

    public List<CurationThumbnailResDto> getAllCurationThumbnails() {
        return curationRepository.findAllCurations().stream()
                .map(CurationThumbnailResDto::fromEntity)
                .sorted((c1, c2) -> c2.getCreatedTime().compareTo(c1.getCreatedTime()))
                .toList();
    }

    public List<CurationThumbnailResDto> getUserCurationThumbnails(Long userId) {
        List<CurationThumbnailResDto> curationThumbnails = curationRepository.findAllCurations().stream()
                .map(CurationThumbnailResDto::fromEntity)
                .sorted((c1, c2) -> c2.getCreatedTime().compareTo(c1.getCreatedTime()))
                .toList();

        Set<Long> bookmarkedCurationIds = new HashSet<>(bookmarkRepository.findUserBookmarks(userId).stream()
                .map(bookmark -> bookmark.getCuration().getId())
                .toList());

        for (CurationThumbnailResDto thumbnail : curationThumbnails) {
            thumbnail.setBookmarked(bookmarkedCurationIds.contains(thumbnail.getId()));
        }

        return curationThumbnails;
    }

    public Curation findCurationById(Long id) {
        return curationRepository.findCurationById(id);
    }

    public Curation createCuration(Curation curation) {
        return curationRepository.createCuration(curation);
    }

    public Curation deleteCuration(Long id) {
        return curationRepository.deleteCuration(id);
    }

}
