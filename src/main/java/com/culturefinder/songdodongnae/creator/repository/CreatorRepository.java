package com.culturefinder.songdodongnae.creator.repository;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto;
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

    public List<Creator> findAll(int offset, int pageSize) {
        return em.createQuery("SELECT c from Creator c", Creator.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<CreatorThumbnailResDto> findAllV2(int offset, int pageSize) {
        String jpql = """
        select new com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto(
            c.id, c.name, c.introduction, c.imageUrl
        )
        from Creator c
        order by c.id asc
        """;

        return em.createQuery(jpql, CreatorThumbnailResDto.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .setHint("org.hibernate.fetchSize", pageSize)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }


    public Optional<Creator> findById(Long id) {
        Creator creator = em.find(Creator.class, id);
        return Optional.ofNullable(creator);
    }

    public Optional<Creator> findByName(String name) {
        try {
            Creator creator = em.createQuery("SELECT c from Creator c WHERE c.name = :name", Creator.class)
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
                .setHint("org.hibernate.cacheable", true)
                .getSingleResult();
    }

    public List<CreatorThumbnailResDto> findAllByCursor(Long cursor, int size) {
        String jpql = """
        select new com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto(
            c.id, c.name, c.introduction, c.imageUrl
        )
        from Creator c
        where c.id > :cursor
        order by c.id asc
        """;
        return em.createQuery(jpql, CreatorThumbnailResDto.class)
                .setParameter("cursor", cursor != null ? cursor : 0L)
                .setMaxResults(size + 1)
                .setHint("org.hibernate.fetchSize", size)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }

    public List<CreatorThumbnailResDto> findFirstPage(int size) {
        String jpql = """
        select new com.culturefinder.songdodongnae.creator.dto.CreatorThumbnailResDto(
            c.id, c.name, c.introduction, c.imageUrl
        )
        from Creator c
        order by c.id asc
        """;
        return em.createQuery(jpql, CreatorThumbnailResDto.class)
                .setMaxResults(size + 1)
                .setHint("org.hibernate.fetchSize", size)
                .setHint("org.hibernate.readOnly", true)
                .getResultList();
    }
}
