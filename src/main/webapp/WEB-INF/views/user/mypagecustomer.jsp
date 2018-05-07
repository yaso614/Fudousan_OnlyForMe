<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
	 
	 
    <!-- css -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="resources/css/3dmodel.css" rel="stylesheet">
    <link href="resources/css/mypagecustomer.css" rel="stylesheet">
    <link href="resources/css/templete.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>고객 페이지</title>
<body >
	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>

	<!-- main page  -->
	<%@include file="/WEB-INF/views/include/mypagecustomermaininclude.jsp" %>
	
	<!-- footer  -->
	<%@include file="/WEB-INF/views/include/footer.jsp" %>
	

	
	<!-- include js List -->
	<script src="/fudousan/resources/js/jquery-3.3.1.js"></script>
	<script src="/fudousan/resources/js/login.js"></script>
	<script src="/fudousan/resources/js/bootstrap.min.js"></script>
	
	<!-- 다국어 처리 -->
	<script src="/fudousan/resources/js/cookie.js"></script>
	<script src="/fudousan/resources/js/translation.js"></script>

	
	<script>
	function roomSearch(){
		var roomSearch = document.getElementById("roomSearch").value; //방검색
		var memberId = document.getElementById("memberId").value; //사용자아이디
		if(roomSearch == ''){
			alert("검색할 방의 이름을 입력해주세요");
			$('#roomSearch').focus();
			return false;
		}
		 /* location.href ="searchMyRoom?roomSearch="+roomSearch+"&memberId="+memberId; */
		$.ajax({
		url : "/fudousan/searchMyRoom",
		type : "get",
		data : {
			roomSearch : roomSearch,
			memberId : memberId
		},
		success : function(srlist){
			alert('검색성공');
			 $('[name="home"]').remove(); 
			
			$.each(srlist,function(index,room){
				var str  = '<tr name="home">';
				str += '<td><p><a href="/fudousan/estate/detailedinfomation?id=EstateId:'+room.estate.estateId+'">';
				if (room.roomTitle == null){
					str += '이름없음';
				}
				else {
					str += room.roomTitle;
				}
				str += '</a></p></td>';
				str += '<td><a class="btn btn-info" href="/fudousan/roomPage?roomId='+room.roomId+'&roomPublic=0">내집꾸미기</a></td>';
				str += '<td><a class="btn btn-warning" href="/fudousan/deletionLogical?memberId=${sessionScope.loginId}&roomId='+room.roomId+'">논리삭제</a></td>';
				str += '</tr>'; 
				
				$('#hhh').append(str);
			})
			
			
		},
		error : function(e){
			alert(JSON.stringify(e));
			alert('검색 실패');
		}
		}); //ajax 끝 
		
		 
		
		
	}
	
	
	
	function favoriteSearch(){
		var favoSearch = document.getElementById("favoSearch").value; //방검색
		var memberId = document.getElementById("memberId").value; //사용자아이디
		if(favoSearch == ''){
			alert("검색할 찜한 방의 이름을 입력해주세요");
			$('#favoSearch').focus();
			return false;
		}
		/*  location.href ="searchFavorite?favoSearch="+favoSearch+"&memberId="+memberId;  */
		
	 	alert('검색 실행');
		$.ajax({
		url : "/fudousan/searchFavorite",
		type : "get",
		data : {
			favoSearch : favoSearch,
			memberId : memberId
		},
		success : function(flist){
			$('[name="favorite"]').remove();
			
			$.each(flist,function(index,favorite){
				var str  = '<div class="col-sm-12 form-group" name="favorite">';
				str += '<p><a href="/fudousan/estate/detailedinfomation?id=EstateId:'+favorite.estate.estateId+'">'+favorite.estate.estateName+'</a></p>';
				str += '<input type="hidden" value="'+favorite.estate.estateId+'" id="favo" name="favo">';
				str += '<button><a href="/fudousan/estate/detailedinfomation?id=EstateId:'+favorite.estate.estateId+'">매물상세정보</a></button>';
				str += '</div>';
				
				$('#profile').append(str);
			})
			
			alert('검색성공');
		},
		error : function(e){
			alert(JSON.stringify(e));
			alert('검색 실패');
		}
		}); //ajax 끝 
	}
	
	function sayonara(){
		var realsayo = confirm("정말로 의뢰를 거절하시겠소?");
		if(realsayo){
			var client = document.getElementById("client").value;
			var accepter = document.getElementById("accepter").value;
			var roomNum = document.getElementById("roomNum").value;
			location.href = "cancelAdviceTrue?customer="+client+"&interior="+accepter+"&roomNum="+roomNum;
		}
		return false;
	}
	
	
	function goDetail(){
		var favo = document.getElementById("favo").value;
		alert(favo);
		location.href ="detailedinfomation?id=EstateId:"+favo;
	}
	
	</script>
	
	
</body>
</html>