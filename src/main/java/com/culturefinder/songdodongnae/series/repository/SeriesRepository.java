package com.culturefinder.songdodongnae.series.repository;

import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
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

    public Series findSeriesById(Long id) {
        Series series =  em.find(Series.class, id);
        if (series == null) throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        return series;
    }

    public List<Series> findAllSeries() {
        return em.createQuery("SELECT s FROM Series s", Series.class)
                .getResultList();
    }

    public Series addSeries(Series series) {
        List<Series> seriesList = findAllSeries();
        if (seriesList.size() >= 4) throw new CustomException(ErrorCode.SERIES_OVER_MAX);
        em.persist(series);
        return series;
    }

    public Series removeSeries(Long id) {
        Series series = em.find(Series.class, id);
        if (series == null) throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        em.remove(series);
        return series;
    }

}