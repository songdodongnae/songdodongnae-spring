package com.culturefinder.songdodongnae.curation.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class CurationFestivalRepository {

    @PersistenceContext
    private final EntityManager em;

    public void deleteByFestivalId(Long festivalId) {
        em.createQuery("DELETE FROM CurationFestival cf WHERE cf.festival.id = :festivalId")
                .setParameter("festivalId", festivalId)
                .executeUpdate();
    }

}
