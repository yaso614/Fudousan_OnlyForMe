<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
	
	<title>Item Modify Page</title>
	 <!-- 부트스트랩 -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
	
	<link rel="stylesheet" href="<c:url value="/resources/css/templete.css"/>" >
	<!-- font awesome -->
    <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.min.css"/>" media="screen" title="no title">
    <!-- Custom style -->
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" media="screen" title="no title">

	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/itemForm.css"/>">

	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/loading.css"/>"/>
	
		
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="<c:url value="/resources/js/three.js"/>"></script>
	<script src="<c:url value="/resources/js/TDSLoader.js"/>"></script>
	<script src="<c:url value="/resources/js/OrbitControls.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/itemForm.js"/>"></script>

	<script type="text/javascript">
	
	/**
	 * 화면 데이터를 Blob으로 변환
	 * @param dataURI
	 * @returns
	 */
	function dataURItoBlob(dataURI)
	{
	    var byteString = atob(dataURI.split(',')[1]);

	    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

	    var ab = new ArrayBuffer(byteString.length);
	    var ia = new Uint8Array(ab);
	    for (var i = 0; i < byteString.length; i++)
	    {
	        ia[i] = byteString.charCodeAt(i);
	    }

	    var bb = new Blob([ab], { "type": mimeString });
	    
	    return bb;
	}
	
	function formsubmit() {
		if ($("#name").val() == null || $("#name").val() == "") {
			$("#name").focus();
			return false;
		}
		if ($("#type").val() == null || $("#type").val() == "") {
			$("#type").focus();
			return false;
		}
		if ($("#model").val() == null || $("#model").val() == "") {
			$("#model").focus();
			return false;
		}
		
		// 3D 캡쳐
		var strMime = "image/jpeg";
		var imgData = renderer.domElement.toDataURL(strMime);
    
   		var blob = dataURItoBlob(imgData);
   		var formData = new FormData();
    	formData.append("file", blob, $("#itemId").val());

        $.ajax({
        	
    		url:"preview",
    		type:"POST",				
    		data:formData,
    		processData: false,
    	    contentType: false,
    		dataType:"text",	
    		success:function(data) {
    			
    			if(data != null) {
    				
    			} else {
    				
    				alert("미리보기 저장에 실패하였습니다.");
    				
    			}
    			
    		},
    		error:function(e) {
    			
    			console.log(e);
    			alert("미리보기 저장 중 오류가 발생하였습니다.");
    			
    		}
    	});
    	
		return true;
	}
	</script>	
</head>
<body>
	<div id="blocker">
		<div>
			<img src="<c:url value="/resources/image/loading.svg"/>" class="ld ld-spin"/>
		</div>
	</div>
	
	<!-- login modal  -->
 	<%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>
	
	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-2 sidenav"></div>
			<div class="col-sm-8 text-left">
				<h1 >
					<span>Item Add Form</span>
					<small>basic</small>
				</h1>
				<hr>
				<div class="col-sm-12 text-left">
					<form id="itemform" action="moditem" method="post" enctype="multipart/form-data" onsubmit="return formsubmit()" onreset="formreset()">
						<%@ include file="./itemForm.jsp" %>
					</form>
				</div>
			</div>
			<div class="col-sm-2 sidenav"></div>
		</div>		
	</div>	
	<br>		
					
	

	<!-- footer -->
	<%@include file="/WEB-INF/views/include/footer.jsp" %>


	
	<!-- bootstrap -->
	<script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>
	
	<!-- join.js -->
	<script src="<c:url value="/resources/js/join.js"></c:url>"></script>
	
	<!-- login.js -->
	<script src="<c:url value="/resources/js/login.js"></c:url>"></script>
	

	<!-- 다국어 처리 -->
	<script src="../resources/js/cookie.js"></script>
	<script src="../resources/js/translation.js"></script>
</body>
</html>