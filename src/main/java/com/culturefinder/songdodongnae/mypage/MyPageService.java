package com.culturefinder.songdodongnae.mypage;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.culturefinder.songdodongnae.exception.ErrorCode.RESOURCE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final UserRepository userRepository;

    public String updateNickName(NickNameReqDto nickNameReqDto) {
      //   String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        log.info("memberId = {} ", memberId);
        User findUser = userRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> new RuntimeException(String.valueOf(RESOURCE_NOT_FOUND)));

        findUser.updateNickname(nickNameReqDto.getNickName());
        return nickNameReqDto.getNickName();
    }
}
