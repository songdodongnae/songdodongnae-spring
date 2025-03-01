package com.culturefinder.songdodongnae.delicious_spot;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotImage;
import com.culturefinder.songdodongnae.delicious_spot.repository.DeliciousSpotRepository;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    @PersistenceContext private EntityManager em;
    @Autowired
    private SeriesRepository seriesRepository;

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
        Series deliciousSpotList = new Series();
        deliciousSpotList.setDeliciousSpots(deliciousSpotArrayList);
        deliciousSpot.setSeries(deliciousSpotList);

        Series findDeliciousSpotList = seriesRepository.addSeries(deliciousSpotList);
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

        Series deliciousSpotList = new Series();
        deliciousSpot1.setSeries(deliciousSpotList);
        deliciousSpot2.setSeries(deliciousSpotList);
        deliciousSpot3.setSeries(deliciousSpotList);
        deliciousSpotList.setDeliciousSpots(deliciousSpots);
        seriesRepository.addSeries(deliciousSpotList);

        List<DeliciousSpot> findDeliciousSpotList = seriesRepository
                .findSeriesById(deliciousSpotList.getId()).getDeliciousSpots();

        assertThat(findDeliciousSpotList.size()).isEqualTo(3);
        assertThat(findDeliciousSpotList.get(0).getSeries().getId()).isEqualTo(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.get(1).getSeries().getId()).isEqualTo(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.get(2).getSeries().getId()).isEqualTo(deliciousSpotList.getId());
    }

    @Test
    @DisplayName("맛집 리스트를 불러오는지 확인하는 테스트")
    public void test3() {
        Series deliciousSpotList = new Series();
        seriesRepository.addSeries(deliciousSpotList);
        Series findDeliciousSpotList = seriesRepository.findSeriesById(deliciousSpotList.getId());
        assertThat(findDeliciousSpotList.getId()).isEqualTo(deliciousSpotList.getId());
    }

    @Test
    @DisplayName("맛집 리스트를 모두 불러오는지 확인하는 테스트")
    public void test4 () {
        List<Series> findDeliciousSpotList1 = seriesRepository.findAllSeries();
        assertThat(findDeliciousSpotList1.size()).isEqualTo(0);

        Series deliciousSpotList = new Series();
        seriesRepository.addSeries(deliciousSpotList);
        List<Series> findDeliciousSpotList2 = seriesRepository.findAllSeries();
        assertThat(findDeliciousSpotList2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 맛집 리스트에 맛집이 등록되는지 확인하는 테스트")
    public void test5() {
        Series deliciousSpotList = new Series();
        seriesRepository.addSeries(deliciousSpotList);
        Long deliciousSpotListId = deliciousSpotList.getId();
        deliciousSpotRepository.addDeliciousSpot(deliciousSpotListId, new DeliciousSpot());

        Series findDeliciousSpotList = seriesRepository.findSeriesById(deliciousSpotListId);
        assertThat(findDeliciousSpotList).isNotNull();
        assertThat(findDeliciousSpotList.getId()).isEqualTo(deliciousSpotListId);
        assertThat(findDeliciousSpotList.getDeliciousSpots().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 맛집 리스트가 지워지는지 확인하는 테스트")
    public void test6() {
        Series deliciousSpotList = new Series();
        seriesRepository.addSeries(deliciousSpotList);
        em.flush();
        Long deliciousSpotListId = deliciousSpotList.getId();

        Series removeDeliciousSpotList = seriesRepository.removeSeries(deliciousSpotListId);
        assertThat(removeDeliciousSpotList.getId()).isEqualTo(deliciousSpotListId);
    }

    @Test
    @DisplayName("특정 맛집 리스트가 지워질때 연관된 데이터가 모두 지워지는지 확인하는 테스트")
    public void test7() {
        Series deliciousSpotList = new Series();
        DeliciousSpot deliciousSpot = new DeliciousSpot();
        DeliciousSpotImage deliciousSpotImage = new DeliciousSpotImage();
        deliciousSpotImage.setDeliciousSpot(deliciousSpot);
        deliciousSpot.setDeliciousSpotImages(List.of(deliciousSpotImage));
        deliciousSpot.setSeries(deliciousSpotList);
        deliciousSpotList.setDeliciousSpots(List.of(deliciousSpot));
        Series AddDeliciousSpotList = seriesRepository.addSeries(deliciousSpotList);
        em.flush();

        Long id = AddDeliciousSpotList.getId();
        seriesRepository.removeSeries(id);
        em.flush();

        List<Series> listlist = em.createQuery("select d from Series d").getResultList();
        List<DeliciousSpot> list = em.createQuery("select d from DeliciousSpot d").getResultList();
        List<DeliciousSpotImage> image = em.createQuery("select d from DeliciousSpotImage d").getResultList();

        assertThat(listlist).isEmpty();
        assertThat(list).isEmpty();
        assertThat(image).isEmpty();
    }
}
