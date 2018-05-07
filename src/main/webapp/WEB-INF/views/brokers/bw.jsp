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
    <title>공인중개사 입력</title>

    <!-- 부트스트랩 -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
   
   
    <!-- font awesome -->
    
    <link rel="stylesheet" href="../resources/css/font-awesome.min.css" media="screen" title="no title" charset="utf-8">
    
    <!-- Custom style -->
    <link rel="stylesheet" href="../resources/css/style.css" media="screen" title="no title" charset="utf-8">
   
   <!-- login.js -->
	<script src="<c:url value="/resources/js/login.js"></c:url>"></script>
   
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
     <!--   <nav class="navbar navbar-default" style="margin: auto 0">  -->
        <%-- <div class="container-fluid">
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
			<h1 data-lang="116"> 物件登録</h1>
		</div>
			<div class="col-md-6 col-md-offset-3">
	       
			<form role="form" action="estatewrite" method="post" id="estateForm">
				<div class="form-group">
					<label for="inputMunicipality" data-lang="121">매물이름</label>
					<input type="text" class="form-control" id="inputestateName" data-lang="121" placeholder="매물이름" name="estateName">
				</div>
	
				<div class="form-group">
					<label for="inputType" data-lang="122">거래종류</label>
	             
					<select name='transTypeId' class="form-control" data-live-search="true" >
						<c:set var="transTypeId"/>
						<option value='0' selected data-lang="16">-- 選択 --</option>
						<option value='1' data-lang="123">マンション</option>
						<option value='2' data-lang="124">住宅</option>
						<option value='3' data-lang="125">宅地(土地)</option>
					</select>
				</div>
				
				<div class="form-group">
					<label for="inputPrefecture" data-lang="123">가격</label>
					<input type="text" class="form-control" id="inputPrefecture" data-lang="123" placeholder=" 가격" name="price">
				</div>
	
				<div class="form-group">
					<label for="inputRegion"data-lang="127">지역이름</label>
					
					<!-- Localname = 지역이름 -->
					<select name='prefecture' data-live-search="true"  class="form-control">
	
						<option value='0' selected="selected" data-lang="28"></option>
						<!-- 홋카이도 -->
						<option value='北海道' data-lang="29">北海道</option>
						<!-- 아오모리현 -->
						<option value='青森県' data-lang="30">青森県</option>
						<!-- 이와테현 -->
						<option value='岩手県' data-lang="31">岩手県</option>
						<!-- 미야기현 -->
						<option value='宮城県' data-lang="32">宮城県</option>
						<!-- 아키타현 -->
						<option value='秋田県' data-lang="33">秋田県</option>
						<!-- 야마가타현 -->
						<option value='山形県' data-lang="34">山形県</option>
						<!-- 후쿠시마현-->
						<option value='福島県' data-lang="35">福島県</option>
						<!-- 이바라키현 -->
						<option value='茨城県' data-lang="36">茨城県</option>
						<!-- 토치기현 -->
						<option value='栃木県' data-lang="37">栃木県</option>
						<!-- 군마현 -->
						<option value='群馬県' data-lang="38">群馬県</option>
						<!-- 사이타마현 -->
						<option value='埼玉県' data-lang="39">埼玉県</option>
						<!-- 치바현 -->
						<option value='千葉県' data-lang="40">千葉県</option>
						<!--  도쿄도 -->
						<option value='東京都' data-lang="41">東京都</option>
						<!-- 가나가와현 -->
						<option value='神奈川県' data-lang="42">神奈川県</option>
						<!-- 니가타현 -->
						<option value='新潟県' data-lang="43">新潟県</option>
						<!-- 도야마현 -->
						<option value='富山県' data-lang="44">富山県</option>
						<!-- 이시카와현 -->
						<option value='石川県' data-lang="45">石川県</option>
						<!-- 후쿠이현 -->
						<option value='福井県' data-lang="46">福井県</option>
						<!-- 야마나시현 -->
						<option value='山梨県' data-lang="47">山梨県</option>
						<!-- 나가노현 -->
						<option value='長野県' data-lang="48">長野県</option>
						<!-- 기후현 -->
						<option value='岐阜県' data-lang="49">岐阜県</option>
						<!-- 스지오카현 -->
						<option value='静岡県' data-lang="50">静岡県</option>
						<!-- 아이치현 -->
						<option value='愛知県' data-lang="51">愛知県</option>
						<!-- 미에현 -->
						<option value='三重県' data-lang="52">三重県</option>
						<!-- 시가현 -->
						<option value='滋賀県' data-lang="53">滋賀県</option>
						<!-- 교토부 -->
						<option value='京都府' data-lang="54">京都府</option>
						<!-- 오사카부 -->
						<option value='大阪府' data-lang="55">大阪府</option>
						<!-- 효고현 -->
						<option value='兵庫県' data-lang="56">兵庫県</option>
						<!-- 나라현 -->
						<option value='奈良県' data-lang="57">奈良県</option>
						<!-- 와카야마현 -->
						<option value='和歌山県' data-lang="58">和歌山県</option>
						<!-- 돗토리현 -->
						<option value='鳥取県' data-lang="59">鳥取県</option>
						<!-- 시마네현 -->
						<option value='島根県' data-lang="60">島根県</option>
						<!-- 오카야마현 -->
						<option value='岡山県' data-lang="61">岡山県</option>
						<!-- 히로시마현 -->
						<option value='広島県' data-lang="62">広島県</option>
						<!-- 야마구치현 -->
						<option value='山口県' data-lang="63">山口県</option>
						<!-- 도쿠시마현 -->
						<option value='徳島県' data-lang="64">徳島県</option>
						<!-- 가가와현 -->
						<option value='香川県' data-lang="65">香川県</option>
						<!-- 에히메현 -->
						<option value='愛媛県' data-lang="66">愛媛県</option>
						<!-- 고치현 -->
						<option value='高知県' data-lang="67">高知県</option>
						<!-- 후쿠오카현 -->
						<option value='福岡県' data-lang="68">福岡県</option>
						<!-- 사가현 -->
						<option value='佐賀県' data-lang="69">佐賀県</option>
						<!-- 나가사키현 -->
						<option value='長崎県' data-lang="70">長崎県</option>
						<!-- 구마모토현 -->
						<option value='熊本県' data-lang="71">熊本県</option>
						<!-- 오이타현 -->
						<option value='大分県' data-lang="72">大分県</option>
						<!-- 미야자키현 -->
						<option value='宮崎県' data-lang="73">宮崎県</option>
						<!-- 가고시마현 -->
						<option value='鹿児島県' data-lang="74">鹿児島県</option>
						<!-- 오키나와현 -->
						<option value='沖縄県' data-lang="75">沖縄県</option>
					</select>
				</div>
				
				<div class="form-group">
					<label for="inputRegion" data-lang="128">시구 도촌 </label>
					<!-- Localname = 지역이름 -->
					<input type="text" name='municipality' data-live-search="true"  class="form-control">
	           
					<label for="inputRegion" data-lang="129">시구 도촌 코드</label>
					<input type="text" name="municipalitycodeId" data-live-search="true"  class="form-control">
				</div>
	         
				<div class="form-group">
					<label for="inputDistrictName" data-lang="130">지역구</label>
					<input type="text" class="form-control" id="inputDistrictName"  name="districtname">
				</div>
	           
				<div class="form-group">
					<label for="inputNearestStation" data-lang="131"> 나머지 주소 </label>
					<input type="text" class="form-control" id="inputNearestStation"data-lang="131"  placeholder="나머지 주소" name="address">
				</div>
	           
				<div class="form-group">
					<label for="inputNearestStation" data-lang="132"> 가까운역</label>
					<input type="text" class="form-control" id="inputNearestStation" data-lang="132" placeholder="제일 가까운 역 : 명칭 " name="neareststation">
				</div>
	        
				<div class="form-group">
					<label for="inputTimeToNearestStation" data-lang="133"> 역까지 걸리는 시간(분)</label>
					<input type="text" class="form-control" id="inputTimeToNearestStation" data-lang="133" placeholder="제일 가까운역 거리(분)" name="timetoneareststation">
				</div>
	           
				<!-- <div class="form-group">
					<label for="inputTradePrice" data-lang="108"> 取引価格（総額）</label>
					<input type="text" class="form-control" id="inputTradePrice" placeholder="거래 가격(총액)" name="tradeprice">
				</div>
	           
				<div class="form-group">
					<label for="inputPricePerUnit" data-lang="109"> 坪単価</label>
					<input type="text" class="form-control" id="inputPricePerUnit" placeholder="평단가" name="priceperunit">
				</div> -->

				<div class="form-group">
					<label for="inputFloorPlan" data-lang="136"> 구조</label>
					<input type="text" class="form-control" id="inputFloorPlan" data-lang="136" placeholder="구조 " name="floorplan">
				</div>
	           
			<!-- 	<div class="form-group">
					<label for="inputArea"> 평당 미터 가격</label>
					<input type="text" class="form-control" id="inputArea" placeholder="면적(평방 미터)" name="area">
				</div>
	           
				<div class="form-group">
					<label for="inputUnitPrice"> 取引価格（平方メートル単価）</label>
					<input type="text" class="form-control" id="inputUnitPrice" placeholder="거래 가격(평방 미터 단가 )" name="unitprice">
				</div>
	           
				<div class="form-group">
					<label for="inputLandShape">土地の形状 </label>
					<input type="text" class="form-control" id="inputLandShape" placeholder="토지의 형상" name="landshape">
				</div>
	           
				<div class="form-group">
					<label for="inputTotalFloorArea">延べ面積（㎡） </label>
					<input type="text" class="form-control" id="inputTotalFloorArea" placeholder="연면적 (㎡)" name="totalfloorarea">
				</div> -->
	           
				<div class="form-group">
					<label for="inputBuildingYear" data-lang="138">건축년도</label>
					<input type="text" class="form-control" id="inputBuildingYear" data-lang="138" placeholder="건축 년도 " name="buildingyear" data-lang="111">
				</div>
	           
				<!-- <div class="form-group">
					<label for="inputStructure" data-lang="112">건물의 구조 </label>
					<input type="text" class="form-control" id="inputStructure" placeholder="건물의 구조 " name="structure">
				</div> -->
	           
				<div class="form-group">
					<label for="inputUse" data-lang="139">용도</label>
					<input type="text" class="form-control" id="inputUse" data-lang="139" placeholder="용도" name="use">
				</div>
	       
				<div class="form-group">
					<label for="inputCoverageRatio" data-lang="140">건폐율（％）</label>
					<input type="text" class="form-control" id="inputCoverageRatio" data-lang="140" placeholder="건폐율 (%)" name="coverageratio">
				</div>
	           
				<div class="form-group">
					<label for="inputFloorAreaRatio" data-lang="141">용적률（％）</label>
					<input type="text" class="form-control" id="inputFloorAreaRatio" data-lang="141" placeholder="용적률 (%)" name="floorarearatio">
				</div>
				
				<div class="form-group text-center">
					<button type="button" class="btn btn-info" onclick="formcheck()"data-lang="142">登録完了<i class="fa fa-check spaceLeft"></i></button>
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
		function formcheck() {
			var municipalitycodeId = $('[name="municipalitycodeId"]').val();
			
			if (municipalitycodeId == ""){
				alert('지역 코드명을 입력해 주세요.');
			}
			else {
				$.ajax({
					url : "codecheck",
					type : "get",
					data : {municipalitycodeId:municipalitycodeId},
					dataType : "text",
					success: function(result){
						if (result == ''){
							alert("서비스되지 않는 지역코드입니다");
						}
						else {
							$('#estateForm').submit();
						}
					},
					error: function(err){
						console.log(err);
					}
				});
			}
		}
	</script>

	<script src="<c:url value="/resources/js/cookie.js"/>"></script>
	<script src="<c:url value="/resources/js/translation.js"/>"></script>
</body>
</html>