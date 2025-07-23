package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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

    public DeliciousSpot findDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = em.find(DeliciousSpot.class, id);
        if (deliciousSpot == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return deliciousSpot;
    }

    public List<DeliciousSpot> findAll() {
        return em.createQuery("SELECT d FROM DeliciousSpot d", DeliciousSpot.class).getResultList();
    }

    public DeliciousSpot updateDeliciousSpot(Long id, DeliciousSpot deliciousSpot) {
        DeliciousSpot existingDeliciousSpot = em.find(DeliciousSpot.class, id);
        if (existingDeliciousSpot != null) {
            existingDeliciousSpot.updateDeliciousSpot(deliciousSpot);
            return existingDeliciousSpot;
        } else {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
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

}
