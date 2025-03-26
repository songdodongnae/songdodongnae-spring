//package com.culturefinder.songdodongnae.admin.series.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//@Getter
//@AllArgsConstructor
//public class AdminSeriesCreateRequestDto {
//
//    private Long creatorId;
//
//    @NotBlank(message = "제목은 필수입니다.")
//    private String title;
//
//    @NotNull(message = "이미지를 등록해야 합니다.")
//    private MultipartFile file;
//
//    @NotNull(message = "카테고리는 필수입니다.")
//    private SeriesCategory category;
//
//    private Boolean isOnMain = false;
//
//    @Min(value = 1, message = "정렬 순서는 1 이상이어야 합니다.")
//    private Integer orderNumber;
//
//    // 기본 생성자 (Spring이 객체 생성할 때 필요)
//    public SeriesCreateRequest() {}
//
//    // 생성자
//    public SeriesCreateRequest(Long creatorId, String title, MultipartFile file,
//                               SeriesCategory category, Boolean isOnMain, Integer orderNumber) {
//        this.creatorId = creatorId;
//        this.title = title;
//        this.file = file;
//        this.category = category;
//        this.isOnMain = isOnMain;
//        this.orderNumber = orderNumber;
//}
//}
