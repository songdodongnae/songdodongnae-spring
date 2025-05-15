package com.culturefinder.songdodongnae.curation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Curation {

    @Id
    @GeneratedValue
    @Column(name = "curation_id")
    private Long id;

}
