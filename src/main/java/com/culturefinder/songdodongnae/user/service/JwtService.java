package com.culturefinder.songdodongnae.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";

    private final UserRepository userRepository;

    public String createAccessToken(Long id) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenExpirationPeriod);
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(expirationDate)
                .withClaim(ID_CLAIM, id)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshTokenExpirationPeriod);
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(secretKey));
    }

//    public void sendAccessToken(HttpServletResponse response, String accessToken) {
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setHeader(accessHeader, accessToken);
//        log.info("Access Token = {}", accessToken);
//    }

    public void sendAccessAndRefreshToken(HttpServletResponse response,
                                         String accessToken,
                                         String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith("Bearer"))
                .map(accessToken -> accessToken.replace("Bearer ", ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith("Bearer"))
                .map(refreshToken -> refreshToken.replace("Bearer ", ""));
    }

    public Optional<Long> extractId(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(ID_CLAIM)
                    .asLong());
        } catch (Exception e){
            log.error("액세스 토큰이 유효하지 않습니다");
            return Optional.empty();
        }
    }

    public void updateRefreshToken(Long id, String refreshToken) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        user -> user.setRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e){
            log.error("유효하지 않은 토큰입니다");
            return false;
        }
    }
}
