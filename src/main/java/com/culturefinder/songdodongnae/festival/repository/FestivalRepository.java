package com.culturefinder.songdodongnae.festival.repository;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class FestivalRepository {

    @PersistenceContext
    private final EntityManager em;

    public Festival saveFestival(Festival festival) {
        em.persist(festival);
        return festival;
    }

    public Festival findById(Long id) {
        return em.find(Festival.class, id);
    }

    public void deleteFestival(Long id) {
        Festival findFestival = em.find(Festival.class, id);
        em.remove(findFestival);
    }

    public List<Festival> findAll(int offset, int limit) {
        return em.createQuery("SELECT f from Festival f ORDER BY f.id", Festival.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Festival> findAll() {
        return em.createQuery("SELECT f from Festival f ORDER BY f.id", Festival.class)
                .getResultList();
    }

    public List<Festival> findTopByOrderByCreatedTimeDesc() {
        return em.createQuery("SELECT f FROM Festival f ORDER BY f.createdAt DESC", Festival.class)
                .setMaxResults(20)
                .getResultList();
    }

    public List<Festival> findByYearAndMonth(LocalDate start, LocalDate end) {
        return em.createQuery(
                        "SELECT f FROM Festival f WHERE f.startDate <= :end AND f.endDate >= :start", Festival.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<Festival> findAllById(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }

        return em.createQuery(
                        "SELECT f FROM Festival f WHERE f.id IN :idList", Festival.class)
                .setParameter("idList", idList)
                .getResultList();
    }

}
