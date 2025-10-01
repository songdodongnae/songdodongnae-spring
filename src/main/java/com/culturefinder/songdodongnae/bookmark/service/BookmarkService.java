package com.culturefinder.songdodongnae.bookmark.service;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.common.exception.CustomException;
import com.culturefinder.songdodongnae.common.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BookmarkResDto createBookmark(BookmarkReqDto bookmarkReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        if (bookmarkReqDto.getTargetId() == null) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }
        if (bookmarkRepository.existsByUserAndTypeAndTargetId(userId, bookmarkReqDto.getBookmarkType(), bookmarkReqDto.getTargetId())) {
            throw new CustomException(ErrorCode.DUPLICATE_BOOKMARK);
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .bookmarkType(bookmarkReqDto.getBookmarkType())
                .targetId(bookmarkReqDto.getTargetId())
                .build();
        bookmarkRepository.createBookmark(bookmark);
        return BookmarkResDto.fromEntity(bookmark);
    }

    public BookmarkResDto deleteBookmark(Long targetId, BookmarkType bookmarkType, Long userId) {
        Bookmark bookmarkById = bookmarkRepository.findBookmarkByBookmarkTypeAndTargetId(targetId, bookmarkType, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        if (!bookmarkById.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        bookmarkRepository.deleteBookmark(bookmarkById.getId());
        return BookmarkResDto.fromEntity(bookmarkById);
    }

}
