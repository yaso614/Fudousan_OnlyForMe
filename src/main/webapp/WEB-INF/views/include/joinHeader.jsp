<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
	<!-- Header -->
	<nav class="navbar navbar-default" style="margin: auto 0">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				
				<a class="navbar-brand" href="/fudousan" style="margin-bottom: 10px;">
					<img alt="Fudousan" src="/fudousan/resources/image/logo2.png">
				</a>
				
			</div>
			
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right" style="margin-top: 8px;" id="navbar-ul">
	
					<li><a href="javascript:selectLanguage('ko')"><img src="/fudousan/resources/image/if_South Korea_15986.png"></a></li>
					<li><a href="javascript:selectLanguage('jp')"><img src="/fudousan/resources/image/if_Japan_92149.png"></a></li>
					<li><a href="javascript:selectLanguage('en')"><img src="/fudousan/resources/image/if_United States of America(USA)_16036.png"></a></li>
					
					<%-- <c:if test="${sessionScope.loginId != null && sessionScope.isNormal == normal}">
						<li><a href="mypageNormalUser" style="color: blue;">ahm test</a></li>
					</c:if> --%>
				</ul>
			</div><!-- /.navbar-collapse -->
		</div><!-- /.container-fluid -->
	</nav>