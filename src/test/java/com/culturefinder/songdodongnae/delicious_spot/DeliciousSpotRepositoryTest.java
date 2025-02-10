package com.culturefinder.songdodongnae.delicious_spot;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
public class DeliciousSpotRepositoryTest {

    @Autowired private DeliciousSpotRepository deliciousSpotRepository;

    @Test
    @DisplayName("맛집 리스트 저장되는지 확인하는 테스트")
    public void test1() {
        DeliciousSpotImage deliciousSpotImage1 = new DeliciousSpotImage("imageUrl1");
        DeliciousSpotImage deliciousSpotImage2 = new DeliciousSpotImage("imageUrl2");
        DeliciousSpot deliciousSpot = new DeliciousSpot();
        List<DeliciousSpotImage> deliciousSpotImageArrayList = new ArrayList<>();
        deliciousSpotImageArrayList.add(deliciousSpotImage1);
        deliciousSpotImageArrayList.add(deliciousSpotImage2);
        deliciousSpot.setDeliciousSpotImages(deliciousSpotImageArrayList);
        deliciousSpotImage1.setDeliciousSpot(deliciousSpot);
        deliciousSpotImage2.setDeliciousSpot(deliciousSpot);

        List<DeliciousSpot> deliciousSpotArrayList = new ArrayList<>();
        deliciousSpotArrayList.add(deliciousSpot);
        DeliciousSpotList deliciousSpotList = new DeliciousSpotList();
        deliciousSpotList.setDeliciousSpots(deliciousSpotArrayList);
        deliciousSpotList.setTitle("맛집리스트1");
        deliciousSpot.setDeliciousSpotList(deliciousSpotList);

        DeliciousSpotList findDeliciousSpotList = deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);
        assertThat(deliciousSpotList.getId()).isEqualTo(findDeliciousSpotList.getId());
    }

    @Test
    @DisplayName("맛집리스트의 맛집을 모두 불러오는지 확인하는 테스트")
    public void test2() {
        DeliciousSpot deliciousSpot1 = new DeliciousSpot();
        DeliciousSpot deliciousSpot2 = new DeliciousSpot();
        DeliciousSpot deliciousSpot3 = new DeliciousSpot();
        List<DeliciousSpot> deliciousSpots = new ArrayList<>();
        deliciousSpots.add(deliciousSpot1);
        deliciousSpots.add(deliciousSpot2);
        deliciousSpots.add(deliciousSpot3);

        DeliciousSpotList deliciousSpotList = new DeliciousSpotList();
        deliciousSpot1.setDeliciousSpotList(deliciousSpotList);
        deliciousSpot2.setDeliciousSpotList(deliciousSpotList);
        deliciousSpot3.setDeliciousSpotList(deliciousSpotList);
        deliciousSpotList.setDeliciousSpots(deliciousSpots);
        deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);

        List<DeliciousSpot> findDeliciousSpotList = deliciousSpotRepository
                .findAllDeliciousSpotById(deliciousSpotList.getId()).getDeliciousSpots();

        assertThat(findDeliciousSpotList.size()).isEqualTo(3);
        assertThat(findDeliciousSpotList.get(0).getDeliciousSpotList().getId()).isEqualTo(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.get(1).getDeliciousSpotList().getId()).isEqualTo(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.get(2).getDeliciousSpotList().getId()).isEqualTo(deliciousSpotList.getId());
    }

    @Test
    @DisplayName("맛집 리스트를 불러오는지 확인하는 테스트")
    public void test3() {
        DeliciousSpotList deliciousSpotList = new DeliciousSpotList();
        deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);
        DeliciousSpotList findDeliciousSpotList = deliciousSpotRepository.findDeliciousSpotListById(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.getId()).isEqualTo(deliciousSpotList.getId());
    }

    @Test
    @DisplayName("맛집 리스트를 모두 불러오는지 확인하는 테스트")
    public void test4 () {
        List<DeliciousSpotList> findDeliciousSpotList1 = deliciousSpotRepository.findAllDeliciousSpotList();
        assertThat(findDeliciousSpotList1.size()).isEqualTo(0);

        DeliciousSpotList deliciousSpotList = new DeliciousSpotList();
        deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);
        List<DeliciousSpotList> findDeliciousSpotList2 = deliciousSpotRepository.findAllDeliciousSpotList();
        assertThat(findDeliciousSpotList2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 맛집 리스트에 맛집이 등록되는지 확인하는 테스트")
    public void test5() {
        DeliciousSpotList deliciousSpotList = new DeliciousSpotList();
        deliciousSpotRepository.addDeliciousSpotList(deliciousSpotList);
        Long deliciousSpotListId = deliciousSpotList.getId();
        deliciousSpotRepository.addDeliciousSpot(deliciousSpotListId, new DeliciousSpot());

        DeliciousSpotList findDeliciousSpotList = deliciousSpotRepository.findDeliciousSpotListById(deliciousSpotListId);
        assertThat(findDeliciousSpotList).isNotNull();
        assertThat(findDeliciousSpotList.getId()).isEqualTo(deliciousSpotListId);
        assertThat(findDeliciousSpotList.getDeliciousSpots().size()).isEqualTo(1);
    }
}
