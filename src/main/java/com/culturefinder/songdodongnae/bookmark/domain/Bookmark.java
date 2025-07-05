package com.culturefinder.songdodongnae.bookmark.domain;

import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

}
