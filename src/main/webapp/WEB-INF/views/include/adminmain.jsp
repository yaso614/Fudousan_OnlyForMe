<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >


<div class="container-fluid text-center">    
  <div class="row content">
  	<div class="col-sm-2 sidenav">
	</div>  
  	
  	<!-- 신청중인 부동산 업자 리스트 Start -->
    <div class="col-sm-8 text-left">
	<br>
	<div role="tabpanel">
	  <!-- Nav tabs -->
	  <ul class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Confirm Agency</a></li>
	    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Item Information Management</a></li>
	  </ul>
	
	  <!-- Tab panes -->
	  <div class="tab-content">
	    <div role="tabpanel" class="tab-pane active" id="home">
		<h1 data-lang="300">신청 중인 부동산 리스트 </h1>
		
		<!-- result -->      
		<div class="text-left">
			<table class="table">
				<thead class="table">
					<tr>
						<th data-lang="301">Agency Name</th>
						<th data-lang="302">Confirm</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="agency" items="${agencylist }">
					<tr>
						<td><c:out value="${agency.name }"/></td>
						<td><button class="btn btn-success" onclick="confirm(${agency.agencyId})"><span data-lang="303">승인</span></button></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>	    
	    </div>
	    <div role="tabpanel" class="tab-pane" id="profile">
			<!-- 아이템 정보 관리  Start -->
			<h1 data-lang="304">아이템 정보 관리</h1>
			<!-- search -->
			<div class="col-sm-10 form-group">
				<form class="form-inline" method="GET" onsubmit="return searchItem()">
					<input class="form-control" id="itemName" name="itemName" type="text" data-lang="307" placeholder="アイテム名">
					<input class="btn btn-info" type="submit" value="検索" data-lang="305">
				</form>
		   </div>
		   <!-- 아이템 등록 버튼 -->
			<div class="col-sm-2">
				<a class="btn btn-info" href="../item/itemAddPage" data-lang="306">アイテム登録</a>
			</div>   
			<!-- result -->
			<table class="table">
				<thead class="table">
					<tr>
						<th data-lang="307">Item Name</th>
						<th data-lang="308">Update</th>
						<th data-lang="309">Delete</th>
					</tr>
				</thead>
				<tbody id="itemList">
					<c:forEach var="item" items="${itemList }">
						<tr id="item${item.itemId}">
							<td>${item.itemName }</td>
							<td><a class="btn btn-success" href="../item/itemModifyPage?itemId=${item.itemId}" data-lang="310">修正</a></td>
							<td><button class="btn btn-warning" type="button" onclick="deleteItem(${item.itemId})"><span data-lang="93"></span></button></td>
						</tr>
	
					</c:forEach>
				</tbody>
			</table>
	    </div> 
	    <!-- 신청중인 부동산 업자 리스트 End -->
	    </div>
	  </div>
	
	</div>		
	<div class="col-sm-2 sidenav">
	</div>
</div>
</div>















