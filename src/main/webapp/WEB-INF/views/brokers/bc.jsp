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
    <title>매물 수정 </title>

    <!-- 부트스트랩 -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
	
	
	 <!-- font awesome -->
    
    <link rel="stylesheet" href="../resources/css/font-awesome.min.css" media="screen" title="no title" charset="utf-8">
    
    <!-- Custom style -->
    <link rel="stylesheet" href="../resources/css/style.css" media="screen" title="no title" charset="utf-8">
	
	
	
	<!-- style -->
	<style type="text/css">
	  /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 91%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
     
       
      }
   
	</style>
  </head>
  <body>
  <%@include file="/WEB-INF/views/include/loginmodal.jsp" %> 
  <!-- estate list modal  -->
  <%@include file="estateListModal.jsp" %>
 	
	<!-- header -->
	<%@include file="/WEB-INF/views/include/header.jsp" %>
  
  
  <!-- 로그인 모달 시작 -->
  <div class="modal fade" id="loginModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Login</h4>
				</div>
				
				<div class="modal-body">
					Email <input type="text" id="memberEmail" name="memberID">
					Password <input type="password" id="password" name="password">
					<button type="button" id="loginBtn">Login</button>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 로그인 모달 끝 -->
   <!-- Header -->
		<%--  <nav class="navbar navbar-default" style="margin: auto 0"> 
		  <div class="container-fluid">
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="../fudousan" style="margin-bottom: 10px;">
		      	<img alt="Fudousan" src="resources/image/logo2.png">
		      </a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		     
		    
		      <ul class="nav navbar-nav navbar-right" style="margin-top: 8px;">
		       <c:if test="${sessionScope.loginEmail == null}">
					<li id="loginNameTag"></li>
					<li id="loginAtag"><a data-toggle="modal" href="#loginModal">Login</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Join <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="join/join">Customer Join</a></li>
							<li class="divider"></li>
							<li><a href="join/agencyjoin">Agency Join</a></li>
						</ul>
					</li>
				</c:if>
				<c:if test="${sessionScope.loginEmail != null}">
					<li><a>${sessionScope.loginMemberName}, Welcome!</a></li>
					<li><a href="/fudousan/bw" data-lang="1">Logout</a></li>
				</c:if>
		    
		        
		      </ul>
		    </div><!-- /.navbar-collapse -->
		  </div><!-- /.container-fluid --> --%>
		 </nav> 
   
 
 <!--  join form  -->
 
 <article class="container">
        <div class="page-header">
          <h1 data-lang="145" > 物件修整 </h1>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <form role="form" action="updateByIds" method="post" enctype="multipart/form-data">
          
          <div class="form-group">
              <label for="inputMunicipality" data-lang="121">매물이름</label>
              <input type="text" class="form-control"  value="${estate.estateName}" id="inputestateName"  name="estateName" >
            </div>
          
          <input type="hidden"   class="form-control" id="inputestateName"  name=estateId value="${estate.estateId}">
          
          
            <div class="form-group">
              <label for="inputType" data-lang="122">取引タイプ(거래종류)</label>
              
            <select name='transTypeId' class="form-control" data-live-search="true">

  			<option value='1' data-lang="124" <c:if test="${estate.transType.transTypeId == 1}"> selected </c:if>>中古マンション等</option>
  			<option value='2' data-lang="125" <c:if test="${estate.transType.transTypeId == 2}"> selected </c:if>>宅地(土地と建物)</option>
  			<option value='3' data-lang="126" <c:if test="${estate.transType.transTypeId == 3}"> selected </c:if>>宅地(土地)</option>
			</select>
            </div>
            
            
            
             <div class="form-group">
              <label for="inputPrefecture" data-lang="123">가격</label>
              <input type="text" class="form-control" id="inputPrefecture" placeholder=" 가격" name="price" value="${entry.price}">
           </div>

            <div class="form-group">

               <!-- Localname = 지역이름 -->

               <input type="hidden" value="${estate.prefecture}" data-live-search="true"  class="form-control" name ="prefecture" readonly >

            </div>
         <div class="form-group">
             
            <input type="hidden" name='municipality' data-live-search="true"  class="form-control" value="${estate.municipality}" readonly >
        
           	<input type="hidden" name="municipalitycodeId" data-live-search="true"  class="form-control" value="${estate.municipalitycode.municipalitycodeId}" readonly >
         
         	<input type="hidden" name='estateX' data-live-search="true"  class="form-control" value="${estate.estateX}" readonly>
         	<input type="hidden" name='estateY' data-live-search="true"  class="form-control" value="${estate.estateY}" readonly>
     
             
            </div>
             
      
            <div class="form-group">
             
              <input type="hidden" class="form-control" id="inputDistrictName" placeholder="지역 이름  (지구 명)" name="districtname" value="${estate.districtname}" readonly >
            </div>
            
           <div class="form-group">
            
              <input type="hidden" class="form-control" id="inputNearestStation" placeholder="나머지 주소" name="address" value="${estate.address}" readonly >
            </div>
            
            
            <div class="form-group">
              <label for="inputNearestStation" data-lang="132"> 最寄駅</label>
              <input type="text" class="form-control" id="inputNearestStation" placeholder="전제일 가까운 역 : 명칭 " name="neareststation" value="${estate.neareststation}">
            </div>
         
		<div class="form-group">
              <label for="inputTimeToNearestStation" data-lang="133"> 最寄駅の距離（分）</label>
              <input type="text" class="form-control" id="inputTimeToNearestStation" placeholder="제일 가까운역 거리(분)" name="timetoneareststation" value="${estate.timetoneareststation}">
            </div>
            
            <%--  <div class="form-group">
              <label for="inputTradePrice"> 取引価格（総額）</label>
              <input type="text" class="form-control" data-lang="108" id="inputTradePrice" placeholder="거래 가격(총액)" name="tradeprice" value="${estate.tradeprice}">
            </div>  --%>
            
           <%-- <div class="form-group">
              <label for="inputPricePerUnit"> 坪単価</label>
              <input type="text" class="form-control" id="inputPricePerUnit" placeholder="평단가" name="priceperunit" value="${estate.priceperunit}">
            </div> --%>
              
         <%--    <div class="form-group">
              <label for="inputFloorPlan"> 構造</label>
              <input type="text" class="form-control" id="inputFloorPlan" placeholder="구조 " name="floorplan" value="${estate.floorplan}">
            </div> --%>
            
           <%--  <div class="form-group">
              <label for="inputArea"> 面積（平方メートル</label>
              <input type="text" class="form-control" id="inputArea" placeholder="면적(평방 미터)" name="area" value="${estate.area}">
            </div> --%>
            
           <%--  <div class="form-group">
              <label for="inputUnitPrice"> 取引価格（平方メートル単価）</label>
              <input type="text" class="form-control" id="inputUnitPrice" placeholder="거래 가격(평방 미터 단가 )" name="unitprice" value="${estate.unitprice}">
            </div> --%>
            
            <%-- <div class="form-group">
              <label for="inputLandShape">土地の形状 </label>
              <input type="text" class="form-control" id="inputLandShape" placeholder="토지의 형상" name="landshape" value="${estate.landshape}">
            </div> --%>
            
          <%--  <div class="form-group">
              <label for="inputTotalFloorArea">延べ面積（㎡） </label>
              <input type="text" class="form-control" id="inputTotalFloorArea" placeholder="연면적 (㎡)" name="totalfloorarea" value="${estate.totalfloorarea}">
            </div>  --%>
            
            <div class="form-group">
              <label for="inputBuildingYear" data-lang="138">건축년도 </label>
              <input type="text" class="form-control" id="inputBuildingYear" placeholder="건축 년도 " name="buildingyear" value="${estate.buildingyear}">
            </div>
            
           <%-- <div class="form-group">
              <label for="inputStructure">建物の構造 </label>
              <input type="text" class="form-control" id="inputStructure" placeholder="건물의 구조 " name="structure" value="${estate.structure}">
            </div>  --%>
            
            <div class="form-group">
              <label for="inputUse" data-lang="139">用途</label>
              <input type="text" class="form-control" id="inputUse" placeholder="용도" name="use" value="${estate.use}">
            </div>
        
        <div class="form-group">
              <label for="inputCoverageRatio" data-lang="140">建ぺい率（％）</label>
              <input type="text" class="form-control" id="inputCoverageRatio" placeholder="건폐율 (%)" name="coverageratio" value="${estate.coverageratio}" >
            </div>
            
            <div class="form-group">
              <label for="inputFloorAreaRatio" data-lang="141">容積率（％）</label>
              <input type="text" class="form-control" id="inputFloorAreaRatio" placeholder="용적률 (%)" name="floorarearatio" value="${estate.floorarearatio}">
            </div>
        

           
            
            <div class="form-group text-center">
              <button type="submit" class="btn btn-info" data-lang="146">登録完了<i class="fa fa-check spaceLeft"></i></button>
              <button type="reset" class="btn btn-warning" data-lang="143">初期化<i class="fa fa-times spaceLeft"></i></button>
            </div>
          </form>
        </div>
</article>

 <!-- script -->

    <script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAlZMVBrvQGWP2QTDvf5ur7HrtEC3xlOf0 &callback=initMap">
    </script>
   

    <!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
    <script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>
    
    
<script type="text/javascript">
function bs_input_file() {
	$(".input-file").before(
		function() {
			if ( ! $(this).prev().hasClass('input-ghost') ) {
				var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
				element.attr("name",$(this).attr("name"));
				element.change(function(){
					element.next(element).find('input').val((element.val()).split('\\').pop());
				});
				$(this).find("button.btn-choose").click(function(){
					element.click();
				});
				$(this).find("button.btn-reset").click(function(){
					element.val(null);
					$(this).parents(".input-file").find('input').val('');
				});
				$(this).find('input').css("cursor","pointer");
				$(this).find('input').mousedown(function() {
					$(this).parents('.input-file').prev().click();
					return false;
				});
				return element;
			}
		}
	);
}
$(function() {
	bs_input_file();
});
</script>






    <script src="<c:url value="/resources/js/cookie.js"/>"></script>
	<script src="<c:url value="/resources/js/translation.js"/>"></script>
    
  </body>
</html>
