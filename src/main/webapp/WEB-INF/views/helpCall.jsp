<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>메일 보내기</title>
</head>
<body>
	<table border="1">
		<tr>
		 	<th>인테리어업체 이메일</th>
		 	<th>업체 번호</th>
		 	<th>업체 소개 </th>
		 	<th>메시지 보내기</th>
		</tr>
		
		<c:forEach var="item" items="${interior}">
			<tr>
				<td>${item.email}</td>
				<td>${item.phone}</td>
				<td>${item.text}</td>
				<td> <a href="helpCall?tomail=${item.email}" class="btn btn-warning" OnClick="alert(' ${item.email} 디자이너에게 요청 메시지가 전송되었습니다.')">요청</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>