<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WALLPAGE</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>">
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/resources/js/three.js"/>"></script>
<script src="<c:url value="/resources/js/TDSLoader.js"/>"></script>
<script src="<c:url value="/resources/js/TrackballControls.js"/>"></script>
<script src="<c:url value="/resources/js/DragControls.js"/>"></script>
<script src="<c:url value="/resources/js/THREE.MeshLine.js"/>"></script>
<script src="<c:url value="/resources/js/dat.gui.js"/>"></script>
<script src="<c:url value="/resources/js/stats.js"/>"></script>
<script src="<c:url value="/resources/js/socket.io.js"/>"></script>
<script type="text/javascript">
	var roomId = ${roomId};
	var originalWalls = [];
	var originalConnectors = [];
	<c:if test="${!empty wallsAndConnectors}">
		<c:forEach var="wall" items="${wallsAndConnectors.walls}" >
			originalWalls.push({startPoint:${wall.roomWallConnector1.connectorId}, endPoint:${wall.roomWallConnector2.connectorId}});
		</c:forEach>
		<c:forEach var="connector" items="${wallsAndConnectors.connectors}" >
			originalConnectors.push(new THREE.Vector3(${connector.x}, ${connector.y}, 0));
		</c:forEach>
	</c:if>
</script>
<style type="text/css">
canvas {
    position: fixed;
    top: 0;
    left: 0;
}
.left-menu {
	position:absolute;
	top: 10%;
	left: 0px;
	z-index: 1;
}
.left-menu menu {
	list-style: none;
}
</style>
</head>
<body>
<div class="left-menu" oncontextmenu='return false' onselectstart='return false'>
	<menu>
		<li><input type="button" class="btn btn-default" value="저장" onclick="save()"></li>
		<li><input id="btn_back" type="button" class="btn btn-default" value="뒤로가기"></li>
		<li><input id="btn_forward" type="button" class="btn btn-default" value="앞으로가기"></li>
		<li><input type="button" class="btn btn-default" value="벽 그리기" onclick="changeTool(0)"></li>
		<li><input type="button" class="btn btn-default" value="벽 지우기" onclick="changeTool(1)"></li>
		<li><input type="button" class="btn btn-default" value="리셋" onclick="reset()"></li>
	</menu>
</div>

<script type="text/javascript" src="<c:url value="/resources/js/wallPage.js"/>"></script>
</body>
</html>