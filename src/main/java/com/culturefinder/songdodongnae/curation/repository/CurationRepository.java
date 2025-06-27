package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CurationRepository {

    @PersistenceContext
    private final EntityManager em;

    public Curation createCuration(Curation curation) {
        em.persist(curation);
        return curation;
    }

    public Curation findCurationById(Long id) {
        Curation curation = em.find(Curation.class, id);
        if (curation == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return curation;
    }

    public Curation deleteCuration(Long id) {
        Curation curation = em.find(Curation.class, id);
        if (curation == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        em.remove(curation);
        return curation;
    }

    public List<Curation> findAllCurations() {
        return em.createQuery("SELECT c FROM Curation c", Curation.class)
                 .getResultList();
    }

}
