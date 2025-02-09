package com.culturefinder.songdodongnae.delicious_spot;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

        DeliciousSpotList findDeliciousSpotList = deliciousSpotRepository.saveDeliciousSpotList(deliciousSpotList);
        assertThat(deliciousSpotList.getId()).isEqualTo(findDeliciousSpotList.getId());
    }

    @Test
    @DisplayName("id가 1인 맛집리스트의 맛집을 모두 불러오는지 확인하는 테스트")
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

        deliciousSpotRepository.saveDeliciousSpotList(deliciousSpotList);
        List<DeliciousSpot> findDeliciousSpotList = deliciousSpotRepository
                .findAllDeliciousSpot(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.size()).isEqualTo(3);
        assertThat(findDeliciousSpotList.get(0).getDeliciousSpotList().getId()).isEqualTo(1);
        assertThat(findDeliciousSpotList.get(1).getDeliciousSpotList().getId()).isEqualTo(1);
        assertThat(findDeliciousSpotList.get(2).getDeliciousSpotList().getId()).isEqualTo(1);
    }
}
