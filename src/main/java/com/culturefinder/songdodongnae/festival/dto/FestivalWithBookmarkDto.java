package com.culturefinder.songdodongnae.festival.dto;

import com.culturefinder.songdodongnae.festival.domain.Festival;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FestivalWithBookmarkDto {
    private Festival festival;
    private Boolean isBookmarked;
}
