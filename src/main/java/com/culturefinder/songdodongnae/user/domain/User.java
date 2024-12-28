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

}
