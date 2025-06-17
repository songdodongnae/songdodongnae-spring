package com.culturefinder.songdodongnae.series.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesSummaryDto {
    private Long id;
    private String title;

    public static SeriesSummaryDto fromEntity(com.culturefinder.songdodongnae.series.domain.Series series) {
        return new SeriesSummaryDto(
                series.getId(),
                series.getTitle()
        );
    }
}
