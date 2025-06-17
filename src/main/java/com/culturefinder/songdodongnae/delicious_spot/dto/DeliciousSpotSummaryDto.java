package com.culturefinder.songdodongnae.delicious_spot.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliciousSpotSummaryDto {
    private Long id;
    private String title;

    public static DeliciousSpotSummaryDto fromEntity(DeliciousSpot deliciousSpot) {
        return new DeliciousSpotSummaryDto(
                deliciousSpot.getId(),
                deliciousSpot.getTitle()
        );
    }
}
