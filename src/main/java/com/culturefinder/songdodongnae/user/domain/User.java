package com.culturefinder.songdodongnae.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    private Long id;

    private String nickname;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String providerId;

    private String provider;

    public User() {
    }

    public User(String nickname, String email, LocalDateTime createdAt, LocalDateTime updatedAt, Role role, String providerId, String provider) {
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.providerId = providerId;
        this.provider = provider;
    }

    public User update(String nickname, String email){
        this.nickname = nickname;
        this.email = email;
        //updateAt 도 업데이트 해야할지
        return this;
    }
}
