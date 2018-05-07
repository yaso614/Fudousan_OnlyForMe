<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >

<div class="container-fluid text-center">    
  <div class="row content">
  	<div class="col-sm-2 sidenav"></div>  
    <div class="col-sm-8 text-left">
     	<br>
 		<div role="tabpanel">
		  <!-- Nav tabs -->
		  <ul class="nav nav-tabs" role="tablist">
		    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Member Request List</a></li>
		    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Confirm 3D List</a></li>
		    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">My list</a></li>
		  
		  </ul>
		
		  <!-- Tab panes -->
		  <div class="tab-content">
		    <div role="tabpanel" class="tab-pane active" id="home">
		    <!-- 사용자 요청 리스트 Start -->
				<h1 data-lang="106">사용자 요청 리스트</h1>
				<!-- result -->      
				<table class="table">
					<thead class="table">
						<tr>
							<th data-lang="117">Estate Id</th>
							<th data-lang="116">Estate Name</th>
							<th data-lang="118">Start Interior</th>
							<th data-lang="4">Cancel</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="advice" varStatus="status" items="${adviceList}">
							<tr id="req${status.index}">
								<td>${advice.room.estate.estateId }</td>
								<td>${advice.room.estate.estateName }</td>
								<td><button class="btn btn-success" data-lang="118">인테리어 시작</button></td>
								<td><button class="btn btn-danger" type="button"onclick = "unConfirm(${status.index}, ${advice.requestMemberId}, ${advice.room.roomId})"><span data-lang="4"></span></button></td>
							</tr>
						
					</c:forEach>
					</tbody>
				</table>
		    </div>
		    <div role="tabpanel" class="tab-pane" id="profile">
					<!-- 3D 작성 매물 확인  Start -->
		  			<h1 data-lang="94">3D 작성 매물 확인</h1>
			
					<!-- result -->
					<table class="table">
						<thead class="table">
							<tr>
								<th data-lang="117">Estate Id</th>
								<th data-lang="116">Estate Name</th>
								<th data-lang="115">Design</th>
								<th data-lang="110">Snap Shot</th>
								<th data-lang="112">Modify</th>
								<th data-lang="113">Delete</th>
								<th data-lang="114">Open/Close</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="room" varStatus="status" items="${realRoomList}">
								<%-- <div id="room${room.roomId}" class="col-sm-12"></div> --%>
								<tr id="room${room.roomId}">
									<td><a href="<c:url value="/estate/detailedinfomation?id=EstateId:${room.estate.estateId}"/>">${room.estate.estateId}</a></td>
									<td><a href="<c:url value="/estate/detailedinfomation?id=EstateId:${room.estate.estateId}"/>">${room.estate.estateName }</a></td>
									<td><a class="btn btn-default" href="../newRoom?estateId=${room.estate.estateId}&roomPublic=0" data-lang="144">새로 꾸미기</a></td>
									<td><img style="width:100px" src="<c:url value='${room.snapshot}'/>"></td>
									<td><a class="btn btn-default" href="../roomPage?roomId=${room.roomId}" data-lang="112">수정</a></td>
									<td><button class="btn btn-default" type="button" onclick="deleteRoom(${room.roomId})"><span data-lang="93"></span></button></td>
									<td>
										<p class="radio-inline"><input name="public${room.roomId}" type="radio" value="1" roomId="${room.roomId}"<c:if test="${room.roomPublic == 1}"> checked="checked"</c:if>><span data-lang="119">공개</span></p>
										<p class="radio-inline"><input name="public${room.roomId}" type="radio" value="0" roomId="${room.roomId}"<c:if test="${room.roomPublic == 0}"> checked="checked"</c:if>><span data-lang="120">비공개</span></p>
									</td>
								</tr>
								
							</c:forEach>
						</tbody>
					</table>		    
			</div>
		    <div role="tabpanel" class="tab-pane" id="messages">
		    	<!-- 내가 만든 리스트 Start -->
	   			<h1 data-lang="107">내가 만든 리스트</h1>
	   			<div class="text-right">
					<a class="btn btn-info" data-lang="108" href="../newRoom?roomPublic=0">모델링 작성</a>
				</div>
				<!-- result -->
				<table class="table">
					<thead class="table">
						<tr>
							<th data-lang="109">Room Id</th>
							<th data-lang="110">Snap Shot</th>
							<th data-lang="111">Wall Modify</th>
							<th data-lang="112">Modify</th>
							<th data-lang="113">Delete</th>
							<th data-lang="114">Open / Close</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="virtual" varStatus="status" items="${notRealRoomList}">
							<tr id="virtual${virtual.roomId}">
							<%-- <div id="virtual${virtual.roomId}" class="col-sm-12"></div> --%>
								<td>${virtual.roomId}</td>
								<td><img style="height: 100px; width: auto;" src="<c:url value="${virtual.snapshot}"/>"></td>
								<td><a class="btn btn-default" href="../wall/wallPage?roomId=${virtual.roomId}" data-lang="111">벽 수정</a></td>
								<td><a class="btn btn-default" href="../roomPage?roomId=${virtual.roomId}" data-lang="112">수정</a></td>
								<td>
									<button class="btn btn-default" type="button" onclick="deleteRoom(${virtual.roomId})"><span data-lang="113"></span></button>
								</td>
								<td>
									<p class="radio-inline"><input name="public${virtual.roomId}" type="radio" value="1" roomId="${virtual.roomId}"<c:if test="${virtual.roomPublic == 1}"> checked="checked"</c:if>><span data-lang="119">공개</span></p>
									<p class="radio-inline"><input name="public${virtual.roomId}" type="radio" value="0" roomId="${virtual.roomId}"<c:if test="${virtual.roomPublic == 0}"> checked="checked"</c:if>><span data-lang="120">비공개</span></p>
								</td>
							
							</tr>
						</c:forEach>
					</tbody>
				</table>
		    </div>
		</div>
	</div> 		
    </div> 
	<div class="col-sm-2 sidenav">
	</div>
</div> 
</div>

