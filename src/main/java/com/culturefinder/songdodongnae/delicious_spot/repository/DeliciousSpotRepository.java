package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotRepository {

    @PersistenceContext
    private final EntityManager em;

    public DeliciousSpotList saveDeliciousSpotList(DeliciousSpotList deliciousSpotList) {
        em.persist(deliciousSpotList);
        return deliciousSpotList;
    }

}
