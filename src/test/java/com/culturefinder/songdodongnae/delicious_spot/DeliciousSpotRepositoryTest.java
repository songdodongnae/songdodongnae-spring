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


@SpringBootTest
@Transactional
public class DeliciousSpotRepositoryTest {

    @Autowired private DeliciousSpotRepository deliciousSpotRepository;

    @Test
    @DisplayName("맛집 저장되는지 확인하는 테스트")
    @Rollback(false)
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

        deliciousSpotRepository.saveDeliciousSpotList(deliciousSpotList);
    }
}
