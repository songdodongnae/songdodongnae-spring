package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.CurationDeliciousSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CurationDeliciousSpotRepository extends JpaRepository<CurationDeliciousSpot, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CurationDeliciousSpot cd WHERE cd.deliciousSpot.id = :deliciousSpotId")
    void deleteByDeliciousSpotId(@Param("deliciousSpotId") Long deliciousSpotId);
}
