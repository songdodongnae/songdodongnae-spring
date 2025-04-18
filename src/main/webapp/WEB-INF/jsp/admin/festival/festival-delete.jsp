<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Songdodongnae admin</title>
    <style>
        .form-group label {
            width: 80px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>축제 삭제</h1>
<form action="/admin/festival/delete/${id}" method="post">
    <p>"${festival.name}" 축제를 정말 삭제하시겠습니까?</p>
    <button type="submit">삭제</button>
    <a href="/admin/festival/list">취소</a>
</form>
</body>
</html>
