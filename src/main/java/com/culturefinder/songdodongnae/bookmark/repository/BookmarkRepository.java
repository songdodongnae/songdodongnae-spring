package com.culturefinder.songdodongnae.bookmark.repository;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT b FROM Bookmark b WHERE b.user.id = :userId")
    List<Bookmark> findAllByUser(@Param("userId") Long userId);

    @Query("SELECT b FROM Bookmark b WHERE b.user.id = :userId AND b.bookmarkType = :bookmarkType AND b.targetId = :targetId")
    Optional<Bookmark> findBookmarkByBookmarkTypeAndTargetId(
            @Param("targetId") Long targetId,
            @Param("bookmarkType") BookmarkType bookmarkType,
            @Param("userId") Long userId
    );

    @Query("SELECT b.targetId FROM Bookmark b WHERE b.user.id = :userId AND b.bookmarkType = :bookmarkType ORDER BY b.createdAt DESC")
    List<Long> findTargetIdsByUserAndType(@Param("userId") Long userId, @Param("bookmarkType") BookmarkType bookmarkType);

    @Query("SELECT COUNT(b) > 0 FROM Bookmark b WHERE b.user.id = :userId AND b.bookmarkType = :bookmarkType AND b.targetId = :targetId")
    Boolean existsByUserAndTypeAndTargetId(
            @Param("userId") Long userId,
            @Param("bookmarkType") BookmarkType bookmarkType,
            @Param("targetId") Long targetId
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM Bookmark b WHERE b.user.id = :userId")
    void deleteUserBookmarks(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Bookmark b WHERE b.bookmarkType = :bookmarkType AND b.targetId = :targetId")
    void deleteBookmarkByTypeAndTargetId(@Param("bookmarkType") BookmarkType bookmarkType, @Param("targetId") Long targetId);
}
