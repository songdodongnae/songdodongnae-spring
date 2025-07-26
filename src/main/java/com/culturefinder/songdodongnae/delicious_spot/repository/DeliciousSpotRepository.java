package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotRepository {

    @PersistenceContext
    private final EntityManager em;

    public DeliciousSpot saveDeliciousSpot(DeliciousSpot deliciousSpot) {
        em.persist(deliciousSpot);
        return deliciousSpot;
    }

    public Optional<DeliciousSpot> findById(Long id) {
        DeliciousSpot deliciousSpot = em.find(DeliciousSpot.class, id);
        return Optional.ofNullable(deliciousSpot);
    }


    public List<DeliciousSpot> findAllById(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }

        return em.createQuery(
                        "SELECT d FROM DeliciousSpot d WHERE d.id IN :idList", DeliciousSpot.class)
                .setParameter("idList", idList)
                .getResultList();
    }

    public List<DeliciousSpot> findAll(int offset, int pageSize) {
        return em.createQuery("SELECT d FROM DeliciousSpot d", DeliciousSpot.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public void deleteDeliciousSpot(Long id) {
        DeliciousSpot deliciousSpot = em.find(DeliciousSpot.class, id);
        if (deliciousSpot != null) {
            em.createQuery("DELETE FROM DeliciousSpotImage dsi WHERE dsi.deliciousSpotId = :deliciousSpotId")
                    .setParameter("deliciousSpotId", id)
                    .executeUpdate();
            em.remove(deliciousSpot);
        } else {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    public long countDeliciousSpot() {
        return em.createQuery(
                        "SELECT COUNT(d) FROM DeliciousSpot d",
                        Long.class
                )
                .getSingleResult();
    }

    public List<DeliciousSpot> searchDeliciousSpots(String keyword, int offset, int pageSize) {
        return em.createQuery(
                        "SELECT d FROM DeliciousSpot d WHERE d.title LIKE :keyword ORDER BY d.createdAt DESC",
                        DeliciousSpot.class
                )
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countSearchDeliciousSpot(String keyword) {
        return em.createQuery(
                        "SELECT COUNT(d) FROM DeliciousSpot d WHERE d.title LIKE :keyword",
                        Long.class
                )
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
    }

    public List<DeliciousSpot> findTop3DeliciousSpot(String query) {
        return em.createQuery("SELECT d FROM DeliciousSpot d " +
                                "WHERE Lower(d.title) LIKE :query " +
                                "ORDER BY d.createdAt DESC",
                        DeliciousSpot.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .setMaxResults(3)
                .getResultList();
    }

}
