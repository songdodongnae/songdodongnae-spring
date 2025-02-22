package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotInputDto;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class DeliciousSpotRepository {

    @PersistenceContext
    private final EntityManager em;

    public DeliciousSpotList addDeliciousSpotList(DeliciousSpotList deliciousSpotList) {
        em.persist(deliciousSpotList);
        return deliciousSpotList;
    }

    public List<DeliciousSpotList> findAllDeliciousSpotList() {
        return em
                .createQuery("select d from DeliciousSpotList d", DeliciousSpotList.class)
                .getResultList();
    }

    public DeliciousSpotList findDeliciousSpotListById(Long id) {
        return em.find(DeliciousSpotList.class, id);
    }

    public DeliciousSpotList findAllDeliciousSpotById(Long id) {
        return em.find(DeliciousSpotList.class, id);
    }

    public void addDeliciousSpot(Long id, DeliciousSpot deliciousSpot) {
        DeliciousSpotList deliciousSpotListToAdd = findDeliciousSpotListById(id);
        deliciousSpotListToAdd.addDeliciousSpot(deliciousSpot);
        deliciousSpot.setDeliciousSpotList(deliciousSpotListToAdd);
    }

    public DeliciousSpotList removeDeliciousSpotList(Long id) {
        DeliciousSpotList findDeliciousSpotList = em.find(DeliciousSpotList.class, id);
        em.remove(findDeliciousSpotList);
        return findDeliciousSpotList;
    }
}
