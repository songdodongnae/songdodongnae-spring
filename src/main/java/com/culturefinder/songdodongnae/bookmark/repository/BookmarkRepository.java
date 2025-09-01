package com.culturefinder.songdodongnae.bookmark.repository;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.bookmark.dto.BookmarkDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class BookmarkRepository {

    @PersistenceContext
    private final EntityManager em;

    public Bookmark createBookmark(Bookmark bookmark) {
        em.persist(bookmark);
        return bookmark;
    }

    public Optional<Bookmark> findBookmarkById(Long id) {
        Bookmark bookmark = em.find(Bookmark.class, id);
        return Optional.ofNullable(bookmark);
    }

    public void deleteBookmark(Long id) {
        Bookmark bookmark = em.find(Bookmark.class, id);
        em.remove(bookmark);
    }

    public List<Long> findTargetIdsByUserAndType(Long userId, BookmarkType bookmarkType) {
        return em.createQuery("SELECT b.targetId FROM Bookmark b " +
                        "WHERE b.user.id = :userId AND b.bookmarkType = :bookmarkType " +
                        "ORDER BY b.createdAt DESC", Long.class)
                .setParameter("userId", userId)
                .setParameter("bookmarkType", bookmarkType)
                .getResultList();
    }

    public Boolean existsByUserAndTypeAndTargetId(Long userId, BookmarkType bookmarkType, Long targetId) {
        Long count = em.createQuery("SELECT count(b) FROM Bookmark b " +
                        "WHERE b.user.id = :userId AND b.bookmarkType = :bookmarkType " +
                        "AND b.targetId = :targetId", Long.class)
                .setParameter("userId", userId)
                .setParameter("bookmarkType", bookmarkType)
                .setParameter("targetId", targetId)
                .getSingleResult();
        return count > 0;
     
    }

    public void deleteUserBookmarks(Long userId) {
        em.createQuery("DELETE FROM Bookmark b WHERE b.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public void deleteBookmarkByTypeAndTargetId(BookmarkType bookmarkType, Long targetId) {
        em.createQuery("DELETE FROM Bookmark b WHERE b.bookmarkType = :bookmarkType AND b.targetId = :targetId")
                .setParameter("bookmarkType", bookmarkType)
                .setParameter("targetId", targetId);
    }

    public List<BookmarkDto> findAllBookmarksByUser(Long userId) {
        return em.createQuery(
                "SELECT new com.culturefinder.songdodongnae.bookmark.dto.BookmarkDto(b.targetId, b.bookmarkType) " +
                        "FROM Bookmark b " +
                        "WHERE b.user.id = :userId " +
                        "ORDER BY b.createdAt DESC", BookmarkDto.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
