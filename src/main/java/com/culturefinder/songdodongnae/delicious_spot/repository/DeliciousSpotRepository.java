package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotRepository {

    @PersistenceContext
    private final EntityManager em;

    public DeliciousSpot saveDeliciousSpot(DeliciousSpot deliciousSpot) {
        em.persist(deliciousSpot);
        return deliciousSpot;
    }

    public DeliciousSpot findDeliciousSpotById(Long id) {
        DeliciousSpot deliciousSpot = em.find(DeliciousSpot.class, id);
        if (deliciousSpot == null) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return deliciousSpot;
    }

    public List<DeliciousSpot> findAll() {
        return em.createQuery("SELECT d FROM DeliciousSpot d", DeliciousSpot.class).getResultList();
    }

    public DeliciousSpot updateDeliciousSpot(Long id, DeliciousSpot deliciousSpot) {
        DeliciousSpot existingDeliciousSpot = em.find(DeliciousSpot.class, id);
        if (existingDeliciousSpot != null) {
            existingDeliciousSpot.updateDeliciousSpot(deliciousSpot);
            return existingDeliciousSpot;
        } else {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    public void deleteDeliciousSpot(Long id) {
        DeliciousSpot deliciousSpot = em.find(DeliciousSpot.class, id);
        if (deliciousSpot != null) {
            em.createQuery("DELETE FROM DeliciousSpotImage dsi WHERE dsi.deliciousSpotId = :deliciousSpotId")
                    .setParameter("deliciousSpotId", id)
                    .executeUpdate();
            em.remove(deliciousSpot);
        } else {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    public void saveImageUrls(Long deliciousSpotId, List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            DeliciousSpotImage deliciousSpotImage = DeliciousSpotImage.builder()
                    .deliciousSpotId(deliciousSpotId)
                    .imageUrl(imageUrl)
                    .build();
            em.persist(deliciousSpotImage);
        }
    }

    public List<String> getImageUrlsByDeliciousSpotId(Long deliciousSpotId) {
        return em.createQuery("SELECT dsi.imageUrl FROM DeliciousSpotImage dsi WHERE dsi.deliciousSpotId = :deliciousSpotId", String.class)
                .setParameter("deliciousSpotId", deliciousSpotId)
                .getResultList();
    }
}
