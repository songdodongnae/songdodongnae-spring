package com.culturefinder.songdodongnae.creator.repository;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {

    Optional<Creator> findByName(String name);

    @Query("SELECT c FROM Creator c WHERE c.id > :cursor ORDER BY c.id ASC")
    List<Creator> findAllByCursor(@Param("cursor") Long cursor, Pageable pageable);
}
