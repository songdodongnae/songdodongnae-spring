package com.culturefinder.songdodongnae.user.controller;

import com.culturefinder.songdodongnae.user.dto.CreateAccessTokenReq;
import com.culturefinder.songdodongnae.user.dto.CreateAccessTokenResp;
import com.culturefinder.songdodongnae.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResp> createToken(@RequestBody CreateAccessTokenReq req) {
        String token = tokenService.createAccessToken(req.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResp(token));
    }
}
