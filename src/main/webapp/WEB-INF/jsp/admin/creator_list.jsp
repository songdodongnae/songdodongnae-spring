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
</style>
</head>
<body>
    <h1>크리에이터 목록</h1>
    <label class="field">id</label>
    <label class="field">name</label>
    <label class="long-field">introduction</label>
    <label class="long-field">description</label>
    <label class="long-field">series</label>
    <label class="long-field">imageUrl</label>

    <div>
        <c:forEach var="creator" items="${creatorList}">
            <div style="width: 100%; height: 2px; background-color: black;"></div>
            <div>
                <label class="field">${creator.id}</label>
                <label class="field">${creator.name}</label>
                <label class="long-field">${creator.introduction}</label>
                <label class="long-field">${creator.description}</label>
                <label class="long-field">${creator.series}</label>
                <a href=${creator.imageUrl} class="long-field">이미지 링크 이동</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>