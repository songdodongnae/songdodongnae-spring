package com.culturefinder.songdodongnae.festival.repository;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.dto.FestivalWithBookmarkDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {

    @Query("SELECT f FROM Festival f JOIN FETCH f.creator WHERE f.id = :id")
    Optional<Festival> findByIdWithCreator(@Param("id") Long id);

    @Query("SELECT f FROM Festival f JOIN FETCH f.creator ORDER BY f.createdAt")
    List<Festival> findAllWithCreator(Pageable pageable);

    @Query("SELECT f, CASE WHEN b.id IS NOT NULL THEN true ELSE false END as isBookmarked " +
           "FROM Festival f " +
           "JOIN FETCH f.creator " +
           "LEFT JOIN Bookmark b ON b.targetId = f.id AND b.bookmarkType = 'FESTIVAL' AND b.user.id = :userId " +
           "ORDER BY f.createdAt")
    List<FestivalWithBookmarkDto> findAllWithCreatorAndBookmarkStatus(Pageable pageable, @Param("userId") Long userId);

    @Query("SELECT f FROM Festival f WHERE f.startDate <= :end AND f.endDate >= :start")
    List<Festival> findByYearAndMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT f FROM Festival f WHERE f.title LIKE %:keyword% ORDER BY f.createdAt DESC")
    List<Festival> searchFestivals(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(f) FROM Festival f WHERE f.title LIKE %:keyword%")
    long countSearchFestivals(@Param("keyword") String keyword);

    @Query("SELECT f FROM Festival f WHERE LOWER(f.title) LIKE %:query% ORDER BY f.createdAt DESC")
    List<Festival> findTop3Festival(@Param("query") String query, Pageable pageable);
}
