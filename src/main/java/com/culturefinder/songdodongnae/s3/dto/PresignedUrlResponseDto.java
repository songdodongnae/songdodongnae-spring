package com.culturefinder.songdodongnae.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlResponseDto {
    private String presignedUrl;
    private String imageUrl;
}
