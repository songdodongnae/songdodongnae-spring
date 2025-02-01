package com.culturefinder.songdodongnae.admin.controller;

import com.culturefinder.songdodongnae.admin.dto.AdminLoginRequestDto;
import com.culturefinder.songdodongnae.admin.AdminService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBasicController {

    private final AdminService adminService;

    @GetMapping("/login")
    public String login_get(@CookieValue("admin-session") String session) {
        if (adminService.isContainAdminSession(session)) {
            return "redirect:/admin/main";
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login_post(@ModelAttribute AdminLoginRequestDto adminUser, HttpServletResponse response) {
        if (adminService.isValidIdPw(adminUser)) {
            Cookie adminSession = adminService.issueAdminSession();
            response.addCookie(adminSession);
            return "redirect:/admin/main";
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/main")
    public String main_get() {
        return "admin/main";
    }

}
