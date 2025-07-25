package com.culturefinder.songdodongnae.bookmark.repository;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.utils.CustomPage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Bookmark findBookmarkById(Long id) {
        Bookmark bookmark = em.find(Bookmark.class, id);
        if (bookmark == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return bookmark;
    }

    public Bookmark deleteBookmark(Long id) {
        Bookmark bookmark = em.find(Bookmark.class, id);
        if (bookmark == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        em.remove(bookmark);
        return bookmark;
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

    public Set<Long> findBookmarkedFestivalIdsByUser(User user) {
        String jpql = "SELECT b.targetId FROM Bookmark b " +
                "WHERE b.user = :user " +
                "AND b.bookmarkType = :bookmarkType";

        List<Long> ids = em.createQuery(jpql, Long.class)
                .setParameter("user", user)
                .setParameter("bookmarkType", BookmarkType.FESTIVAL)
                .getResultList();
        return new HashSet<>(ids);
    }


    public void deleteUserBookmarks(Long userId) {
        em.createQuery("DELETE FROM Bookmark b WHERE b.user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

}
