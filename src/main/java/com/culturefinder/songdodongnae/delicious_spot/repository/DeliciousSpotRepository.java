package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
            em.remove(deliciousSpot);
        } else {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
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

}
