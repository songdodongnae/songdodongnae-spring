package com.culturefinder.songdodongnae.series.repository;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.series.domain.Series;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
@RequiredArgsConstructor
public class SeriesRepository {

    @PersistenceContext
    private final EntityManager em;

    public Series findSeriesById(Long id){
        return em.find(Series.class, id);
    }

    public List<Series> findAllSeries(){
        return em.createQuery("SELECT s FROM Series s", Series.class)
                .getResultList();
    }
}