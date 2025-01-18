package com.culturefinder.songdodongnae.user;

import com.culturefinder.songdodongnae.user.service.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    @Autowired private JwtService jwtService;

    @Test
    @DisplayName("application.yml에서 가져온 정보가 null이 아닌지 확인")
    public void test1() {
        assertThat(jwtService.getSecretKey()).isNotNull();
        assertThat(jwtService.getAccessHeader()).isNotNull();
        assertThat(jwtService.getAccessTokenExpirationPeriod()).isNotNull();
        assertThat(jwtService.getRefreshHeader()).isNotNull();
        assertThat(jwtService.getRefreshTokenExpirationPeriod()).isNotNull();
    }

}
