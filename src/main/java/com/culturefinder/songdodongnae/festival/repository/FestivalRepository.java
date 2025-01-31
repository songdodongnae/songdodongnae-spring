package com.culturefinder.songdodongnae.festival.repository;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public Festival updateFestival(Festival festival) {
        Festival findFestival = em.find(Festival.class, festival.getId());
        findFestival.update(festival);
        em.persist(findFestival);
        return festival;
    }
}
