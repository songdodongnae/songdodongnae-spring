package com.culturefinder.songdodongnae.bookmark.domain;

import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private BookmarkType bookmarkType;

    private Long targetId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
