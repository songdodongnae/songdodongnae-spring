package com.culturefinder.songdodongnae.admin.dto;

import com.culturefinder.songdodongnae.delicious_spot.domain.DeliciousSpotList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminDeliciousSpotListDto {

    private Long id;

    private String title;

    public AdminDeliciousSpotListDto(DeliciousSpotList deliciousSpotList) {
        this.id = deliciousSpotList.getId();
        this.title = deliciousSpotList.getTitle();
    }
}
