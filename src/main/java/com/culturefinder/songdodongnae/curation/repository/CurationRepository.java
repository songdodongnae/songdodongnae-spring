package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.bookmark.domain.Bookmark;
import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CurationRepository {

    @PersistenceContext
    private final EntityManager em;

    public Curation findCurationById(Long id){
        Curation curation = em.find(Curation.class, id);
        if(curation == null) throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        return curation;
    }

    public Curation saveCuration(Curation curation) {
        em.persist(curation);
        return curation;
    }

    public Curation deleteById(Long id){
        Curation curation = findCurationById(id);
        em.remove(curation);
        return curation;
    }

    public List<Curation> findAll() {
        return em.createQuery("select c from Curation c", Curation.class).getResultList();
    }

    public List<Curation> findAll(int offset, int pageSize) {
        return em.createQuery("SELECT c FROM Curation c", Curation.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countCuration() {
        return em.createQuery(
                        "SELECT COUNT(c) FROM Curation c",
                        Long.class
                )
                .getSingleResult();
    }
}
