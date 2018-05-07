<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >


<div class="container-fluid text-center">    
  <div class="row content">
  	<div class="col-sm-3 sidenav">

  	<div class="well">
			<h6 data-lang="96" >인테리어 업자에게 도움 요청</h6>
			<c:forEach var="helper" items="${alist}">
				<p><c:out value="${helper.requestedMemberId}"/><span data-lang="101">님에게 도움을 요청하셨습니다.</span></p>
			</c:forEach>
			<!-- Clear -->
		<div style="clear: both;"></div>
		</div>

  	
  	</div>
  
  	<div class="col-sm-6">
  		<br>
  		<div class="col-sm-12 text-left" >
  			<div role="tabpanel">

  			<!-- Nav tabs -->
  			<ul class="nav nav-tabs" role="tablist">
  				<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">3D</a></li>
    			<li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Favorite</a></li>
  			</ul>
  
  			<!-- Tab panes -->
  			<div class="tab-content">
    			<div role="tabpanel" class="tab-pane active" id="home">
    				<h1 data-lang="94">3D 작성 매물 확인</h1>
					<!-- search button -->
					<div id="custom-search-input">
            			<div class="input-group col-md-12"  name="seachedRoom">
                			<input type="text" class="  search-query form-control" placeholder="Search" id ="roomSearch" name="roomSearch" />
                			<span class="input-group-btn">
	                    		<button class="btn btn-danger" type="button" onclick="roomSearch()" >
	                        		<span class=" glyphicon glyphicon-search"></span>
	                        		<input type="hidden" id="memberId" name="memberId" value=${sessionScope.loginId }>
	                    		</button>
                			</span>
            			</div>
        			</div>
        			<br>
        			<!-- result -->      
        			<table class="table" >
        			<thead>
        				<tr>
        					<th data-lang="8">Name</th>
        					<th data-lang="92">Create</th>
        					<th data-lang="93">Delete</th>
        				</tr>
       				</thead>
        				<tbody id="hhh">
        					<c:forEach var="room" items="${rlist}">
	        					<tr name="home">
	        						<td>
	        						<p>
	        						<c:if test="${!empty room.estate.estateId}"><a href="<c:url value="/estate/detailedinfomation?id=EstateId:${room.estate.estateId}"/>"></c:if>
									<c:choose>
		        							<c:when test="${room.roomTitle eq null}"><span data-lang="311">이름없음</span></c:when>
		        							<c:otherwise>${room.roomTitle}</c:otherwise>
        							</c:choose>
									</a></p></td>
	        						<td><a class="btn btn-info" data-lang="98" href="<c:url value="/roomPage?roomId=${room.roomId}&roomPublic=0"/>">내집꾸미기</a></td>
	        						<td><a class="btn btn-warning" data-lang="99" href="<c:url value="/deletionLogical?memberId=${sessionScope.loginId}&roomId=${room.roomId}"/>">논리삭제</a></td>
	        					</tr>
        					</c:forEach>
        				</tbody>
        			</table>
    			</div>
   				<div role="tabpanel" class="tab-pane" id="profile">
    				<h1 data-lang="95">찜한 매물 보기</h1>
					<!-- search button -->
					<div id="custom-search-input">
            			<div class="input-group col-md-12">
                			<input type="text" class="  search-query form-control" placeholder="Search"  name="favoSearch" id="favoSearch" />
                			<span class="input-group-btn">
                    			<button class="btn btn-danger" type="button" onclick="favoriteSearch()" >
                        			<span class=" glyphicon glyphicon-search"></span>
                        			<input type="hidden" id="memberId" name="memberId" value=1>
                    			</button>
                			</span>
            			</div>
        			</div>
					<hr>
					<!-- result -->
					<c:forEach var="favorite" items="${flist}">
						<div class="col-sm-12 form-group" name="favorite">
							<p><a href="/fudousan/estate/detailedinfomation?id=EstateId:${favorite.estate.estateId}">${favorite.estate.estateName}</a></p>
							<button><a href="<c:url value="/estate/detailedinfomation?id=EstateId:${favorite.estate.estateId}"/>" data-lang="103">매물상세정보</a></button>
							<input type="hidden" value="${favorite.estate.estateId}" id="favo" name="favo" >
						</div>
						
					</c:forEach>
					
    			</div>
    		
  			</div>
  		
			</div>
  			
  			
  		</div>
  		<div style="clear: both;"></div>
  	
  	
  	</div>
  	

    <!-- side nav button Start -->
    <div class="col-sm-3 sidenav">
		
		
		<div class="well">
			<h6 data-lang="97">인테리어 업자 승인 여부</h6>
			<c:forEach var="helpRes" items="${rclist}">
			 		<div class="col-sm-12 form-group">
			 			<p><c:out value="${helpRes.requestedMemberId}"/><span data-lang="102">님이 당신의 요청을 받아들였습니다.</span></p>
						<button data-lang="100"  id="cancel" name="cancel" onclick="sayonara()"></button>
						<input type="hidden" value="${helpRes.requestMemberId }" id="client" name="client">
						<input type="hidden" value="${helpRes.requestedMemberId }" id="accepter" name="accepter">
						<input type="hidden" value="${helpRes.room.roomId }" id="roomNum" name="roomNum">
			 		</div>
			</c:forEach>
		</div>
    </div>
  </div>
</div>
<div style="clear: both;"></div>

<script src="<c:url value="/resources/js/cookie.js"/>"></script>
<script src="<c:url value="/resources/js/translation.js"/>"></script>
