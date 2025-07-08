package com.culturefinder.songdodongnae.mypage;

import com.culturefinder.songdodongnae.bookmark.repository.BookmarkRepository;
import com.culturefinder.songdodongnae.curation.repository.CurationRepository;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.culturefinder.songdodongnae.exception.ErrorCode.RESOURCE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final FestivalRepository festivalRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final CurationRepository curationRepository;
    private final BookmarkRepository bookmarkRepository;


    public String updateNickName(NickNameReqDto nickNameReqDto) {
      //   String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        log.info("memberId = {} ", memberId);
        User findUser = userRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new RuntimeException(String.valueOf(RESOURCE_NOT_FOUND)));

        findUser.updateNickname(nickNameReqDto.getNickName());
        return nickNameReqDto.getNickName();
    }

    public List<ThumbnailResDto> getPostByType(String type) {
        return switch (type) {
            case "FESTIVAL" -> festivalRepository.findAll()
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toList());
            case "DELICIOUS_SPOT" -> deliciousSpotRepository.findAll()
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toList());
            case "CURATION" -> curationRepository.findAll()
                    .stream()
                    .map(ThumbnailResDto::of)
                    .collect(Collectors.toList());
            default -> throw new IllegalArgumentException("지원하지 않는 type입니다: " + type);
        };
    }

}
