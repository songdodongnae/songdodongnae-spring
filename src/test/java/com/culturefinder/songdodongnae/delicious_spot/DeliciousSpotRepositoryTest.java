package com.culturefinder.songdodongnae.delicious_spot;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.exception.CustomException;
import com.culturefinder.songdodongnae.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class DeliciousSpotRepositoryTest {

    @Autowired
    private EntityManager em;

    private DeliciousSpotRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DeliciousSpotRepository(em);
    }

    DeliciousSpot createSpot(String name) {
        DeliciousSpot spot = DeliciousSpot.builder()
                .name(name)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return spot;
    }

    @Test
    @DisplayName("맛집 저장 및 조회")
    void saveAndFindById() {
        DeliciousSpot spot = createSpot("테스트맛집");
        repository.saveDeliciousSpot(spot);

        DeliciousSpot found = repository.findDeliciousSpotById(spot.getId());
        assertThat(found.getName()).isEqualTo("테스트맛집");
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회시 예외 발생")
    void findByIdNotFound() {
        assertThatThrownBy(() -> repository.findDeliciousSpotById(999L))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("최신순 20개 조회")
    void findTopByOrderByCreatedTimeDesc() {
        for (int i = 0; i < 25; i++) {
            repository.saveDeliciousSpot(createSpot("맛집" + i));
        }
        List<DeliciousSpot> list = repository.findTopByOrderByCreatedTimeDesc();
        assertThat(list).hasSize(20);
        assertThat(list.get(0).getCreatedAt()).isAfterOrEqualTo(list.get(19).getCreatedAt());
    }

    @Test
    @DisplayName("전체 조회")
    void findAll() {
        repository.saveDeliciousSpot(createSpot("A"));
        repository.saveDeliciousSpot(createSpot("B"));
        List<DeliciousSpot> all = repository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("맛집 정보 수정")
    void updateDeliciousSpot() {
        DeliciousSpot spot = repository.saveDeliciousSpot(createSpot("수정전"));
        DeliciousSpot update = createSpot("수정후");
        DeliciousSpot updated = repository.updateDeliciousSpot(spot.getId(), update);

        assertThat(updated.getName()).isEqualTo("수정후");
        assertThat(updated.getUpdatedAt()).isAfter(spot.getCreatedAt());
    }

    @Test
    @DisplayName("존재하지 않는 맛집 수정시 예외 발생")
    void updateNotFound() {
        DeliciousSpot update = createSpot("수정");
        assertThatThrownBy(() -> repository.updateDeliciousSpot(999L, update))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("맛집 삭제")
    void deleteDeliciousSpot() {
        DeliciousSpot spot = repository.saveDeliciousSpot(createSpot("삭제대상"));
        repository.deleteDeliciousSpot(spot.getId());

        assertThatThrownBy(() -> repository.findDeliciousSpotById(spot.getId()))
                .isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("존재하지 않는 맛집 삭제시 예외 발생")
    void deleteNotFound() {
        assertThatThrownBy(() -> repository.deleteDeliciousSpot(999L))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }
}