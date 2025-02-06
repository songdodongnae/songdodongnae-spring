package com.culturefinder.songdodongnae.festival.repository;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.domain.FestivalCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public Festival updateFestival(Long id, Festival festival) {
        Festival findFestival = em.find(Festival.class, id);
        findFestival.update(festival);
        em.persist(findFestival);
        return findFestival;
    }

    public void deleteFestival(Long id) {
        Festival findFestival = em.find(Festival.class, id);
        em.remove(findFestival);
    }

    public List<Festival> findAll() {
        return em.createQuery("SELECT f from Festival f", Festival.class).getResultList();
    }

    public List<Festival> findByCategory(FestivalCategory category) {
        return  em.createQuery("SELECT f from Festival f WHERE f.category = :category", Festival.class)
                .setParameter("category", category)
                .getResultList();
    }
}
