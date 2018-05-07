<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>">
<script src="resources/js/jquery-3.3.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>3D 작성 정보</title>
<body class="container">
	<header class="col-sm-12">헤더</header>
	<div class="col-sm-12">
		<label class="col-sm-12">3D 작성 매물 확인</label>
		<c:forEach var="three" items="${threeList}">
			<table border="1">
				<tr>
					<th>항목</th>
					<td>값</td>
				</tr>			
				<tr>
					<th>roomId</th>
					<td>${three.roomId}</td>
				</tr>			
				<tr>
					<th>estate</th>
					<td>${three.estate.estateName}</td>
				</tr>			
				<tr>
					<th>snapshot</th>
					<td>${three.snapshot}</td>
				</tr>			
				<tr>
					<th>map</th>
					<td>${three.map}</td>
				</tr>			
				<tr>
					<th>creDate</th>
					<td>${three.creDate}</td>
				</tr>			
				<tr>
					<th>delDate</th>
					<td>${three.delDate}</td>
				</tr>			
				<tr>
					<th>heigth</th>
					<td>${three.heigth}</td>
				</tr>			
				<tr>
					<th>floorTexture</th>
					<td>${three.floorTexture}</td>
				</tr>			
				<tr>
					<th>ceilingTexture</th>
					<td>${three.ceilingTexture}</td>
				</tr>			
				<tr>
					<th>roomPublic</th>
					<td>${three.roomPublic}</td>
				</tr>			
			</table>			
		</c:forEach>
	</div> <!-- 전체 12칸 짜리div -->
	<footer class="col-sm-12">푸터</footer>
</body>
</html>