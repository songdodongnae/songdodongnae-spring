package com.culturefinder.songdodongnae.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class User {

    @Id @GeneratedValue
    private Long id;

    private String nickname;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {}

    public User(UserProfile userProfile) {
        this.nickname = userProfile.getName();
        this.email = userProfile.getEmail();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.role = Role.ROLE_USER;
    }

}
