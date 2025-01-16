package com.culturefinder.songdodongnae.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserProfile {
    private final String oauthId;
    private final String name;
    private final String email;
    private final String provider;

    public User toUser(){
        return new User(
                name,
                email,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Role.ROLE_USER,
                oauthId,
                provider
        );
    }
}
