package com.culturefinder.songdodongnae.admin;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Value("${admin.id}") private String adminId;

    @Value("${admin.password}") private String adminPassword;

    private final AdminSessionManager adminSessionManager;

    @GetMapping("/login")
    public String login_get(@CookieValue("admin-session") String session) {
        if (adminSessionManager.contains(session)) return "redirect:/admin/main";
        return "admin/login";
    }

    @PostMapping("/login")
    public String login_post(@ModelAttribute AdminUser adminUser, HttpServletResponse response) throws IOException {
        if (adminUser.getId().equals(adminId) && adminUser.getPassword().equals(adminPassword)) {
            String session = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("admin-session", session);
            response.addCookie(cookie);
            adminSessionManager.store(session);
            return "redirect:/admin/main";
        }
        return "redirect/admin/login";
    }

    @GetMapping("/main")
    public String main_get() {
        return "admin/main";
    }

    @GetMapping("/delicious")
    public String delicious_get() {
        return "admin/delicious";
    }
}
