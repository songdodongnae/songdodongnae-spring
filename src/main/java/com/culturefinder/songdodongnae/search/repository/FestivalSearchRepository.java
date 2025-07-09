package com.culturefinder.songdodongnae.search.repository;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalSearchRepository extends JpaRepository<Festival, Long> {

    Page<Festival> findByTitleContainingIgnoreCaseOrderByCreatedAt(String query, Pageable pageable);
}
