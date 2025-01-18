package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.OAuthAttributes;
import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.domain.UserProfile;
import com.culturefinder.songdodongnae.user.domain.Role;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
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

        if (!isUserExist(userProfile)) {
            User user = new User(userProfile);
            userRepository.saveUser(user);
            log.info("유저 저장 유저 = {}", user.toString());
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name())),
                attributes,
                userNameAttributeName);
    }

    private boolean isUserExist(UserProfile userProfile) {
        User findUser = userRepository.findUserByEmail(userProfile.getEmail());
        return findUser != null;
    }
}
