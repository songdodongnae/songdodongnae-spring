package com.culturefinder.songdodongnae.admin.dto;

import com.culturefinder.songdodongnae.series.domain.Series;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminSeriesDto {

    private Long id;

    private String title;

    public AdminSeriesDto(Series series) {
        this.id = series.getId();
        this.title = series.getTitle();
    }
}
