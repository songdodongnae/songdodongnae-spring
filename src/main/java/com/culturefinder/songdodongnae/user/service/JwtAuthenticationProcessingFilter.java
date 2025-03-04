package com.culturefinder.songdodongnae.user.service;

import com.culturefinder.songdodongnae.user.domain.User;
import com.culturefinder.songdodongnae.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final String[] whiteList = {"/admin/**", "/swagger-ui/**", "/v3/**"};

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (isUriInWhiteList(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            validateRefreshTokenAndReIssueAccessToken(response, refreshToken);
        } else {
            checkAccessToken(request, response);
        }

        filterChain.doFilter(request, response);
    }

    private void validateRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken).ifPresentOrElse (
                        user -> {
                            String reIssuedRefreshToken = reIssueRefreshToken(user);
                            jwtService.sendAccessAndRefreshToken(
                                    response,
                                    jwtService.createAccessToken(user.getId()),
                                    reIssuedRefreshToken
                            );
                            log.info("access 재발급");
                        },
                        () -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }

    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        userRepository.updateUserRefreshToken(user, reIssuedRefreshToken);
        return reIssuedRefreshToken;
    }

    private void checkAccessToken(HttpServletRequest request, HttpServletResponse response) {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresentOrElse(
                        accessToken -> {
                            jwtService.extractId(accessToken)
                                    .flatMap(userRepository::findById)
                                    .ifPresent(this::saveAuthentication);
                            log.info("access token 으로 접근");
                        },
                        () -> {
                            log.info("처음 저장");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }
                );
    }

    private void saveAuthentication(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(String.valueOf(user.getId()))
                .password("")
                .roles("USER")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean isUriInWhiteList(String uri) {
        return Arrays.stream(whiteList).anyMatch(pattern -> uri.matches(pattern.replace("**", ".*")));
    }
}
