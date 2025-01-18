package com.culturefinder.songdodongnae.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserProfile userProfile) {
        this.id = Long.valueOf(userProfile.getOauthId());
        this.nickname = userProfile.getName();
        this.email = userProfile.getEmail();
    }

    public User update(String nickname) {
        this.nickname = this.nickname; // 이름만 업데이트
        return this;
    }
}
