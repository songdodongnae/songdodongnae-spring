package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.CurationFestival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CurationFestivalRepository extends JpaRepository<CurationFestival, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CurationFestival cf WHERE cf.festival.id = :festivalId")
    void deleteByFestivalId(@Param("festivalId") Long festivalId);
}
