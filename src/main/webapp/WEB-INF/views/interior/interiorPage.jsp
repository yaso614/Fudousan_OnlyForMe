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
    
    <title>Interior Page</title>
	
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
	
	<!-- Interior Main -->
	<%@include file="interiormain.jsp" %>

	





<%-- <div class="container">


	<header class="col-sm-12">헤더</header>
	<div class="col-sm-12">
		<div class="col-sm-12">
			<h1>사용자 요청 리스트</h1>
			<c:forEach var="advice" varStatus="status" items="${adviceList}">
				<div id="req${status.index}" class="col-sm-12">
					<label>${advice.room.estate.estateId } : ${advice.room.estate.estateName }</label>
					<div>
						<button class="btn btn-default">인테리어 시작</button>
						<input class="btn btn-danger" type="button" value="취소" onclick = "unConfirm(${status.index}, ${advice.requestMemberId}, ${advice.room.roomId})">
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="col-sm-12">
			<h1>3D 작성 매물 확인</h1>
			<c:forEach var="room" varStatus="status" items="${realRoomList}">
				<div id="room${room.roomId}" class="col-sm-12">
					<label>${room.estate.estateId } : ${room.estate.estateName }</label>
					<a class="btn btn-default" href="../newRoom?estateId=${room.estate.estateId}&roomPublic=0">새로 꾸미기</a>
					<a class="btn btn-default" href="../roomPage?roomId=${room.roomId}">수정</a>
					<label class="radio-inline"><input name="public${room.roomId}" type="radio" value="1" roomId="${room.roomId}"<c:if test="${room.roomPublic == 1}"> checked="checked"</c:if>>공개</label>
					<label class="radio-inline"><input name="public${room.roomId}" type="radio" value="0" roomId="${room.roomId}"<c:if test="${room.roomPublic == 0}"> checked="checked"</c:if>>비공개</label>
				</div>
			</c:forEach>
		</div>
		<div class="col-sm-12">
			<h1>내가 만든 리스트</h1>
			<c:forEach var="virtual" varStatus="status" items="${notRealRoomList}">
				<div id="virtual${virtual.roomId}" class="col-sm-12">
					<label>${virtual.roomId} : ${virtual.snapshot}</label>
					<div>
						<img style="height: 100px; width: auto;" src="<c:url value="${virtual.snapshot}"/>">
					</div>
					
					<div>
						<div>
							<a class="btn btn-default" href="../wall/wallPage?roomId=${virtual.roomId}">벽 수정</a>
							<a class="btn btn-default" href="../roomPage?roomId=${virtual.roomId}">수정</a>
							<input class="btn btn-default" type="button" value="삭제" onclick="deleteRoom(${virtual.roomId})">
						</div>
						<div>
							<label class="radio-inline"><input name="public${virtual.roomId}" type="radio" value="1" roomId="${virtual.roomId}"<c:if test="${virtual.roomPublic == 1}"> checked="checked"</c:if>>공개</label>
							<label class="radio-inline"><input name="public${virtual.roomId}" type="radio" value="0" roomId="${virtual.roomId}"<c:if test="${virtual.roomPublic == 0}"> checked="checked"</c:if>>비공개</label>
						</div>
					</div>
				</div>
			</c:forEach>
			<a class="btn btn-default" href="../newRoom?roomPublic=0">모델링 작성</a>
		</div>
	</div>
	<footer class="col-sm-12">푸터</footer>
</div> --%>	
	
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
		$(function () {
			$("input[name^=public]:radio").change(function (){
				var roomId = $(this).attr("roomId");
				var value = $(this).val();
				$.ajax({
					url:"../changeRoomPublic?roomId="+roomId+"&roomPublic="+value,
					type:"GET",
					success:function(data) {
						if(data == -1) {
							alert("공개여부 변경에 실패하였습니다.");
							$("input[name=public"+roomId+"]").filter("[value="+(1-value)+"]").prop("checked", true);
						}
					},
					error:function(e) {
						console.log(e);
						alert("공개여부 변경 중 오류가 발생하였습니다.");
						$("input[name=public"+roomId+"]").filter("[value="+(1-value)+"]").prop("checked", true);
					}
				});
			});
		});
		
		function unConfirm(index, requestMemberId, roomId) {			
			$.ajax({
				url:"../unconfirm?requestMemberId="+requestMemberId+"&roomId="+roomId,
				type:"GET",
				success:function(data) {
					if(data) {
						$("#req"+index).remove();
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
		
		function deleteRoom(roomId) {
			$.ajax({
				url:"../deleteRoom?roomId="+roomId,
				type:"GET",
				success:function(data) {
					if(data) {
						$("#room"+roomId).remove();
						$("#virtual"+roomId).remove();
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
	</script>	
</body>
</html>