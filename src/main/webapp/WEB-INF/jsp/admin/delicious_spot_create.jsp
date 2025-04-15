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
    <form action="/admin/delicious_spot/create" method="post" enctype="multipart/form-data">
        <label>이름</label>
        <input type="text" name="name"><br>

        <label>위치</label>
        <input type="text" name="location"><br>

        <label>가격</label>
        <input type="number" name="price"><br>

        <label>네이버 평점</label>
        <input type="number" step="0.1" name="naverRating"><br>

        <label>카카오 평점</label>
        <input type="number" step="0.1" name="kakaoRating"><br>

        <label>영업 시작 시간</label>
        <input type="time" name="startTime"><br>

        <label>영업 종료 시간</label>
        <input type="time" name="endTime"><br>

        <label>웨이팅</label>
        <input type="text" name="waiting"><br>

        <label>주차</label>
        <input type="text" name="parking"><br>

        <label>추천 메뉴</label>
        <input type="text" name="suggestionMenu"><br>

        <label>상세 설명</label>
        <textarea name="description"></textarea><br>

        <label>한줄 설명</label>
        <input type="text" name="onelineDescription"><br>

        <label>인스타그램</label>
        <input type="text" name="instagram"><br>

        <label>연락처</label>
        <input type="text" name="contact"><br>

        <label>좋아요 수</label>
        <input type="number" name="likes" value="0" readonly><br>

        <label>대표 이미지</label>
        <input type="file" name="image"><br>

        <label>상세 이미지</label>
        <input type="file" name="files" multiple><br>

        <button type="submit">등록</button>
    </form>
</body>
</html>