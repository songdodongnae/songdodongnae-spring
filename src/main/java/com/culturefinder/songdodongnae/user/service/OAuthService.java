package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.OAuthAttributes;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.domain.UserProfile;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);

        // UserFile을 통해 User로 변환하는 작업 + DB에 업데이트 하는 작업 필요
        User user = saveOrUpdateUser(userProfile);

        return new DefaultOAuth2User(
                //Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey()))
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())),
                attributes,
                userNameAttributeName);
    }

    // DB 저장 또는 업데이트 메서드
    private User saveOrUpdateUser(UserProfile userProfile) {
        return userRepository.findByEmail(userProfile.getEmail())
                .map(user -> user.update(userProfile.getName()))
                .orElseGet(() -> userRepository.save(new User(userProfile)));
    }
}
