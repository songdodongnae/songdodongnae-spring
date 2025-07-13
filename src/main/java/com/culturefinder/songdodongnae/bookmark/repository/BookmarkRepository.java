package com.culturefinder.songdodongnae.bookmark.repository;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.bookmark.domain.BookmarkType;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class BookmarkRepository {

    @PersistenceContext private final EntityManager em;

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

    public List<Bookmark> findUserBookmarks(Long userId) {
        return em.createQuery("SELECT b FROM Bookmark b WHERE b.user.id = :userId", Bookmark.class)
                 .setParameter("userId", userId)
                 .getResultList();
    }

    public boolean existsByUserAndFestival(User user, Long festivalId) {
        String jpql = "SELECT COUNT(b) FROM Bookmark b " +
                "WHERE b.user = :user " +
                "AND b.bookmarkType = :bookmarkType " +
                "AND b.targetId = :festivalId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("user", user)
                .setParameter("bookmarkType", BookmarkType.FESTIVAL)
                .setParameter("festivalId", festivalId)
                .getSingleResult();
        return count > 0;
    }


}
