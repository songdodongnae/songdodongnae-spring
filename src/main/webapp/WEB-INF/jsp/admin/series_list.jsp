<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Songdodongnae admin</title>
<style>

</style>
</head>
<body>
    <h1>시리즈 조회</h1>
    <table border="1">
        <thead>
            <tr>
                <th>id</th>
                <th>제목</th>
                <th>대표 이미지 링크</th>
                <th>정렬 순서</th>
            </tr>
        </thead>
        <tbody>
                <c:forEach var="series" items="${seriesList}">
                    <tr>
                        <td>${series.id}</td>
                        <td>${series.title}</td>
                        <td><a href=${series.imageUrl}>이미지 링크 (클릭)</a></td>
                        <td>${series.orderNumber}</td>
                    </tr>
                </c:forEach>
        </tbody>
    </table>


</body>
</html>