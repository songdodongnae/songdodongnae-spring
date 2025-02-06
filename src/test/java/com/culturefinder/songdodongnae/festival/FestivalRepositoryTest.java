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
/*import com.culturefinder.songdodongnae.festival.service.FestivalImageService;*/
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
    /*@Autowired
    private FestivalImageService festivalImageService;*/

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

        FestivalService festivalService = new FestivalService(festivalRepository/*, festivalImageService*/);
        FestivalController festivalController = new FestivalController(festivalService);
        ResponseEntity<ResDto> response = festivalController.festivalCreate(festivalReqDto/*, null, null*/);

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

        FestivalService festivalService = new FestivalService(festivalRepository/*, festivalImageService*/);
        FestivalController festivalController = new FestivalController(festivalService);
        assertThatThrownBy(() -> festivalController.festivalCreate(festivalReqDto /*, null, null*/))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("축제 controller update")
    public void controllerUpdate() {

        Festival savedFestival = festivalRepository.saveFestival(new Festival(
                null, "축제1", FestivalCategory.FESTIVAL, null, null, null, null, null,
                "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명",
                "한줄설명", new ArrayList<>(), new ArrayList<>()
        ));

        Festival updatedFestival = new Festival(
                savedFestival.getId(), "업데이트된 축제", FestivalCategory.FESTIVAL,
                savedFestival.getStartDate(), savedFestival.getEndDate(), savedFestival.getStartTime(),
                savedFestival.getEndTime(), savedFestival.getTimeDescription(), "서울 2",
                "1000원", "010.33.44.44", "http://22,,,", "http://,,,",
                "업데이트된 설명", "업데이트된 한줄설명", new ArrayList<>(), new ArrayList<>()
        );

        Festival updatedSavedFestival = festivalRepository.updateFestival(savedFestival.getId(),updatedFestival);

        assertThat(updatedSavedFestival.getName()).isEqualTo("업데이트된 축제");
        assertThat(updatedSavedFestival.getLocation()).isEqualTo("서울 2");
        assertThat(updatedSavedFestival.getFee()).isEqualTo("1000원");
        assertThat(updatedSavedFestival.getContact()).isEqualTo("010.33.44.44");
        assertThat(updatedSavedFestival.getHomePageUrl()).isEqualTo("http://22,,,");
        assertThat(updatedSavedFestival.getReservationUrl()).isEqualTo("http://,,,");
        assertThat(updatedSavedFestival.getDescription()).isEqualTo("업데이트된 설명");
        assertThat(updatedSavedFestival.getOnelineDescription()).isEqualTo("업데이트된 한줄설명");
    }

    @Test
    @DisplayName("축제 controller delete")
    public void controllerDelete() {
        Festival savedFestival = festivalRepository.saveFestival(new Festival(
                null, "축제1", FestivalCategory.FESTIVAL, null, null, null, null, null,
                "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명",
                "한줄설명", new ArrayList<>(), new ArrayList<>()
        ));

        festivalRepository.deleteFestival(savedFestival.getId());

        assertThat(festivalRepository.findById(savedFestival.getId())).isNull();
    }

    @Test
    @DisplayName("축제 controller read")
    public void controllerRead() {
        FestivalReqDto festivalReqDto = new FestivalReqDto("축제2", FestivalCategory.FESTIVAL,  LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 3),  LocalTime.of(10, 0),
                LocalTime.of(18, 0), null, "서울...", "200원쯤?", "010.22..", "http://,,,,,", "http://,,,,,", "설명", "한줄설명");

        Festival savedFestival = festivalRepository.saveFestival(festivalReqDto.toEntity());
        Festival findFestival = festivalRepository.findById(savedFestival.getId());

        assertThat(findFestival.getName()).isEqualTo("축제2");
        assertThat(findFestival.getLocation()).isEqualTo("서울...");
        assertThat(findFestival.getCategory()).isEqualTo(FestivalCategory.FESTIVAL);
        assertThat(findFestival.getStartDate()).isEqualTo(LocalDate.of(2025, 5, 1));
        assertThat(findFestival.getEndDate()).isEqualTo(LocalDate.of(2025, 5, 3));
        assertThat(findFestival.getStartTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(findFestival.getEndTime()).isEqualTo(LocalTime.of(18, 0));
        assertThat(findFestival.getTimeDescription()).isEqualTo(null);
        assertThat(findFestival.getLocation()).isEqualTo("서울...");
        assertThat(findFestival.getFee()).isEqualTo("200원쯤?");
        assertThat(findFestival.getContact()).isEqualTo("010.22..");
        assertThat(findFestival.getHomePageUrl()).isEqualTo("http://,,,,,");
        assertThat(findFestival.getReservationUrl()).isEqualTo("http://,,,,,");
        assertThat(findFestival.getDescription()).isEqualTo("설명");
        assertThat(findFestival.getOnelineDescription()).isEqualTo("한줄설명");

    }

}
