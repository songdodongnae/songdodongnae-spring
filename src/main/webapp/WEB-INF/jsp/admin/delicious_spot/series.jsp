<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Songdodongnae admin</title>
</head>
<body>
    <h1>맛집 시리즈: ${deliciousSpotTitle}</h1>
    <h1>맛집 목록</h1>
    <ul>
        <c:forEach var="item" items="${deliciousSpotList}" varStatus="status">
            <h3>[ id = ${item.id} ] ${item.name}</h3>
        </c:forEach>
    </ul>
    <h1>맛집 시리즈에 맛집 등록</h1>
    <form action="/admin/delicious_spot/series?id=${deliciousSpotId}" method="post">
        <p>
            <input type="text" name="id" placeholder="id", value="${deliciousSpotId}" readonly/>
        </p>
        <p>
            <input type="text" name="name" placeholder="name">
        </p>
        <p>
            <input type="text" name="location" placeholder="location">
        </p>
        <p>
            <input type="text" name="price" placeholder="price">
        </p>
        <p>
            <input type="text" name="naverRating" placeholder="naver_rating">
        </p>
        <p>
            <input type="text" name="kakaoRating" placeholder="kakao_rating">
        </p>
        <p>
            <input type="time" name="startTime" placeholder="시작시간">
        </p>
        <p>
            <input type="time" name="endTime" placeholder="마감시간">
        </p>
        <p>
            <input type="text" name="waiting" placeholder="웨이팅">
        </p>
        <p>
            <input type="text" name="parking" placeholder="주차">
        </p>
        <p>
            <input type="text" name="suggestionMenu" placeholder="추천메뉴">
        </p>
        <p>
            <input type="text" name="description" placeholder="설명">
        </p>
        <p>
            <input type="text" name="onelineDescription" placeholder="한줄 설명">
        </p>
        <p>
            <input type="text" name="instagram" placeholder="인스타그램">
        </p>
        <p>
            <input type="text" name="contact" placeholder="매장 번호">
        </p>
        <p>
            <input type="text" name="likes" placeholder="0" readonly/>
        </p>
        <p>
            <input type="text" name="imageLinks" placeholder="이미지 링크 리스트 (띄어쓰기 구분)">
        </p>
        <p>
            <button type="submit">저장</button>
        </p>
    </form>
</body>
</html>