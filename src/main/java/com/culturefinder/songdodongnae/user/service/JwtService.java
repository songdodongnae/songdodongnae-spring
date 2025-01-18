package com.culturefinder.songdodongnae.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Getter
@Service
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

    public String createAccessToken(String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenExpirationPeriod);
        return JWT.create()
                .withSubject("AccessToken")
                .withExpiresAt(expirationDate)
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshTokenExpirationPeriod);
        return JWT.create()
                .withSubject("RefreshToken")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(secretKey));
    }
}
