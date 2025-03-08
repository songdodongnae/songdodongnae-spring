package com.culturefinder.songdodongnae.admin.creator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
@AllArgsConstructor
public class AdminCreatorCreateRequestDto {

    private String name;

    private String introduction;

    private String description;

    private MultipartFile file;

}
