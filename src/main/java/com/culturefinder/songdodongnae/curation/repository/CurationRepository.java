package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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

    public List<Curation> findAllById(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }

        return em.createQuery(
                        "SELECT c FROM Curation c WHERE c.id IN :idList", Curation.class)
                .setParameter("idList", idList)
                .getResultList();
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

    public List<Curation> searchCurations(String keyword, int offset, int pageSize) {
        return em.createQuery(
                        "SELECT c FROM Curation c WHERE c.title LIKE :keyword ORDER BY c.createdAt DESC",
                        Curation.class
                )
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public long countSearchCurations(String keyword) {
        return em.createQuery(
                        "SELECT COUNT(c) FROM Curation c WHERE c.title LIKE :keyword",
                        Long.class
                )
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
    }

}
