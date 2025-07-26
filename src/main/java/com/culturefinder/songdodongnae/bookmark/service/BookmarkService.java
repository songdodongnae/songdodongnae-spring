package com.culturefinder.songdodongnae.bookmark.service;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkReqDto;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkResDto;
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

    public BookmarkResDto createBookmark(BookmarkReqDto bookmarkReqDto, Long userId) {
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
        return BookmarkResDto.fromEntity(bookmark);
    }

    public BookmarkResDto deleteBookmark(Long id, Long userId) {
        Bookmark bookmarkById = bookmarkRepository.findBookmarkById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        if (!bookmarkById.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        bookmarkRepository.deleteBookmark(id);

        return BookmarkResDto.fromEntity(bookmarkById);
    }

}
