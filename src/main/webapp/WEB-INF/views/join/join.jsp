<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
	
	<title>Customer Join</title>
	
	<!-- 부트스트랩 -->
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="<c:url value="/resources/css/templete.css"/>" >
	<!-- font awesome -->
	<link rel="stylesheet" href="../resources/css/font-awesome.min.css" media="screen" title="no title">
	
	<!-- Custom style -->
	<link rel="stylesheet" href="../resources/css/style.css" media="screen" title="no title">
   
</head>
<body>
 
 	 
	<!-- header -->
	<%@include file="/WEB-INF/views/include/joinHeader.jsp" %>
	
	<!-- join main -->
	<%@include file="/WEB-INF/views/include/joinmain.jsp" %>
	
	<!-- footer -->
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
	
	<!-- jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	
	<!-- bootstrap  -->
	<script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>
	
	<!-- join.js -->
	<script src="<c:url value="/resources/js/join.js"></c:url>"></script>
	
	
	<!-- Form check.js -->
	<script src="<c:url value="/resources/js/memberFormCheck.js"></c:url>"></script>
	
	<!-- email check.js -->
	<script src="<c:url value="/resources/js/checkEmail.js"></c:url>"></script>	
	
	<!-- 다국어 처리 -->
	<script src="../resources/js/cookie.js"></script>
	<script src="../resources/js/translation.js"></script>
</body>
</html>