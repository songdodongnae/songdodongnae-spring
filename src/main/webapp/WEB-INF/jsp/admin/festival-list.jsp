<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Songdodongnae admin</title>
    <style>
        .field {
            width: 80px;
            display: inline-block;
            text-align: center;
        }
        .long-field {
            width: 160px;
            display: inline-block;
            text-align: center;
        }
        .url-field {
            width: 200px;
            display: inline-block;
            text-align: center;
        }
        .file {
            width: 300px;
            display: inline-block;
            text-align: center;
        }
        .record {
            margin-top: 10px;
            border-top: 1px solid #000;
            padding-top: 10px;
        }
    </style>
</head>
<body>

<h1>축제 목록</h1>

<div>
    <label class="field">ID</label>
    <label class="field">이름</label>
    <label class="long-field">기간</label>
    <label class="long-field">시간</label>
    <label class="long-field">장소</label>
    <label class="long-field">입장료</label>
    <label class="long-field">연락처</label>
    <label class="url-field">홈페이지</label>
    <label class="url-field">예약 URL</label>
    <label class="long-field">한줄 설명</label>
    <label class="long-field">상세 설명</label>
    <label class="file">포스터</label>
</div>

<c:if test="${empty festivals}">
    <p>등록된 축제가 없습니다.</p>
</c:if>

<div>
    <c:forEach var="festival" items="${festivals}">
        <div class="record">
            <label class="field">${festival.id}</label>
            <label class="field">${festival.name}</label>
            <label class="long-field">${festival.startDate} ~ ${festival.endDate}</label>
            <label class="long-field">${festival.startTime} ~ ${festival.endTime}</label>
            <label class="long-field">${festival.location}</label>
            <label class="long-field">${festival.fee}</label>
            <label class="long-field">${festival.contact}</label>
            <a href="${festival.homePageUrl}" class="url-field" target="_blank">홈페이지</a>
            <a href="${festival.reservationUrl}" class="url-field" target="_blank">예약 링크</a>
            <label class="long-field">${festival.onelineDescription}</label>
            <label class="long-field">${festival.description}</label>

            <div class="file">
                <c:forEach var="img" items="${festival.posterImages}">
                    <a href="${img}" target="_blank">[이미지]</a>&nbsp;
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>