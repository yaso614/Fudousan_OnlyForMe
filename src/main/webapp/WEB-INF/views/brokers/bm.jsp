<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
 	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    
    <title>Agency Page</title>
	
    <!-- 부트스트랩 -->
	<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
	<link rel="stylesheet" href="<c:url value="/resources/css/templete.css"/>" >

	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    
    <!-- bootstrap -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>

	<!-- login.js -->
	<script src="<c:url value="/resources/js/login.js"></c:url>"></script>
	
	<!-- bm.js -->
	<script src="<c:url value="/resources/js/bm.js"></c:url>"></script>
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
	<![endif]-->
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	
	<!-- 다국어 처리 -->
	<script src="<c:url value="resources/js/cookie.js"/>"></script>
	<script src="<c:url value="resources/js/translation.js"/>"></script>
	
<body>
	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
 	
 	
 	
	<!-- estate list modal  -->
 	<%@include file="estateListModal.jsp" %> 
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	
	<!-- main -->
	<%@include file="/WEB-INF/views/include/agencypagemain.jsp" %>

	<!-- footer -->
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
</body>

</html>

