package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.curation.domain.CurationSortType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CurationRepository {

    @PersistenceContext
    private final EntityManager em;

    public Curation saveCuration(Curation curation) {
        em.persist(curation);
        return curation;
    }

    public Optional<Curation> findById(Long id){
        Curation curation = em.find(Curation.class, id);
        return Optional.ofNullable(curation);
    }

    public List<Curation> findAll(int offset, int pageSize, CurationSortType curationSortType) {
        String query;
        if (curationSortType == CurationSortType.BOOKMARK) {
            query = "SELECT c FROM Curation c " +
                    "ORDER BY (SELECT COUNT(b) FROM Bookmark b WHERE b.targetId = c.id AND b.bookmarkType = 'CURATION') DESC, c.createdAt DESC";
        } else {
            query = "SELECT c FROM Curation c ORDER BY c.createdAt DESC";
        }

        return em.createQuery(query, Curation.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Curation> findAll(int offset, int pageSize) {
        return findAll(offset, pageSize, CurationSortType.CREATED_AT);
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

    public void deleteById(Long id){
        Curation curation = em.find(Curation.class, id);
        em.remove(curation);
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
