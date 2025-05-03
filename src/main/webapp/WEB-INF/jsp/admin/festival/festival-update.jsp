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
<h1>축제 수정</h1>
<form action="/admin/festival/update/${id}" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label for="name">이름</label>
        <input type="text" name="name" value="${festival.name}">
    </div>
    <div class="form-group">
        <label for="startDate">시작일</label>
        <input type="date" name="startDate" value="${festival.startDate}">
    </div>
    <div class="form-group">
        <label for="endDate">종료일</label>
        <input type="date" name="endDate" value="${festival.endDate}">
    </div>
    <div class="form-group">
        <label for="startTime">시작 시간</label>
        <input type="time" name="startTime" value="${festival.startTime}">
    </div>
    <div class="form-group">
        <label for="endTime">종료 시간</label>
        <input type="time" name="endTime" value="${festival.endTime}">
    </div>
    <div class="form-group">
        <label for="timeDescription">시간 설명</label>
        <input type="text" name="timeDescription" value="${festival.timeDescription}">
    </div>
    <div class="form-group">
        <label for="location">장소</label>
        <input type="text" name="location" value="${festival.location}">
    </div>
    <div class="form-group">
        <label for="fee">입장료</label>
        <input type="text" name="fee" value="${festival.fee}">
    </div>
    <div class="form-group">
        <label for="contact">연락처</label>
        <input type="text" name="contact" value="${festival.contact}">
    </div>
    <div class="form-group">
        <label for="homePageUrl">홈페이지</label>
        <input type="text" name="homePageUrl" value="${festival.homePageUrl}">
    </div>
    <div class="form-group">
        <label for="reservationUrl">예약 링크</label>
        <input type="text" name="reservationUrl" value="${festival.reservationUrl}">
    </div>
    <div class="form-group">
        <label for="onelineDescription">한줄 소개</label>
        <input type="text" name="onelineDescription" value="${festival.onelineDescription}">
    </div>
    <div class="form-group">
        <label for="description">상세 설명</label>
        <input type="text" name="description" value="${festival.description}">
    </div>
    <button type="submit">수정</button>
</form>
</body>
</html>
