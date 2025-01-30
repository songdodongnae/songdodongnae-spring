package com.culturefinder.songdodongnae.festival;

import com.culturefinder.songdodongnae.festival.controller.FestivalController;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.domain.FestivalCategory;
import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
import com.culturefinder.songdodongnae.festival.domain.FestivalPosterImage;
import com.culturefinder.songdodongnae.festival.dto.FestivalReqDto;
import com.culturefinder.songdodongnae.festival.dto.FestivalResDto;
import com.culturefinder.songdodongnae.festival.dto.ResDto;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import com.culturefinder.songdodongnae.festival.service.FestivalService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class FestivalRepositoryTest {

    @Autowired
    private FestivalRepository festivalRepository;

    @Test
    @DisplayName("축제 저장되는지 확인하는 테스트")
    @Rollback(false)
    public void test1() {
        ArrayList<FestivalPosterImage> festivalPosterImages = new ArrayList<>();
        FestivalPosterImage festivalPosterImage = new FestivalPosterImage("poster1");
        festivalPosterImages.add(festivalPosterImage);

        ArrayList<FestivalImage> festivalImages = new ArrayList<>();
        FestivalImage festivalImage = new FestivalImage("image1");
        festivalImages.add(festivalImage);

        Festival festival = new Festival(null, "축제1", FestivalCategory.FESTIVAL, null, null, null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", festivalPosterImages, festivalImages);
        festivalPosterImage.setFestival(festival);
        festivalImage.setFestival(festival);

        Festival savedFestival = festivalRepository.saveFestival(festival);
        assertThat(festival.getId()).isEqualTo(savedFestival.getId());
    }

    @Test
    @DisplayName("축제 controller create")
    void controllerCreate() {
        FestivalReqDto festivalReqDto = new FestivalReqDto("축제2", FestivalCategory.FESTIVAL,  LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 3),  LocalTime.of(10, 0),
                LocalTime.of(18, 0), null, "서울...", "200원쯤?", "010.22..", "http://,,,,,", "http://,,,,,", "설명", "한줄설명");

        FestivalService festivalService = new FestivalService(festivalRepository);
        FestivalController festivalController = new FestivalController(festivalService);
        ResponseEntity<ResDto> response = festivalController.festivalCreate(festivalReqDto);

        FestivalResDto festival = (FestivalResDto) response.getBody().getResult();
        System.out.println("festival.getId() = " + festival.getId());
        assertThat(festival.getName()).isEqualTo("축제2");
        assertThat(festival.getCategory()).isEqualTo(FestivalCategory.FESTIVAL);
        assertThat(festival.getStartDate()).isEqualTo(LocalDate.of(2025, 5, 1));
        assertThat(festival.getEndDate()).isEqualTo(LocalDate.of(2025, 5, 3));
        assertThat(festival.getStartTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(festival.getEndTime()).isEqualTo(LocalTime.of(18, 0));
        assertThat(festival.getTimeDescription()).isEqualTo(null);
        assertThat(festival.getLocation()).isEqualTo("서울...");
        assertThat(festival.getFee()).isEqualTo("200원쯤?");
        assertThat(festival.getContact()).isEqualTo("010.22..");
        assertThat(festival.getHomePageUrl()).isEqualTo("http://,,,,,");
        assertThat(festival.getReservationUrl()).isEqualTo("http://,,,,,");
        assertThat(festival.getDescription()).isEqualTo("설명");
        assertThat(festival.getOnelineDescription()).isEqualTo("한줄설명");
    }


    @Test
    @DisplayName("축제 controller create 이름 없으면 에러 발생")
    void controllerCreateError() {
        FestivalReqDto festivalReqDto = new FestivalReqDto(null, FestivalCategory.FESTIVAL,  LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 3),  LocalTime.of(10, 0),
                LocalTime.of(18, 0), null, "서울...", "200원쯤?", "010.22..", "http://,,,,,", "http://,,,,,", "설명", "한줄설명");

        FestivalService festivalService = new FestivalService(festivalRepository);
        FestivalController festivalController = new FestivalController(festivalService);
        assertThatThrownBy(() -> festivalController.festivalCreate(festivalReqDto))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
