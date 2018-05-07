<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>회원 가입 페이지</title>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.2.1.js'/>"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			var idRegexp = /^[0-9a-zA-Z]{1,20}$/;
			
			function idClass(status) {
				switch(status) {
					case 'reset':
						$('#id').parent().removeClass('has-success');
						$('#id').parent().removeClass('has-error');
						$('#id').parent().find('span').removeClass('glyphicon-remove');
						$('#id').parent().find('span').removeClass('glyphicon-ok');
						break;
					case 'success':
						$('#id').parent().addClass('has-success');
						$('#id').parent().find('span').addClass('glyphicon-ok');
						break;
					case 'error':
						$('#id').parent().addClass('has-error');
						$('#id').parent().find('span').addClass('glyphicon-remove');
						break;
				}
			}
			
			function passwordClass(status) {
				switch(status) {
					case 'reset':
						$('#password').parent().removeClass('has-success');
						$('#password').parent().removeClass('has-error');
						$('#password').parent().find('span').removeClass('glyphicon-remove');
						$('#password').parent().find('span').removeClass('glyphicon-ok');
						$('#password2').addClass('hidden');	
						break;
					case 'success':
						$('#password').parent().addClass('has-success');
						$('#password').parent().removeClass('has-error');
						$('#password').parent().find('span').addClass('glyphicon-ok');
						$('#password').parent().find('span').removeClass('glyphicon-remove');
						$('#password2').addClass('hidden');	
						break;
					case 'error':
						$('#password').parent().removeClass('has-success');
						$('#password').parent().addClass('has-error');
						$('#password').parent().find('span').removeClass('glyphicon-ok');
						$('#password').parent().find('span').addClass('glyphicon-remove');
						$('#password2').removeClass('hidden');	
						break;
				}
			}

			function nameClass(status) {
				switch(status) {
					case 'reset':
						$('#name').parent().removeClass('has-success');
						$('#name').parent().removeClass('has-error');
						$('#name').parent().find('span').removeClass('glyphicon-remove');
						$('#name').parent().find('span').removeClass('glyphicon-ok');
						break;
					case 'success':
						$('#name').parent().addClass('has-success');
						$('#name').parent().find('span').addClass('glyphicon-ok');
						break;
					case 'error':
						$('#name').parent().addClass('has-error');
						$('#name').parent().find('span').addClass('glyphicon-remove');
						break;
				}
			}
			
			$('form').on('reset', function() {
				idClass('reset');
				passwordClass('reset');
				nameClass('reset');
			});
			
			$('form').on('submit', function formCheck() {
				var id = $('#id').val();
				var password = $('#password').val();
				var name = $('#name').val();
				
				if (id == null || !idRegexp.test(id)) {
					idClass('error');
					$('#id').focus();
					return false;
				}
				if (password == null || password.length == 0 || password == '') {
					passwordClass('error');
					$('#password').focus();
					return false;
				}
				if (password.length > 20) {
					passwordClass('error');
					$('#password').focus();
					return false;
				}
				if (password != $('#password2').val()) {
					passwordClass('error');
					$('#password').focus();
					return false;
				}
				if (name == null || name.length == 0 || name == '') {
					nameClass('error');
					$('#name').focus();
					return false;
				}
				if (name.length > 30) {
					nameClass('error');
					$('#name').focus();
					return false;
				}
				return true;
			})
			
			$('#id').on('change', function() {
				idClass('reset');
				if (!idRegexp.test($('#id').val())) {
					idClass('error');
					return false;
				} else {
					$.ajax({
						url:'idcheck',
						tyep:'GET',
						data:{
							id:$(this).val()
						},
						dataType:'text',
						success:function(data) {
							if (data=='true') {
								idClass('error');
							} else {
								idClass('success');
							}
						},
						error:function(err) {
							console.log(err);
						}
					});
				}
			});
			
			$('#password').on('change', function() {
				$('#password2').removeClass('hidden');	

				if ($('#password').val() == $('#password2').val()) {
					passwordClass('success');
				} else {
					passwordClass('error');
				}
			});
			$('#password2').on('change', function() {
				if ($('#password').val() == $('#password2').val()) {
					passwordClass('success');
				} else {
					passwordClass('error');
				}
			});
			
			$('#name').on('change', function() {
				if ($('#name').val().length > 30) {
					nameClass('error');
				} else {
					nameClass('success');
				}
			});
		})
	</script>
</head>
<body>
<div class="container">
	<div>
		<h1 class="text-center">회원 가입</h1>
	</div>
	<form action="register" method="POST">
		<div class="col-sm-offset-3 col-sm-6">
			<div class="form-group has-feedback">
				<label for="id">아이디</label>
				<input id="id" class="form-control" type="text" name="clientId" required="required" placeholder="아이디를 입력하세요.">
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<label for="password">비밀번호</label>
				<input id="password" class="form-control" type="password" name="clientPassword" required="required" placeholder="비밀번호를 입력하세요.">
				<span class="glyphicon form-control-feedback"></span>
				<input id="password2" type="password" class="form-control hidden" placeholder="동일한 비밀번호를 입력하세요.">
			</div>
			<div class="form-group has-feedback">
				<label for="name">이름</label>
				<input id="name" class="form-control" type="text" name="clientName" required="required" placeholder="이름을 입력하세요.">
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="form-group text-center">
				<div>
					<input type="submit" class="btn btn-primary" value="회원가입">
					<input type="reset" class="btn btn-danger" value="다시 작성">
				</div>
			</div>
		</div>
	</form>
</div>
</body>
</html>