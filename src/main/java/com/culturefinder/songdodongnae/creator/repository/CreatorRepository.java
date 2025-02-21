package com.culturefinder.songdodongnae.creator.repository;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
