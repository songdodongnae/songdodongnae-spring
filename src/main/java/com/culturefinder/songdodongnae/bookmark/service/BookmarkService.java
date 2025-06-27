package com.culturefinder.songdodongnae.bookmark.service;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public Bookmark createBookmark(Bookmark bookmark) {
        return bookmarkRepository.createBookmark(bookmark);
    }

    public Bookmark findBookmarkById(Long id) {
        return bookmarkRepository.findBookmarkById(id);
    }

    public Bookmark deleteBookmark(Long id) {
        return bookmarkRepository.deleteBookmark(id);
    }
}
