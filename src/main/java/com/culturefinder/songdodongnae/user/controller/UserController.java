package com.culturefinder.songdodongnae.user.controller;

import com.culturefinder.songdodongnae.user.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final JwtService jwtService;

    // For development
    @GetMapping("/access-token/{id}")
    public String createAccessToken(@PathVariable Long id) {
        return jwtService.createAccessToken(id);
    }

}
