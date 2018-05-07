$(function(){
	$("#startVideoChatBtn").html(langSet[lang][0]);
});

let startBtn = document.getElementById("startVideoChatBtn");
startBtn.onclick = pushStartBtn;

let localCam = null;
let remoteCam = null;
let localStream = null;
let myPeerConnection;

let langSet = {
	ko: {
		0: "화상 채팅 시작",
		1: "화상 채팅 종료",
		2: "상대방의 수락을 기다리고 있습니다.",
		3: "취소",
		4: "상대방이 화상 채팅을 신청했습니다.",
		5: "상대방이 없습니다.",
		6: "상대방이 화상 채팅을 거절했습니다.",
		7: "상대방이 화상 채팅 연결을 취소했습니다.",
		8: "수락"
	},
	jp: {
		0: "画像チャット開始",
		1: "画像チャット終了",
		2: "相手の受諾を待っています。",
		3: "キャンセル",
		4: "相手が画像チャットを申請しました。",
		5: "相手がいません。",
		6: "相手が画像チャットを拒絶しました。",
		7: "相手が画像チャットの連結をキャンセルしました。",
		8: "受ける"
	},
	en: {
		0: "Start Video Chat",
		1: "Stop Video Chat",
		2: "Waiting for acceptance.",
		3: "Cancel",
		4: "The other person has requested a video chat.",
		5: "There is no other person.",
		6: "The other person declined the video chat.",
		7: "The other person canceled the call.",
		8: "Accept"
	}
};

let lang = getCookie("lang");

if (lang === ""){
	lang = "ko";
}

function pushStartBtn(){
	if (startBtn.innerHTML === langSet[lang][0]){
		call();
	}
	else {
		hangup();
	}
};

function call(){
	console.log("--> call");
	
	setStartBtn("start");
	
	localCam = setLocalCamTo("top-menu");
	remoteCam = setRemoteCamTo("top-menu");
	
	console.dir(localCam);
	console.dir(remoteCam);
	
	navigator.mediaDevices.getUserMedia({video: true, audio: true}).then(function(stream){
		localCam.srcObject = stream;
		
		showCallWindow();

		socket.emit("call", {roomId: room.roomId});
	}).catch(function(e){
		alert("getUserMedia() error: " + e.name);
	});
};

function setStartBtn(state){
	switch (state){
		case "start":
			startBtn.innerHTML = langSet[lang][1];
			startBtn.disabled = false;
			break;
		case "calling":
			startBtn.disabled = true;
			break;
		case "stop":
			startBtn.innerHTML = langSet[lang][0];
			startBtn.disabled = false;
			break;
	}
}

function showCallWindow(){
	setStartBtn("calling");
	
	let div = document.createElement("div");
	div.id = "callingDiv";
	div.style.position = "absolute";
	div.style.top = "30px";
	div.style.left = "0px";
	div.style.width = "300px";
	div.style.color = "rgb(255, 255, 255)";
    div.style.padding = "10px";
    div.style.background = "rgba(0, 0, 0, 0.5)";
    div.style.zIndex = "2";
    
	let html = langSet[lang][2] + "<br><div id='btnDiv'><button type='button' id='cancelCallBtn'>취소</button></div>";
	div.innerHTML = html;
	
	document.body.appendChild(div);
	
	let cancelCallBtn = document.getElementById("cancelCallBtn");
	cancelCallBtn.style.marginTop = "10px";
	cancelCallBtn.style.color = "rgb(0, 0, 0)";
	cancelCallBtn.onclick = function(){
    	removeDiv("callingDiv");
    	
		socket.emit("cancel-call", {roomId: room.roomId});
		
    	closeCall();
    };
    
    let btnDiv = document.getElementById("btnDiv");
    btnDiv.style.textAlign = "right";
};

function removeDiv(id){
	let div = document.getElementById(id);
	div.remove();
}

socket.on("call", function(){
	startBtn.disabled = true;
	
	receiveCallWindow();
});

socket.on("cancel-call", function(){
	let receiveCallDiv = document.getElementById("receiveCallDiv");
	
	while (receiveCallDiv.hasChildNodes()){
		receiveCallDiv.removeChild(receiveCallDiv.firstChild);
	}
	
	let html = langSet[lang][7];
	receiveCallDiv.innerHTML = html;
	
	setTimeout(function(){
		removeDiv("receiveCallDiv");
		
		setStartBtn("stop");
	}, 3000);
});

socket.on("not-found-target", function(){
	console.log("--> not found target");
	
	closeCall();
	removeDiv("callingDiv");
	
	alert(langSet[lang][5]);
});

function receiveCallWindow(){
	setStartBtn("calling");
	
	let div = document.createElement("div");
	div.id = "receiveCallDiv";
	div.style.position = "absolute";
	div.style.top = "30px";
	div.style.left = "0px";
	div.style.width = "300px";
	div.style.color = "rgb(255, 255, 255)";
    div.style.padding = "10px";
    div.style.background = "rgba(0, 0, 0, 0.5)";
    div.style.zIndex = "2";
    
	let html  = langSet[lang][4] + "<br>";
		html += "<button type='button' id='acceptCallBtn'>" + langSet[lang][8] + "</button>";
		html += "<button type='button' id='refuseCallBtn'>" + langSet[lang][3] + "</button>";
	div.innerHTML = html;
	
	document.body.appendChild(div);
	
	let acceptCallBtn = document.getElementById("acceptCallBtn");
	acceptCallBtn.style.color = "rgb(0, 0, 0)";
	acceptCallBtn.onclick = function(){
		removeDiv("receiveCallDiv");
		
		socket.emit("answer-call", {
			roomId: room.roomId,
			answer: true
		});
		
		setStartBtn("start");
	};
	
	let refuseCallBtn = document.getElementById("refuseCallBtn");
	refuseCallBtn.style.color = "rgb(0, 0, 0)";
	refuseCallBtn.onclick = function(){
		removeDiv("receiveCallDiv");
		
		socket.emit("answer-call", {
			roomId: room.roomId,
			answer: false
		});
		
		startBtn.disabled = false;
		startBtn.style.color = "rgb(0, 0, 0)";
	};
}

