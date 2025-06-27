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

    /*
    축제인지 맛집인지 큐레이션인지를 구분하는 타입 필요
    축제, 맛집, 큐레이션이랑 연결되어 있어야 함
     */

}
