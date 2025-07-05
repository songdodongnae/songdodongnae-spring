package com.culturefinder.songdodongnae.bookmark.service;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public Bookmark createBookmark(BookmarkReqDto bookmarkReqDto, Long userId) {
        User user = userRepository.findById(userId).get();
        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .bookmarkType(bookmarkReqDto.getBookmarkType())
                .targetId(bookmarkReqDto.getTargetId())
                .build();
        bookmarkRepository.createBookmark(bookmark);
        return bookmark;
    }

    public Bookmark findBookmarkById(Long id) {
        return bookmarkRepository.findBookmarkById(id);
    }

    public Bookmark deleteBookmark(Long id) {
        return bookmarkRepository.deleteBookmark(id);
    }
}
