package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliciousSpotRepository extends JpaRepository<DeliciousSpot, Long> {

    @Query("SELECT d FROM DeliciousSpot d JOIN FETCH d.creator WHERE d.id = :id")
    Optional<DeliciousSpot> findByIdWithCreator(@Param("id") Long id);

    @Query("SELECT d FROM DeliciousSpot d WHERE d.title LIKE %:keyword% ORDER BY d.createdAt DESC")
    List<DeliciousSpot> searchDeliciousSpots(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(d) FROM DeliciousSpot d WHERE d.title LIKE %:keyword%")
    long countSearchDeliciousSpot(@Param("keyword") String keyword);

    @Query("SELECT d FROM DeliciousSpot d WHERE LOWER(d.title) LIKE %:query% ORDER BY d.createdAt DESC")
    List<DeliciousSpot> findTop3DeliciousSpot(@Param("query") String query, Pageable pageable);
}
