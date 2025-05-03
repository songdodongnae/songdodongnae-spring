<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시리즈-맛집 다대다 매핑</title>
<style>
    label { margin-right: 10px; }
    select { margin-right: 20px; }
</style>
</head>
<body>
    <h1>시리즈-맛집 관리</h1>
    <form action="/admin/series_delicious_spot" method="post">

        <label for="seriesId">시리즈 선택:</label>
        <select name="seriesId" id="seriesId" required>
            <option value="">-- 시리즈 선택 --</option>
            <c:forEach var="series" items="${seriesList}">
                <option value="${series.id}">${series.title} (ID: ${series.id})</option>
            </c:forEach>
        </select>

        <label for="deliciousSpotId">맛집 선택:</label>
        <select name="deliciousSpotId" id="deliciousSpotId" required>
            <option value="">-- 맛집 선택 --</option>
            <c:forEach var="spot" items="${deliciousSpotList}">
                <option value="${spot.id}">${spot.name} (ID: ${spot.id})</option>
            </c:forEach>
        </select>

        <button type="submit">추가</button>
    </form>
</body>
</html>
