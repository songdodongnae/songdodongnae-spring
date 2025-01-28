package com.culturefinder.songdodongnae.delicious_spot;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@SpringBootTest
@Transactional
public class DeliciousSpotRepositoryTest {

    @Autowired private DeliciousSpotRepository deliciousSpotRepository;

    @Test
    @DisplayName("맛집 저장되는지 확인하는 테스트")
    public void test1() {
        ArrayList<DeliciousSpotImage> deliciousSpotImages = new ArrayList<>();
        DeliciousSpotImage deliciousSpotImage = new DeliciousSpotImage("image url 1");
        deliciousSpotImages.add(deliciousSpotImage);
        DeliciousSpot deliciousSpot = new DeliciousSpot(null, "맛집1", "여기", 10000, (float) 1.1, (float) 5.0, null, null, "계속기다려그냥", "주차못함", "메뉴 암거나 시켜", "설명ㄴ", "한줄", "인스타없어", "0101..", 22 , deliciousSpotImages);
        deliciousSpotImage.setDeliciousSpot(deliciousSpot);
        DeliciousSpot saveDeliciousSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);
        Assertions.assertThat(deliciousSpot.getId()).isEqualTo(saveDeliciousSpot.getId());
    }
}
