package com.culturefinder.songdodongnae.curation.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class CurationDeliciousSpotRepository {

    @PersistenceContext
    private final EntityManager em;

    public void deleteByDeliciousSpotId(Long deliciousSpotId) {
        em.createQuery("DELETE FROM CurationDeliciousSpot cf WHERE cf.deliciousSpot.id = :deliciousSpotId")
                .setParameter("deliciousSpotId", deliciousSpotId)
                .executeUpdate();
    }

}
