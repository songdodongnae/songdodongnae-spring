package com.culturefinder.songdodongnae.delicious_spot;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class DeliciousSpotRepositoryTest {

    @Autowired
    private DeliciousSpotRepository deliciousSpotRepository;

    @Test
    @DisplayName("맛집 저장 및 조회 테스트")
    void test_01() {
        // Given
        DeliciousSpot deliciousSpot = DeliciousSpot.builder()
                .title("맛있는 집")
                .onelineDescription("정말 맛있는 집입니다.")
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        // When
        DeliciousSpot savedSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);

        // Then
        DeliciousSpot foundSpot = deliciousSpotRepository.findDeliciousSpotById(savedSpot.getId());
        Assertions.assertThat(foundSpot).isNotNull();
    }

    @Test
    @DisplayName("맛집 목록 조회 테스트")
    void test_02() {
        // Given
        DeliciousSpot deliciousSpot1 = DeliciousSpot.builder()
                .title("맛집 1")
                .onelineDescription("첫 번째 맛집입니다.")
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        DeliciousSpot deliciousSpot2 = DeliciousSpot.builder()
                .title("맛집 2")
                .onelineDescription("두 번째 맛집입니다.")
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        deliciousSpotRepository.saveDeliciousSpot(deliciousSpot1);
        deliciousSpotRepository.saveDeliciousSpot(deliciousSpot2);

        // When
        List<DeliciousSpot> spots = deliciousSpotRepository.findAll();

        // Then
        Assertions.assertThat(spots).hasSize(2);
    }

    @Test
    @DisplayName("맛집 업데이트 테스트")
    void test_03() {
        // Given
        DeliciousSpot deliciousSpot = DeliciousSpot.builder()
                .title("업데이트 전 맛집")
                .onelineDescription("업데이트 전 설명")
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        DeliciousSpot savedSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);

        // When
        savedSpot.updateDeliciousSpot(DeliciousSpot.builder()
                .title("업데이트 후 맛집")
                .onelineDescription("업데이트 후 설명")
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build());

        DeliciousSpot updatedSpot = deliciousSpotRepository.updateDeliciousSpot(savedSpot.getId(), savedSpot);

        // Then
        Assertions.assertThat(updatedSpot.getTitle()).isEqualTo("업데이트 후 맛집");
    }

    @Test
    @DisplayName("맛집 삭제 테스트")
    void test_04() {
        // Given
        DeliciousSpot deliciousSpot = DeliciousSpot.builder()
                .title("삭제할 맛집")
                .onelineDescription("삭제 전 설명")
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();

        DeliciousSpot savedSpot = deliciousSpotRepository.saveDeliciousSpot(deliciousSpot);

        // When
        deliciousSpotRepository.deleteDeliciousSpot(savedSpot.getId());

        // Then
        Assertions.assertThatThrownBy(() -> deliciousSpotRepository.findDeliciousSpotById(savedSpot.getId()))
                .isInstanceOf(CustomException.class);
    }

}