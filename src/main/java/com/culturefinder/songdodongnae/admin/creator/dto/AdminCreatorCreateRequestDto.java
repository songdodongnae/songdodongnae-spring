package com.culturefinder.songdodongnae.admin.creator.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreatorCreateRequestDto {

    private String name;

    private String introduction;

    private String description;

    private MultipartFile file;

}
