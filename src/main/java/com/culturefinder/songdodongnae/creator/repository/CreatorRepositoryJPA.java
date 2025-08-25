package com.culturefinder.songdodongnae.creator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.culturefinder.songdodongnae.creator.domain.Creator;

public interface CreatorRepositoryJPA extends JpaRepository<Creator, Long> {
    @Query("SELECT c FROM Creator c " +
            "LEFT JOIN FETCH c.deliciousSpots ds " +
            "LEFT JOIN FETCH c.festivals f " +
            "LEFT JOIN FETCH c.curations cur " +
            "WHERE c.id = :id")
    Optional<Creator> findByIdWithDetails(@Param("id") Long id);
}
