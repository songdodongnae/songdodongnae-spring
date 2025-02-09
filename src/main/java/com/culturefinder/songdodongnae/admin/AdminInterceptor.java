package com.culturefinder.songdodongnae.admin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final AdminSessionManager adminSessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String session = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("admin-session")) {
                session = cookie.getValue();
            }
        }
        if (session == null) return false;
        return adminSessionManager.contains(session);
    }
}
