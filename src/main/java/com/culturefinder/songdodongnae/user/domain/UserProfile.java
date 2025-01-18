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
}
