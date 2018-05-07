<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
    
    <title>Admin Page</title>
	
    <!-- 부트스트랩 -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
	<link rel="stylesheet" href="<c:url value="/resources/css/templete.css"/>" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"> --%>
	<%-- <script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script> --%>

</head>
<body >
	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	
	<!-- Admin Main -->
	<%@include file="/WEB-INF/views/include/adminmain.jsp" %> 
	
	
	
	<!-- footer -->
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
	
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    
    <!-- bootstrap -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>

	<!-- login.js -->
	<script src="<c:url value="/resources/js/login.js"></c:url>"></script>
	
	<!-- 다국어 처리 -->
	<script src="../resources/js/cookie.js"></script>
	<script src="../resources/js/translation.js"></script>
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
	<![endif]-->
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	
	<script type="text/javascript">
		function confirm(agencyId) {
			location.href="./confirm?agencyId="+agencyId;
		}
		function deleteItem(itemId) {
			$.ajax({
				url:"../item/deleteItem?itemId="+itemId,
				type:"GET",
				success:function(data) {
					if(data || data == "true") {
						$("#item"+itemId).remove();
					} else {
						alert("삭제에 실패하였습니다.");
					}
				},
				error:function(e) {
					console.log(e);
					alert("삭제 중 오류가 발생하였습니다.");
				}
			});
		}
		function searchItem() {
			$.ajax({
				url:"../item/searchItem?itemName="+$("#itemName").val(),
				type:"GET",
				dataType:"JSON",
				success:function(obj) {
					
					$("#itemList").empty();
					$(obj).each(function(index, data) {
						var html = '<tr>'+
						' <td><label class="col-sm-6">'+data.itemName+'</label></td>'+
						' <td><a class="btn btn-success" href="../item/itemModifyPage?itemId='+data.itemId+'">수정</a></td>'+
						' <td><input class="btn btn-warning" type="button" onclick="deleteItem('+data.itemId+')" value="삭제"></td>'+
						'</tr>';
						
						$("#itemList").append(html);
					});
				},
				error:function(e) {
					console.log(e);
				}
			});
			
			return false;
		}
	</script>
	
	
	
	
	
	
	
	
	
	
</body>

</html>