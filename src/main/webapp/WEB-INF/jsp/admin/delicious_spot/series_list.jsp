<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Songdodongnae admin</title>
</head>
<body>
    <h1>맛집 시리즈 목록</h1>
    <ul>
        <c:forEach var="item" items="${deliciousSpotList}">
            <div>
                <a href="/admin/delicious_spot/series?id=${item.id}">${item.title}</a>
                <form action="/admin/delicious_spot/series_list/delete" method="post" style="display: inline;">
                    <button type="submit" name="id" value=${item.id} style="width: 60px">[x]</button>
                </form>
            </div>
        </c:forEach>
    </ul>

    <form action="/admin/delicious_spot/series_list" method="post">
        <p>
            <input type="text" name="title" placeholder="맛집 리스트 제목">
        </p>
        <p>
            <input type="text" name="imageUrl" placeholder="대표 이미지 url">
        </p>
        <p>
            <button type="submit">빈 맛집 시리즈 추가</button>
        </p>
   </form>
</body>
</html>