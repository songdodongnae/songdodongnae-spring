<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Songdodongnae admin</title>
    <style>
        .form-group {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 8px;
        }
        .form-group label {
            width: 80px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>축제 등록</h1>
<form action="/admin/festival/create" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label for="name">이름</label>
        <input type="text" name="name">
    </div>
    <div class="form-group">
        <label for="startDate">시작일</label>
        <input type="date" name="startDate">
    </div>
    <div class="form-group">
        <label for="endDate">종료일</label>
        <input type="date" name="endDate">
    </div>
    <div class="form-group">
        <label for="startTime">시작 시간</label>
        <input type="time" name="startTime">
    </div>
    <div class="form-group">
        <label for="endTime">종료 시간</label>
        <input type="time" name="endTime">
    </div>
    <div class="form-group">
        <label for="timeDescription">시간 설명</label>
        <input type="text" name="timeDescription">
    </div>
    <div class="form-group">
        <label for="location">장소</label>
        <input type="text" name="location">
    </div>
    <div class="form-group">
        <label for="fee">요금</label>
        <input type="text" name="fee">
    </div>
    <div class="form-group">
        <label for="contact">연락처</label>
        <input type="text" name="contact">
    </div>
    <div class="form-group">
        <label for="homePageUrl">홈페이지</label>
        <input type="text" name="homePageUrl">
    </div>
    <div class="form-group">
        <label for="reservationUrl">예약 링크</label>
        <input type="text" name="reservationUrl">
    </div>
    <div class="form-group">
        <label for="onelineDescription">한줄 소개</label>
        <input type="text" name="onelineDescription">
    </div>
    <div class="form-group">
        <label for="description">상세 설명</label>
        <textarea name="description"></textarea>
    </div>
    <div class="form-group">
        <label for="postImage">이미지</label>
        <input type="file" name="file">
    </div>
    <button type="submit">등록</button>
</form>
</body>
</html>