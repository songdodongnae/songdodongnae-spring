package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.metamodel.internal.MemberResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        String providerId = authentication.getName();

        Optional<User> user = userRepository.findByProviderIdAndProvider(providerId, provider);

        log.info("provider = {}", provider);
        log.info("providerid = {}", providerId);
        log.info("user = {}", user);

        user.ifPresent(u -> loginSuccess(request, response, u.getId()));

        // [ 웹 서버 uri + 토큰 ] 으로 리다이렉트

        // 테스트 코드
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:3000");
        builder.path("auth");
//        builder.queryParam("accessToken", "abcdefg");


        response.sendRedirect(builder.build().toString());
    }

    private void loginSuccess(HttpServletRequest request,
                              HttpServletResponse response,
                              Long id){
        String accessToken = jwtService.createAccessToken(id);
        String refreshToken = jwtService.createRefreshToken();

//        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        response.addCookie(new Cookie("Authorization", "Bearer " + accessToken));
        response.addCookie(new Cookie("Authorization-refresh", "Bearer " + refreshToken));

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(id, refreshToken);
    }
}
