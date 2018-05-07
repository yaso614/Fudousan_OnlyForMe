<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    
    <title>boot</title>
    
    <!-- css -->
    <link href="<c:url value="resources/css/bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="resources/css/home.css"/>" rel="stylesheet">
</head>

<body onload="initialize()">

 	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
 	
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	<input id="pac-input" class="controls form-control" type="text" placeholder="Search Box" style="z-index: 99; margin-top: 0.8%">
	<!-- Map -->
	<div id="map" style="width:100%; height:100%"></div>
	
	
	<!-- include js List -->
	<script src="resources/js/jquery-3.3.1.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	
	<!-- map js -->
	<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
	<script type="text/javascript">
		var locations = [
		/* 중개소 위치  */
		<c:forEach items="${locationList}" var="location" varStatus="status">
			${location},
		</c:forEach>
		/* 매물 위치  */
		<c:forEach items="${elocationList}" var="elocation" varStatus="status">
			${elocation}<c:if test="${status.last eq false}">,</c:if>
		</c:forEach>
		];
	</script>
	<script src="resources/js/googlemaps-settings.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB1tbIAqN0XqcgTR1-FxYoVTVq6Is6lD98&libraries=places&callback=initialize&language=en" async defer></script>
	
	<!-- 다국어 처리 -->
	<script src="resources/js/cookie.js"></script>
	<script src="resources/js/translation.js"></script>
	<script src="resources/js/login.js"></script>
	
	<!-- value -->
	<input type="hidden" value="${loginEmail }" id="loginEmail">
	<input type="hidden" value="${estateIdList }" id="estateIdList">
	<input type="hidden" value="${addList }" id="addList">


	
</body>
</html>





