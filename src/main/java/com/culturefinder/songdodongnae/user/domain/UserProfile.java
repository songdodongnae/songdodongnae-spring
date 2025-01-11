package com.culturefinder.songdodongnae.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfile {
    private final String oauthId;
    private final String name;
    private final String email;
}
