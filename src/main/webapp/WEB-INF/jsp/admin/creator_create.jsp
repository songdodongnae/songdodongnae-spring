<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <h1>크리에이터 등록</h1>
    <form action="/admin/creator/create" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">이름</label>
            <input type="text" name="name">
        </div>
        <div class="form-group">
            <label for="introduction">소개말</label>
            <input type="text" name="introduction">
        </div>
        <div class="form-group">
            <label for="description">상세 소개</label>
            <input type="text" name="description">
        </div>
        <div class="form-group">
            <label for="fileInput">이미지</label>
            <input type="file" name="file" id="fileInput">
        </div>
        <div>
            <button type="submit">등록</button>
        </div>
    </form>
</body>
</html>