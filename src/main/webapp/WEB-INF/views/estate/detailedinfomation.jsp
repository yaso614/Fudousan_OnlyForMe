<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    <title>boot</title>

    <!-- css -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="../resources/css/3dmodel.css" rel="stylesheet">
    <link href="../resources/css/templete.css" rel="stylesheet">
    <link href="../resources/css/detailedinfomationpage.css" rel="stylesheet">
     <link href="../resources/css/comment.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


	
	<!-- hidden value -->
	<input type="hidden" value="${resultEstate.estateX}" id="lat">
	<input type="hidden" value="${resultEstate.estateY}" id="lng">
	<input type="hidden" value="${estateId}" id="estateId">
	<input type="hidden" value="${sessionScope.memberId }" id="memberId">
	
	<!-- js -->
	<script src="../resources/js/jquery-3.3.1.js"></script>
	<script src="../resources/js/login.js"></script>
	<script src="../resources/js/bootstrap.min.js"></script>
	<script src="../resources/js/estatedetailinit.js"></script>    
	<script src="../resources/js/reply.js"></script>  
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB1tbIAqN0XqcgTR1-FxYoVTVq6Is6lD98&callback=initMap">
	</script>
	<script src="../resources/js/infiniteScrollPagingAjax.js"></script>    
    <script src="../resources/js/estateFavorite.js"></script>    
    
    <!-- 다국어 처리 -->
	<script src="../resources/js/cookie.js"></script>
	<script src="../resources/js/translation.js"></script>

</head>
<body>

	<!-- 3d design modal  -->
 	<%@include file="/WEB-INF/views/include/3ddesignmodal.jsp" %>
 	
 	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>

	<!-- main -->
	<%@include file="/WEB-INF/views/include/estatedetailmain.jsp" %>

	<!-- footer  -->
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
    
</body>
</html>