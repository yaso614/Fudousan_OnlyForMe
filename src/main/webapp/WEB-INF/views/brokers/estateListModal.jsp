<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ESTATE LIST MODAL START -->
<div class="modal fade" id="estateListModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" data-lang="151">Estate List</h4>
			</div>
			<div class="modal-body">
				<table class="table">
					<thead>
						<tr>
							<th data-lang="121">Estate Name</th>
							<th data-lang="149">Estate Address</th>
							<th data-lang="150">Base Room</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="estate" items="${select }">
							<tr>
								<td>${estate.estateName}</td>
								<td>${estate.region}${estate.prefecture}${estate.municipality}${estate.districtname}${estate.address}</td>
								<td>
									<c:if test="${empty estate.baseRoomId}"><a class="btn btn-default" href="./newBaseRoom?estateId=${estate.estateId}">Create</a></c:if>
									<c:if test="${!empty estate.baseRoomId}"><a class="btn btn-default" href="./wall/wallPage?roomId=${estate.baseRoomId}" data-lang="310"></a></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>	
<!-- ESTATE LIST MODAL END -->