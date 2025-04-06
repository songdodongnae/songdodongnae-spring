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
        width: 100px;
        text-align: left;
    }
</style>
</head>
<body>
    <h1>시리즈 등록</h1>
    <form action="/admin/series/create" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="creator">크리에이터</label>
            <select name="creatorId">
                <c:forEach var="creator" items="${creatorList}">
                    <option value="${creator.id}">${creator.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" name="title">
        </div>
        <div class="form-group">
            <label for="image">대표 이미지</label>
            <input type="file" name="file">
        </div>
        <div class="form-group">
            <label for="category">카테고리</label>
            <select name="category">
                <c:forEach var="category" items="${categoryList}">
                    <option value="${category}">${category}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="isOnMain">메인 노출</label>
            <input type="checkbox" name="isOnMain" value="true">
        </div>
        <div class="form-group">
            <label for="orderNumber">정렬 순서</label>
            <input type="number" name="orderNumber" min="1">
        </div>
        <div>
            <button type="submit">등록</button>
        </div>
    </form>
</body>
</html>