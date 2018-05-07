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
				<c:if test="${sessionScope.loginEmail == null}">
					<li id="loginNameTag"></li>
					<li id="loginAtag"><a data-toggle="modal" href="#loginModal" data-lang="0"></a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span data-lang="5"></span><span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="/fudousan/join/join" data-lang="6"></a></li>
							<li class="divider"></li>
							<li><a href="/fudousan/join/agencyjoin" data-lang="7"></a></li>
						</ul>
					</li>
				</c:if>
				<c:if test="${sessionScope.permissionId == 1}">
					<li><a>${sessionScope.loginMemberName}, Welcome!</a></li>


					<li><a href="/fudousan/logout" data-lang="1"></a></li>

					<li><a href="/fudousan/memberupdate/memberupdate?email=${sessionScope.loginEmail}" data-lang ="84">Update Member</a></li>
					
					<li><a href="/fudousan/mypageNormalUser" data-lang ="85">Member Page</a></li>
					
										
				</c:if>
				<c:if test="${sessionScope.permissionId == 2}">
					<li><a>${sessionScope.loginMemberName}, Welcome!</a></li>

					

					<li><a href="/fudousan/logout" data-lang="1"></a></li>

					<li><a href="/fudousan/memberupdate/memberupdate?email=${sessionScope.loginEmail}" data-lang ="84">Update Member</a></li>
					
					<li><a href="/fudousan/interior/" data-lang ="87">Interior Page</a></li>
					
				</c:if>
				 
				<c:if test="${sessionScope.permissionId == 3}">
					<li><a>${sessionScope.loginMemberName}, Welcome!</a></li>
					
					<li><a href="/fudousan/logout" data-lang="1"></a></li>

					<li><a href="/fudousan/memberupdate/agencyupdate?email=${sessionScope.loginEmail}" data-lang ="84">Update Member</a></li>
					
					<li><a href="/fudousan/bm" data-lang ="86">Agency Page</a></li>
					
					<c:if test="${sessionScope.loginDesigner == 1 }">
						<li><a href="/fudousan/interior/" data-lang ="87">Interior Page</a></li>
					</c:if>
				</c:if>
				<c:if test="${sessionScope.permissionId == 99 }">
					<li><a>${sessionScope.loginMemberName}, Welcome!</a></li>
					<li><a href="/fudousan/logout" data-lang="1"></a></li>
					<li><a href="/fudousan/admin/" data-lang ="88">Admin page</a></li>
					<li><a href="/fudousan/mypageNormalUser" data-lang ="85">Member Page</a></li>
					<li><a href="/fudousan/bm" data-lang ="86">Agency Page</a></li>
					<li><a href="/fudousan/interior/" data-lang ="87">Interior Page</a></li>
				</c:if>
				 	
 				
 					
 					
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