package com.culturefinder.songdodongnae.creator.repository;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Repository
public class CreatorRepository {

    @PersistenceContext
    private final EntityManager em;

    public Creator saveCreator(Creator creator) {
        em.persist(creator);
        return creator;
    }

    public List<Creator> findAll(int offset, int pageSize) {
        return em.createQuery("SELECT f from Creator f", Creator.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Optional<Creator> findById(Long id) {
        Creator creator = em.find(Creator.class, id);
        return Optional.ofNullable(creator);
    }

    public Optional<Creator> findByName(String name) {
        try {
            Creator creator = em.createQuery("SELECT f from Creator f WHERE f.name = :name", Creator.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(creator);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        Creator findCreator = em.find(Creator.class, id);
        if (findCreator != null) em.remove(findCreator);
    }

    public long countCreator() {
        return em.createQuery(
                        "SELECT COUNT(c) FROM Creator c",
                        Long.class
                )
                .getSingleResult();
    }
}
