package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotRepository {

    @PersistenceContext
    private final EntityManager em;
    private final SeriesRepository seriesRepository;

    public DeliciousSpot findDeliciousSpotById(Long id) {
        return em.find(DeliciousSpot.class, id);
    }

    public List<DeliciousSpot> findTopByOrderByCreatedTimeDesc() {
        return em.createQuery("SELECT d FROM DeliciousSpot d ORDER BY d.createdTime DESC", DeliciousSpot.class)
                .setMaxResults(20)
                .getResultList();
    }

    public List<DeliciousSpot> findAll() {
        return em.createQuery("SELECT d FROM DeliciousSpot d", DeliciousSpot.class).getResultList();
    }

    public void addDeliciousSpot(Long id, DeliciousSpot deliciousSpot) {
        Series seriesToAdd = seriesRepository.findSeriesById(id);
        deliciousSpot.setSeries(seriesToAdd);

        em.persist(deliciousSpot);
    }

}