socket.on("answer-call", function(answer){
	removeDiv("callingDiv");
	
	if (answer == true){
		sendVideoOffer();
		
		setStartBtn("start");
	}
	else {
		closeCall();
		
		alert(langSet[lang][6]);
	}
});

function sendVideoOffer(){
	myPeerConnection = createPeerConnection();

	myPeerConnection.addStream(localCam.srcObject);

	myPeerConnection.createOffer().then(function(offer){
		return myPeerConnection.setLocalDescription(offer);
	}).then(function(){
		console.log("--> send video offer");
		socket.emit("video-offer", {
			roomId: room.roomId,
			sdp: myPeerConnection.localDescription
		});
	}).catch(function(err){
		console.log(err);
	});
}

socket.on("video-offer", function(sdp){
	console.log("--> receive video-offer");

	myPeerConnection = createPeerConnection();

	let desc = new RTCSessionDescription(sdp);

	localCam = setLocalCamTo("top-menu");
	remoteCam = setRemoteCamTo("top-menu");
	
	myPeerConnection.setRemoteDescription(desc).then(function(){
		return navigator.mediaDevices.getUserMedia({video: true, audio: true});
	}).then(function(stream){
		localCam.srcObject = stream;
		myPeerConnection.addStream(stream);
	}).then(function(){
		console.log("--> create answer");
		return myPeerConnection.createAnswer();
	}).then(function(answer){
		console.log("--> set local description");
		return myPeerConnection.setLocalDescription(answer);
	}).then(function(){
		console.log("--> send video-answer");
		socket.emit("video-answer", {
			roomId: room.roomId,
			sdp: myPeerConnection.localDescription
		});
	}).catch(function(err){
		console.log(err);
	})
});

socket.on("video-answer", function(sdp){
	console.log("--> receive video-answer");

	let desc = new RTCSessionDescription(sdp);
	myPeerConnection.setRemoteDescription(desc);
});

socket.on("new-ice-candidate", function(candidate){
	console.log("--> receive new-ice-candidate");

	myPeerConnection.addIceCandidate(new RTCIceCandidate(candidate));
});

socket.on("hangup", function(){
	console.log("--> receive hang up");
	
	closeCall();
});

function hangup(){
	console.log("--> hang up");
	
	closeCall();
	
	socket.emit("hangup", {
		roomId: room.roomId
	});
};

function closeCall(){
	console.log("--> close call");
	
	if (myPeerConnection){
		myPeerConnection.onaddstream = null;
		myPeerConnection.onremovestream = null;
		myPeerConnection.onnicecandidate = null;

		remoteCam.srcObject = null;

		myPeerConnection.close();
		myPeerConnection = null;
	}
	
	if (localCam.srcObject !== null){
		let tracks = localCam.srcObject.getTracks();
		
		tracks.forEach(function(track){
			track.stop();
		});
		
		localCam.srcObject = null;
	}
	
	removeCam();
	setStartBtn("stop");
};

function createPeerConnection(){
	console.log("--> create peer connection");

	let rpc = new RTCPeerConnection();

	// Set up event handlers for the ICE negotiation process.
	rpc.onicecandidate = handleICECandidateEvent;
	rpc.onaddstream = handleAddStreamEvent;
	rpc.onnremovestream = handleRemoveStreamEvent;

	return rpc;
}

function handleICECandidateEvent(event){
	console.log("--> handle ice candidate event");

	if (event.candidate){
		socket.emit("new-ice-candidate", {
			roomId: room.roomId,
			candidate: event.candidate
		});
	}
}

function handleAddStreamEvent(event){
	console.log("--> handle add stream event");
	
	remoteCam.srcObject = event.stream;
	setStartBtn("start");
}

function handleRemoveStreamEvent(event){
	console.log("--> handle remove stream event");

	closeCall();
}

function handleICEConnectionStateChangeEvent(event){
	console.log("--> handle ice connection state change event");

	switch (myPeerConnection.iceConnectionState){
		case "closed":
		case "failed":
		case "disconnect":
			hangup();
			break;
	}
}

function handleSignalingStateChangeEvent(event){
	console.log("--> handle signaling state change event");

	switch (myPeerConnection.signalingState){
		case "closed":
			hangup();
			break;
	}
}

function setLocalCamTo(id){
	let localVideo = document.createElement("video");
	localVideo.id = "localCam";
	localVideo.width = 200;
	localVideo.height = 150;
	localVideo.autoplay = true;
	localVideo.muted = true;
	
	$("#" + id).append(localVideo);
	
	return localVideo;
}

function setRemoteCamTo(id){
	let remoteVideo = document.createElement("video");
	remoteVideo.id = "remoteCam";
	remoteVideo.width = 200;
	remoteVideo.height = 150;
	remoteVideo.autoplay = true;
	
	$("#" + id).append("<br>");
	$("#" + id).append(remoteVideo);
	
	return remoteVideo;
}

function removeCam(){
	$("#top-menu").children().remove();
}