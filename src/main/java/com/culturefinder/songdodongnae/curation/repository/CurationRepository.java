package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationSortType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {

    @Query("SELECT c FROM Curation c ORDER BY " +
           "(SELECT COUNT(b) FROM Bookmark b WHERE b.targetId = c.id AND b.bookmarkType = 'CURATION') DESC, " +
           "c.createdAt DESC")
    List<Curation> findAllByBookmarkCount(Pageable pageable);

    @Query("SELECT c FROM Curation c ORDER BY c.createdAt DESC")
    List<Curation> findAllByCreatedAt(Pageable pageable);

    @Query("SELECT c FROM Curation c WHERE c.title LIKE %:keyword% ORDER BY c.createdAt DESC")
    List<Curation> searchCurations(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Curation c WHERE c.title LIKE %:keyword%")
    long countSearchCurations(@Param("keyword") String keyword);

    @Query("SELECT c FROM Curation c WHERE LOWER(c.title) LIKE %:query% ORDER BY c.createdAt DESC")
    List<Curation> findTop3Curation(@Param("query") String query, Pageable pageable);
}
