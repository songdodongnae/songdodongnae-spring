package com.culturefinder.songdodongnae.mypage;

import com.culturefinder.songdodongnae.curation.domain.Curation;
import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import com.culturefinder.songdodongnae.festival.domain.Festival;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ThumbnailResDto {

    private Long id;
    private String title;
    private String imageUrl;

    public static ThumbnailResDto of(Festival festival) {
        return ThumbnailResDto.builder()
                .id(festival.getId())
                .title(festival.getTitle())
                .imageUrl(festival.getThumbnailImageUrl())
                .build();
    }

    public static ThumbnailResDto of(DeliciousSpot spot) {
        return ThumbnailResDto.builder()
                .id(spot.getId())
                .title(spot.getTitle())
                .imageUrl(spot.getThumbnailImageUrl())
                .build();
    }

    public static ThumbnailResDto of(Curation curation) {
        return ThumbnailResDto.builder()
                .id(curation.getId())
                .title(curation.getTitle())
                .imageUrl(curation.getImageUrl())
                .build();
    }
}
