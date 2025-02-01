package com.culturefinder.songdodongnae.admin;

import com.culturefinder.songdodongnae.admin.dto.AdminLoginRequestDto;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Value("${admin.id}")
    private String adminId;

    @Value("${admin.password}")
    private String adminPassword;

    private final AdminSessionManager adminSessionManager;

    public boolean isContainAdminSession(String session) {
        return adminSessionManager.contains(session);
    }

    public boolean isValidIdPw(AdminLoginRequestDto adminLoginInputDto) {
        return adminLoginInputDto.getId().equals(adminId)
                && adminLoginInputDto.getPassword().equals(adminPassword);
    }

    public Cookie issueAdminSession() {
        String sessionString = UUID.randomUUID().toString();
        Cookie session = new Cookie("admin-session", sessionString);
        adminSessionManager.store(sessionString);
        return session;
    }

}
