<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<div class="container-fluid text-center"  >



	<div class="row content">
		<div class="col-sm-2 sidenav"></div>
		<div class="col-sm-8 text-left">
			<div role="tabpanel">
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab" data-lang="147">Estate Management</a></li>
					<li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" data-lang="148">3D Modeling Management</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="home">
						<!--  매물 관리 Start -->
						<h1 data-lang="147">매물 관리</h1>
						<div class="text-right">
							<button type="button"  data-lang="142"  class="btn btn-info" data-lang="142"
								onclick="location.href='/fudousan/bw'" /></button>
						</div>

						<!-- result -->
						<div class="text-left">
							<table class="table">
								<thead class="table">
									<tr>
										<th data-lang="117">Estate Id</th>
										<th data-lang="116">Estate Name</th>
										<th data-lang="145">Estate Modify</th>
										<th data-lang="93">Estate Delete</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="estate" items="${select}">
										<tr>
											<td><a href="./estate/detailedinfomation?id=EstateId:${estate.estateId }">${estate.estateId}</a></td>
											<td><a href="./estate/detailedinfomation?id=EstateId:${estate.estateId }">${estate.estateName}</a></td>
											<td><button type="button"  data-lang="145" class="btn btn-info" onclick="location.href='/fudousan/bc?estateId=${estate.estateId}'"></button></td>
											<td><button type="button" data-lang="93" class="btn btn-danger" onclick="location.href='deleteEntry?agencyId=${agencyId}&estateId=${estate.estateId}'"></button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="profile">
						<!-- 매물 3D 모델링 관리  Start -->
						<h1 data-lang="148">매물 3D 모델링 관리</h1>
						<div class="text-right">
							<button type="button" data-lang="148" class="btn btn-info" data-toggle="modal" data-target="#estateListModal">매물 모델링 관리</button>
						</div>
						<!-- result -->
						<table class="table">
							<thead class="table">
								<tr>
									<th data-lang="117">Estate Id</th>
										<th data-lang="116">Estate Name</th>
									<th data-lang="110">Snap Shot</th>
									<th data-lang="112">Modify</th>
									<th data-lang="93">Delete</th>
									<th data-lang="114">Open/Close</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="room" items="${roomList }">	
									<tr id="room${room.roomId}">
										<td><c:if test="${!empty room.estate.estateId}"><a href="<c:url value="estate/detailedinfomation?id=EstateId:${room.estate.estateId}"/>">${room.estate.estateId}</a></c:if></td>
										<td><c:if test="${!empty room.estate.estateId}"><a href="<c:url value="estate/detailedinfomation?id=EstateId:${room.estate.estateId}"/>">${room.estate.estateName}</a></c:if></td>
										<td><img style="width: 350px" class="col-sm-12" src="<c:url value="${room.snapshot}"/>"></td>
										<td><a class="btn btn-info" data-lang="112" href="./roomPage?roomId=${room.roomId}"></a></td>
										<td><button type="button" class="btn btn-danger" data-lang="93" onclick="roomDeleteListener(${room.roomId})">삭제</button></td>
										<td>
											<p class="radio-inline"><input name="public${room.roomId}" type="radio" value="1" roomId="${room.roomId}"<c:if test="${room.roomPublic == 1}"> checked="checked"</c:if>><span data-lang="119">공개</span></p>
											<p class="radio-inline"><input name="public${room.roomId}" type="radio" value="0" roomId="${room.roomId}"<c:if test="${room.roomPublic == 0}"> checked="checked"</c:if>><span data-lang="120">비공개</span></p>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- 신청중인 부동산 업자 리스트 End -->
				</div>
			</div>
		</div>
		<div class="col-sm-2 sidenav"></div>
	</div>
</div>