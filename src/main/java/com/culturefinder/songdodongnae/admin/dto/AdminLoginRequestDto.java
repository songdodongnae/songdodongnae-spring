package com.culturefinder.songdodongnae.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminLoginRequestDto {
    private String id;
    private String password;
}
