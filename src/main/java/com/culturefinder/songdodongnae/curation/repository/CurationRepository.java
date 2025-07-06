package com.culturefinder.songdodongnae.curation.repository;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
