package com.culturefinder.songdodongnae.delicious_spot.repository;

import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotInputDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotDto;
import com.culturefinder.songdodongnae.admin.dto.AdminDeliciousSpotListDto;
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

    public DeliciousSpotList saveDeliciousSpotList(DeliciousSpotList deliciousSpotList) {
        em.persist(deliciousSpotList);
        return deliciousSpotList;
    }

    public List<AdminDeliciousSpotListDto> findAllDeliciousSpotListNames() {
        return em.createQuery(
                "select d from DeliciousSpotList d", DeliciousSpotList.class
        )
                .getResultStream()
                .map(d -> new AdminDeliciousSpotListDto(d.getId(), d.getTitle()))
                .toList();
    }

    // 테스트 코드 작성 필요
    public List<DeliciousSpotList> findAllDeliciousSpotList() {
        return em
                .createQuery("select d from DeliciousSpotList d", DeliciousSpotList.class)
                .getResultList();
    }

    // 테스트 코드 작성 필요
    public AdminDeliciousSpotListDto findDeliciousSpotListById(Long id) {
        DeliciousSpotList deliciousSpotList = em.find(DeliciousSpotList.class, id);
        return new AdminDeliciousSpotListDto(deliciousSpotList.getId(), deliciousSpotList.getTitle());
    }

    public List<AdminDeliciousSpotDto> findAllDeliciousSpot(Long id) {
        return em.createQuery(
                "select d from DeliciousSpot d where d.deliciousSpotList.id = :id", DeliciousSpot.class
        )
                .setParameter("id", id)
                .getResultStream()
                .map(AdminDeliciousSpotDto::new)
                .toList();
    }

    // 테스트 코드 작성 필요
    public void addDeliciousSpot(Long id, AdminDeliciousSpotInputDto deliciousSpotDto) {
        DeliciousSpot deliciousSpot = new DeliciousSpot(deliciousSpotDto);
        DeliciousSpotList findDeliciousSpot = em.find(DeliciousSpotList.class, id);

        Arrays.stream(deliciousSpotDto.getImageLinks().split(" "))
                        .forEach(s -> {
                            DeliciousSpotImage deliciousSpotImage = new DeliciousSpotImage(s);
                            deliciousSpotImage.setDeliciousSpot(deliciousSpot);
                            deliciousSpot.getDeliciousSpotImages().add(deliciousSpotImage);
                        });

        findDeliciousSpot.addDeliciousSpot(deliciousSpot);
        deliciousSpot.setDeliciousSpotList(findDeliciousSpot);
        em.persist(findDeliciousSpot);
    }
}
