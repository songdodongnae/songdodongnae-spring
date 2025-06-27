package com.culturefinder.songdodongnae.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlResponseDto {
    private String presignedUrl;
    private String imageUrl;
}
