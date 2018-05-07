<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
	<!-- 로그인 모달 -->
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" data-lang="0"></h4>
				</div>
				<div class="modal-body">
					<div class="col-sm-2"></div>
					<div class="col-sm-7 ">
						<label><span data-lang="2"></span></label>
						<input type="text" id="memberEmail" name="memberID"  class="form-control" data-lang="89" placeholder="Input your Email Address">
						<br>
						<label><span data-lang="3"></span></label>
						<input type="password" id="password" name="password" class="form-control" data-lang="90" placeholder="Input your Password">
						<br>
						<div class="text-center">
							<button type="button" class="btn btn-info " id="loginBtn" data-lang="0"></button>
							<button type="button" class="btn btn-danger " data-dismiss="modal" data-lang="4"></button>
						</div>
					</div>
					<div class="col-sm-2"></div>
					<div style="clear: both;"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- 로그인 모달 끝 -->