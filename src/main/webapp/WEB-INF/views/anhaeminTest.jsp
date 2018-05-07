<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Anhaemin Server Test</title>
 	<link rel="stylesheet" href="/fudousan/resources/js/prototype/style.css">
    <script src="/fudousan/resources/js/three.js"></script>
	<script src="/fudousan/resources/js/TDSLoader.js"></script>
	<script src="/fudousan/resources/js/TrackballControls.js"></script>
	<script src="/fudousan/resources/js/DragControls.js"></script>
	<script src="/fudousan/resources/js/dat.gui.js"></script>
	<script src="/fudousan/resources/js/stats.js"></script>
    <style type="text/css">
    	#threejsShow {
    		position:absolute;
    		z-index: 0;
    	}
    	#myButton {
    		position:absolute;
    		z-index: 1;
    	}
    </style>
</head>
<body>
<div id="myButton">
<input id="sendMsg"/><button id="chatBtn">챗고!</button>
    <ul id="msg"></ul>
</div>
<div id="threejsShow">

</div>
    <input type="hidden" value="88" id="itemId" name="itemId">
	<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="resources/js/socket.io.js"></script>
	  <script>
	  
	  
		var socket = io('http://localhost:7000');
		var chatBtn = document.querySelector('#chatBtn');

	
		chatBtn.addEventListener("click", function(event) {
			socket.emit('chat message', $('#sendMsg').val());
		});
		
		socket.on('chat message', function(msg){
			$('#msg').append($('<li>').text(msg));
		});
		
		socket.on('planeChangeOkMassage', function(data){
			 alert(data); 
			cocoa_count = 0;
		});
		
	</script>
	<script src="/fudousan/resources/js/prototype/main.js"></script>
	
</body>
</html>