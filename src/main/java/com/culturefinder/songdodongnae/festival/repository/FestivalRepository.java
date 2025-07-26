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
import java.util.Optional;

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

    public Optional<Festival> findById(Long id) {
        Festival festival = em.find(Festival.class, id);
        return Optional.ofNullable(festival);
    }

    public List<Festival> findAll(int offset, int limit) {
        return em.createQuery("SELECT f from Festival f ORDER BY f.id", Festival.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
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

    public void deleteFestival(Long id) {
        Festival findFestival = em.find(Festival.class, id);
        em.remove(findFestival);
    }

    public List<Festival> searchFestivals(String keyword, int offset, int pageSize) {
        return em.createQuery(
                        "SELECT f FROM Festival f WHERE f.title LIKE :keyword ORDER BY f.createdAt DESC",
                        Festival.class
                )
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countSearchFestivals(String keyword) {
        return em.createQuery(
                        "SELECT COUNT(f) FROM Festival f WHERE f.title LIKE :keyword",
                        Long.class
                )
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
    }

    public long countFestivals() {
        return em.createQuery(
                        "SELECT COUNT(f) FROM Festival f",
                        Long.class
                )
                .getSingleResult();
    }

}
