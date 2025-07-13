package com.culturefinder.songdodongnae.creator.repository;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
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

    public List<Creator> findAll() {
        return em.createQuery("SELECT f from Creator f", Creator.class).getResultList();
    }

    public Creator findById(Long id) {
        return em.find(Creator.class, id);
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
        Creator findCreator = findById(id);
        if (findCreator != null) em.remove(findCreator);
    }
}
