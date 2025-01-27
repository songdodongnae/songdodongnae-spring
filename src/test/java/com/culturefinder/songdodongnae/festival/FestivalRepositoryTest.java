package com.culturefinder.songdodongnae.festival;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import com.culturefinder.songdodongnae.festival.domain.FestivalCategory;
import com.culturefinder.songdodongnae.festival.domain.FestivalImage;
import com.culturefinder.songdodongnae.festival.domain.FestivalPosterImage;
import com.culturefinder.songdodongnae.festival.repository.FestivalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class FestivalRepositoryTest {

    @Autowired private FestivalRepository festivalRepository;

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

        Festival festival = new Festival(null, "축제1", FestivalCategory.FESTIVAL, null, null, null, null, "서울...", "200원쯤?", "010.22..", "https:///....", "http://,,,,,", "설명", "한줄설명", festivalPosterImages, festivalImages);
        festivalPosterImage.setFestival(festival);
        festivalImage.setFestival(festival);

        Festival savedFestival = festivalRepository.saveFestival(festival);
        Assertions.assertThat(festival.getId()).isEqualTo(savedFestival.getId());
    }
}
