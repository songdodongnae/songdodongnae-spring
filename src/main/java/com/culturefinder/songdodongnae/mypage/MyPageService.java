package com.culturefinder.songdodongnae.mypage;

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

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final FestivalRepository festivalRepository;
    private final DeliciousSpotRepository deliciousSpotRepository;
    private final CurationRepository curationRepository;


    public String updateNickName(NickNameReqDto nickNameReqDto, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

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
