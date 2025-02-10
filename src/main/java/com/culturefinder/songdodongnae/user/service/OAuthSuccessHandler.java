package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        String providerId = extractProviderId(authentication);

        Optional<User> user = userRepository.findByProviderIdAndProvider(providerId, provider);
        user.ifPresent(u -> loginSuccess(request, response, u.getId()));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://www.songdodongnae.n-e.kr/");
        builder.path("auth");

        response.sendRedirect(builder.build().toString());
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, Long id) {
        String accessToken = jwtService.createAccessToken(id);
        String refreshToken = jwtService.createRefreshToken();

        ResponseCookie accessCookie = createCookie("Authorization", accessToken);
        ResponseCookie refreshCookie = createCookie("Authorization-refresh", refreshToken);

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(id, refreshToken);
    }

    private ResponseCookie createCookie(String key, String value) {
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .maxAge(60)
                .path("/")
                .httpOnly(false)
                .secure(true)
                .sameSite("None")
                .build();
        return cookie;
    }

    private String extractProviderId(Authentication authentication) {
        return authentication.getName().replaceAll("[{}]|id=", "");
    }
}
