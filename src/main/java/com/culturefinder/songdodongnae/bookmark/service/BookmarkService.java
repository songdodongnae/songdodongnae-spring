package com.culturefinder.songdodongnae.bookmark.service;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import com.culturefinder.songdodongnae.utils.CustomPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public Bookmark createBookmark(BookmarkReqDto bookmarkReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        if (bookmarkReqDto.getTargetId() == null) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

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

    public CustomPage<BookmarkResDto> findUserBookmarks(Long userId, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;

        List<Bookmark> bookmarks = bookmarkRepository.findUserBookmarks(userId, offset, pageSize);
        long totalElements = bookmarkRepository.countUserBookmarks(userId);

        List<BookmarkResDto> dtos = bookmarks.stream()
                .map(BookmarkResDto::fromEntity)
                .toList();

        return CustomPage.of(
                dtos,
                currentPage,
                pageSize,
                totalElements
        );
    }
}
