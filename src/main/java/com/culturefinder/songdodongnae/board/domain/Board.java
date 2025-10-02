package com.culturefinder.songdodongnae.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {


    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "board_id")
    Long id;




}
