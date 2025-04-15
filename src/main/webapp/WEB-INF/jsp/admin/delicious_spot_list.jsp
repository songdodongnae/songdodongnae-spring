<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Songdodongnae admin</title>
<style>
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ccc; padding: 8px; }
    th { background: #f5f5f5; }
</style>
</head>
<body>
    <h1>맛집 리스트</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>이름</th>
                <th>위치</th>
                <th>가격</th>
                <th>네이버 평점</th>
                <th>카카오 평점</th>
                <th>영업 시작</th>
                <th>영업 종료</th>
                <th>웨이팅</th>
                <th>주차</th>
                <th>추천 메뉴</th>
                <th>한줄 설명</th>
                <th>대표 이미지</th>
                <th>좋아요</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="spot" items="${deliciousSpotList}">
                <tr>
                    <td>${spot.id}</td>
                    <td>${spot.name}</td>
                    <td>${spot.location}</td>
                    <td>${spot.price}</td>
                    <td>${spot.naverRating}</td>
                    <td>${spot.kakaoRating}</td>
                    <td>${spot.startTime}</td>
                    <td>${spot.endTime}</td>
                    <td>${spot.waiting}</td>
                    <td>${spot.parking}</td>
                    <td>${spot.suggestionMenu}</td>
                    <td>${spot.onelineDescription}</td>
                    <td>${spot.imageUrl}</td>
                    <td>${spot.likes}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
