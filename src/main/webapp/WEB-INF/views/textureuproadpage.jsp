<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



<form action="textureuproad" method="post" enctype="multipart/form-data">
사용자 ID<input type="text" name="memberId" value="${sessionScope.loginId}" readonly/><br>
텍스쳐 이름<input type="text" name="name"><br>
텍스쳐 설명<input type="text" name="text"><br>
텍스쳐 파일<input type="file" name="file">

<input type="submit" value="등록  " > 


</form>

<br><br><br><br><br><br><br><br>

<%-- <table border=1>
<tr>
	<th>텍스쳐 ID</th>
	<th>텍스쳐 이름</th>
	<th>텍스쳐 파일</th>
</tr>

<tr>
	<td>${texture.textureId}</td>
	<td>${texture.name}</td>
	<td>${texture.file }
</table> --%>






</body>
</html>