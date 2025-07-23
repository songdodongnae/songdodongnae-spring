package com.culturefinder.songdodongnae.mypage;

import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final CurationRepository curationRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FestivalRepository festivalRepository;


    public String updateNickName(NickNameReqDto nickNameReqDto, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        findUser.updateNickname(nickNameReqDto.getNickName());
        return nickNameReqDto.getNickName();
    }

    public List<ThumbnailResDto> getPostByType(Long userId, BookmarkType bookmarkType, int currentPage, int pageSize) {
        List<Long> targetIds = bookmarkRepository.findTargetIdsByUserAndType(userId, bookmarkType);

        return switch (bookmarkType) {
            case BookmarkType.FESTIVAL -> festivalRepository.findAllById(targetIds)
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toList());
            case BookmarkType.DELICIOUS_SPOT -> deliciousSpotRepository.findAllById(targetIds)
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toList());
            case BookmarkType.CURATION -> curationRepository.findAllById(targetIds)
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toList());
        };
    }

    @Transactional
    public void deleteUser(Long userId) {
        bookmarkRepository.deleteUserBookmarks(userId);
        userRepository.deleteUser(userId);
    }

}
