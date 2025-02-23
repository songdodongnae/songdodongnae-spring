<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Songdodongnae admin</title>
<style>
    #series-list-container {
        border: 2px solid black;
        border-radius: 12px;
        padding: 20px;
        width: 300px;
        margin-bottom: 24px;
    }
    #form-container {
        border: 2px solid black;
        border-radius: 12px;
        padding: 20px;
        width: 300px;
    }
</style>
</head>
<body>
    <div id="series-list-container">
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
    </div>

    <div id="form-container">
        <h1>맛집 시리즈 등록</h1>
        <form action="/admin/delicious_spot/series_list" method="post" enctype="multipart/form-data" onsubmit="return validateFile()">
            <p>
                <input type="text" name="title" placeholder="맛집 시리즈 이름" id="id_title">
            </p>
            <p>
                <input type="file" name="file" id="fileInput">
            </p>
            <p>
                <button type="submit">빈 맛집 시리즈 추가</button>
            </p>
        </form>
    </div>

    <script>
        <!-- 파일 확장자 검사 -->
        function validateFile() {
            var fileInput = document.getElementById('fileInput');
            var title = document.getElementById('id_title').value.trim();
            var filePath = fileInput.value;
            var allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i; // 허용할 확장자

            if (title === "") {
                alert('제목을 꼭 입력하셔야 합니다.');
                return false;
            }

            if (!fileInput.files.length) {
                return true;
            }

            if (!allowedExtensions.exec(filePath)) {
                alert('JPG, JPEG, PNG 파일만 업로드 가능합니다.');
                fileInput.value = ''; // 파일 선택 취소
                return false; // 제출 막기
            }
            return true;
        }
    </script>
</body>
</html>