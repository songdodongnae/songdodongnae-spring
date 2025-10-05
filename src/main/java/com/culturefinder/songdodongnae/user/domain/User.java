package com.culturefinder.songdodongnae.user.domain;

import com.culturefinder.songdodongnae.chat.domain.ChatRoomUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String providerId;

    private String provider;

    private String refreshToken;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChatRoomUser> chatRoomUser;

    public User(UserProfile userProfile) {
        this.nickname = userProfile.getName();
        this.email = userProfile.getEmail();
        this.role = Role.ROLE_USER;
        this.providerId = userProfile.getOauthId();
        this.provider = userProfile.getProvider();
    }

    public User update(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        return this;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
