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
    
    <title>Member Update Form</title>

    <!-- 부트스트랩 -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">

	<!-- font awesome -->
    <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.min.css"/>" media="screen" title="no title">
    
    <!-- Custom style -->
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" media="screen" title="no title">
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    
    <![endif]-->
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	
      
	
</head>
<body>
  	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>
   	
   	<!-- Main -->
	<%@include file="/WEB-INF/views/include/memberupdatemain.jsp" %>
 	
   
    <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
   
    <!-- bootstrap -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>
   
    <!-- join.js -->
    <script src="<c:url value="/resources/js/join.js"></c:url>"></script>
   
    <!-- login.js -->
	<script src="<c:url value="/resources/js/login.js"></c:url>"></script>
	
	<!-- Form check.js -->
	<script src="<c:url value="/resources/js/memberFormCheck.js"></c:url>"></script>
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
	
	<!-- (Optional) Latest compiled and minified JavaScript translation files -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/i18n/defaults-*.min.js"></script>
	
	<!-- 다국어 처리 -->
	<script src="../resources/js/cookie.js"></script>
	<script src="../resources/js/translation.js"></script>
   
   
   
  </body>
</html>