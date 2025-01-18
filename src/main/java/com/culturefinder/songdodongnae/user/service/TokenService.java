package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.RefreshToken;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.RefreshTokenRepository;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Transactional
@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenService;
    private final UserRepository userRepository;

    public String createAccessToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long userId = refreshTokenService.findByRefreshToken(token)
                .map(RefreshToken::getUserId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 Refresh token 입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 사용자 입니다."));

        return tokenProvider.generateToken(user, Duration.ofHours(3));

    }
}
