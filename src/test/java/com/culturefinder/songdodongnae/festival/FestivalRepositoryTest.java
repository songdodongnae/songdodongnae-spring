package com.culturefinder.songdodongnae.festival;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
import com.culturefinder.songdodongnae.festival.domain.FestivalPosterImage;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import com.culturefinder.songdodongnae.series.domain.Series;
import com.culturefinder.songdodongnae.series.repository.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class FestivalRepositoryTest {

    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private FestivalService festivalService;

    @BeforeEach
    void setUp() {
        List<Festival> festivals = festivalRepository.findAll();
        for (Festival festival : festivals) {
            festivalRepository.deleteFestival(festival.getId());
        }
    }

    @Test
    @DisplayName("축제 저장되는지 확인하는 테스트")
    public void test1() {
        ArrayList<FestivalPosterImage> festivalPosterImages = new ArrayList<>();
        FestivalPosterImage festivalPosterImage = new FestivalPosterImage("poster1");
        festivalPosterImages.add(festivalPosterImage);

        ArrayList<FestivalImage> festivalImages = new ArrayList<>();
        FestivalImage festivalImage = new FestivalImage("image1");
        festivalImages.add(festivalImage);

        Series series = new Series();
        seriesRepository.addSeries(series);

        Festival festival = new Festival(null, "축제1", null, null, null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", festivalPosterImages, festivalImages, series);
        festivalPosterImage.setFestival(festival);
        festivalImage.setFestival(festival);

        Festival savedFestival = festivalRepository.saveFestival(festival);
        assertThat(festival.getId()).isEqualTo(savedFestival.getId());
    }

    @Test
    @DisplayName("년도와 월을 입력하면 제대로 반환하는지 테스트")
    public void test2() {
        Series series = new Series();
        seriesRepository.addSeries(series);

        Festival festival1 = new Festival(null, "축제1", LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival1);
        Festival festival2 = new Festival(null, "축제1", LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival2);
        Festival festival3 = new Festival(null, "축제1", LocalDate.of(2025, 3, 20), LocalDate.of(2025, 4, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival3);

        List<Festival> festivalsByYearAndMonth = festivalService.getFestivalsByYearAndMonth(2025, 3);
        assertThat(festivalsByYearAndMonth.size()).isEqualTo(3);
        assertThat(festivalsByYearAndMonth).contains(festival1);
        assertThat(festivalsByYearAndMonth).contains(festival2);
        assertThat(festivalsByYearAndMonth).contains(festival3);
    }

    @Test
    @DisplayName("모든 축제 제대로 찾아오는지 테스트")
    public void test3() {
        Series series = new Series();
        seriesRepository.addSeries(series);

        Festival festival1 = new Festival(null, "축제1", LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival1);
        Festival festival2 = new Festival(null, "축제1", LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival2);
        Festival festival3 = new Festival(null, "축제1", LocalDate.of(2025, 3, 20), LocalDate.of(2025, 4, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival3);

        List<Festival> allFestival = festivalService.getAllFestival();
        assertThat(allFestival.size()).isEqualTo(3);
        assertThat(allFestival).contains(festival1);
        assertThat(allFestival).contains(festival2);
        assertThat(allFestival).contains(festival3);
    }

    @Test
    @DisplayName("ID로 조회 가능한지 테스트")
    public void test4() {
        Series series = new Series();
        seriesRepository.addSeries(series);

        Festival festival = new Festival(null, "축제1", LocalDate.of(2025, 2, 10), LocalDate.of(2025, 3, 10), null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", null, null, series);
        festivalRepository.saveFestival(festival);

        Festival savedFestival = festivalService.getFestival(festival.getId());
        assertThat(savedFestival.getId()).isEqualTo(festival.getId());
        assertThat(savedFestival.getId()).isNotNull();

    }

}
