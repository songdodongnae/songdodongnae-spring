package com.culturefinder.songdodongnae.search.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepository {

    private final EntityManager em;

    public List<Festival> findTop3Festival(String query) {
        return em.createQuery("SELECT f FROM Festival f " +
                "WHERE Lower(f.title) LIKE :query " +
                "ORDER BY f.createdAt DESC",
                Festival.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .setMaxResults(3)
                .getResultList();
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

    public List<Curation> findTop3Curation(String query) {
        return em.createQuery("SELECT c FROM Curation c " +
                                "WHERE Lower(c.title) LIKE :query " +
                                "ORDER BY c.createdAt DESC",
                        Curation.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .setMaxResults(3)
                .getResultList();
    }

}
