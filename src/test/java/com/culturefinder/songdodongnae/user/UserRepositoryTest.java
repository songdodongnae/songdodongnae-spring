package com.culturefinder.songdodongnae.user;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.domain.UserProfile;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장하고 이메일로 검색되는지 확인")
    public void test1() throws Exception {
        UserProfile userProfile = new UserProfile("1234", "suee97", "cheatz@naver.com");
        User user = new User(userProfile);
        userRepository.saveUser(user);
        User findUser = userRepository.findUserByEmail(user.getEmail());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
    }

    @Test
    @DisplayName("유저 이메일이 null일 때 유저를 찾지 못하는지 확인")
    public void test2() throws Exception {
        User findUser = userRepository.findUserByEmail(null);
        assertThat(findUser).isNull();
    }
}
