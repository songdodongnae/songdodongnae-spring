package com.culturefinder.songdodongnae.user.domain;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Table(name = "userProfile")
public class UserProfile {
    private final String oauthId;
    private final String name;
    private final String email;
}
