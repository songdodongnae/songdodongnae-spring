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
import com.culturefinder.songdodongnae.utils.CustomPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

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

    public CustomPage<ThumbnailResDto> getPostByType(Long userId, BookmarkType bookmarkType, int currentPage, int pageSize) {
        List<Long> targetIds = bookmarkRepository.findTargetIdsByUserAndType(userId, bookmarkType);
        int totalElements = targetIds.size();
        int offset = (currentPage - 1) * pageSize;
        int end = Math.min(offset + pageSize ,totalElements);

        if (offset >= totalElements) {
            return CustomPage.of(Collections.emptyList(), currentPage,pageSize, totalElements);
        }

        List<Long> pageIds = targetIds.subList(offset, end);

        Map<Long, ThumbnailResDto> dtoMap = switch (bookmarkType) {
            case BookmarkType.FESTIVAL -> festivalRepository.findAllById(pageIds)
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toMap(ThumbnailResDto::getId, dto -> dto));
            case BookmarkType.DELICIOUS_SPOT -> deliciousSpotRepository.findAllById(pageIds)
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toMap(ThumbnailResDto::getId, dto -> dto));

            case BookmarkType.CURATION -> curationRepository.findAllById(pageIds)
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toMap(ThumbnailResDto::getId, dto -> dto));
        };

        List<ThumbnailResDto> response = pageIds.stream()
                .map(dtoMap::get)
                .toList();

        return CustomPage.of(response, currentPage, pageSize, totalElements);
    }

    @Transactional
    public void deleteUser(Long userId) {
        bookmarkRepository.deleteUserBookmarks(userId);
        userRepository.deleteUser(userId);
    }

}
