package com.culturefinder.songdodongnae.creator;

import com.culturefinder.songdodongnae.creator.domain.Creator;
import com.culturefinder.songdodongnae.creator.dto.CreatorReqDto;
import com.culturefinder.songdodongnae.creator.dto.CreatorResDto;
import com.culturefinder.songdodongnae.creator.repository.CreatorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
public class CreatorRepositoryTest {

    @Autowired
    private CreatorRepository creatorRepository;

    @Test
    @DisplayName("크리에이터가 올바르게 생성되는지 확인하는 테스트")
    void test01() {
        CreatorReqDto creatorReqDto = new CreatorReqDto("두둥", "매운걸 좋아하는", "순대가 좋아요");
        Creator savedCreator = creatorRepository.saveCreator(creatorReqDto.toEntity());
        CreatorResDto creatorResDto = savedCreator.fromEntity();

        Assertions.assertThat(creatorReqDto.getName()).isEqualTo(creatorResDto.getName());
        Assertions.assertThat(creatorReqDto.getDescription()).isEqualTo(creatorResDto.getDescription());
    }

}
