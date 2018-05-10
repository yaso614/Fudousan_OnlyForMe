# const express = require("express");
const app = express();
const fs = require("fs");
const options = {
	key: fs.readFileSync("/etc/letsencrypt/live/syper01.com/privkey.pem"),
	cert: fs.readFileSync("/etc/letsencrypt/live/syper01.com/cert.pem"),
	ca: fs.readFileSync("/etc/letsencrypt/live/syper01.com/chain.pem")
};
const https = require("https").Server(options, app);
const io = require("socket.io")(https, {
	path: "/fudousan"
});

const roomPageIO = io.of("/roompage");
const userPageIO = io.of("/userpage");

let rooms = [];

roomPageIO.on("connection", function(socket){
	//-----------
	// Room 부분
	//-----------
	socket.on("connect-server", function(data){
		console.log(data.roomId + "번 방에 유저가 접속했습니다.");

		socket.join(data.roomId.toString());

		if (rooms.length > 0){
			for (let i = 0; i < rooms.length; i++){
				if (rooms[i].roomId === data.roomId){
					rooms[i].userCount++;
					rooms[i].userSocketId.push(socket.id);

					break;
				}
				if (i >= (rooms.length - 1)){
					rooms.push({
						roomId: data.roomId,
						userSocketId: [socket.id],
						userCount: 1
					});

					break;
				}
			}
		}
		else {
			rooms.push({
				roomId: data.roomId,
				userSocketId: [socket.id],
				userCount: 1
			});
		}

		showCurrentRooms();
	});

	socket.on('addItem',function(data){
		console.dir(data); //아이템 추가시 아이템 정보
		console.log('누군가 아이템을 추가합니다');
		socket.broadcast.to(data.roomId.toString()).emit('othersideAdd', data.roomItemObject); 
		//자기자신을 제외한 방안에 모두에게 데이터를 전달해주고 있다.
	});
	
	socket.on('delItem',function(data){
		console.dir(data); //아아템 삭제시 아이템 정보
		console.log('누군가 아이템을 삭제합니다');
		socket.broadcast.to(data.roomId.toString()).emit('othersideDelete', data.roomItemObject);
	});
	
	socket.on('moveItem',function(data){
		console.dir(data);//아이템 이동시 아이템 정보
		console.log('누군가 아이템을 옮깁니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideMove', data.roomItemObject);
	})
	
	socket.on('changeItem',function(data){
		console.dir(data);//아이템 변경시 아이템 정보
		console.log('누군가 아이템을 변경합니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideChange', data.roomItemObject);
	});
	
	socket.on('modeLoad',function(data){
		console.dir(data);//모델 로드시 정보
		console.log('모델이 로드됩니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideModeLoad', data.roomItemObject);
	});
	
	socket.on('modeError',function(data){
		console.dir(data); //모델 에러시 정보
		console.log('모델이 에러납니다');
		socket.broadcast.to(data.roomId.toString()).emit('othersideModeError', data.roomItemObject);
	});
	
	socket.on('selectItem',function(data){
		console.dir(data); //선택된 아이템 정보
		console.log('아이템을 선택합니다');
		socket.broadcast.to(data.roomId.toString()).emit('othersideSelectItem', data.roomObject);
	});
	
	socket.on('deselectItem',function(data){
		console.dir(data); //선택해제된 아이템 정보
		console.log('아이템을 선택해제 합니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideDeselectItem', data.roomObject);
	});
	
	socket.on('roomReset',function(data){
		console.log('누군가 리셋해서 너도 리셋하라고 요청합니다.') 
		socket.broadcast.to(data.roomId.toString()).emit('othersideReset');
	});
	
	socket.on('SnapShot',function(data){
		console.log('누군가 스냅샷을 찍었습니다.');
		socket.broadcast.to(data.roomId.toString()).emit('otherSnapShot', data.url);
	});
	
	socket.on('forward',function(data){
		console.log('누군가 앞으로가기를 눌렀습니다,');
		socket.broadcast.to(data.roomId.toString()).emit('otherForward', data.roomObject);
	});
	
	socket.on('back',function(){
		console.log('누군가 뒤로가기를 눌렀습니다.');
		socket.broadcast.to(data.roomId.toString()).emit('back');
	})

	socket.on('exit',function(data){
		console.log('누군가 접속을 종료합니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideExit', data);
	});
	
	socket.on('floor',function(data){
		console.log('누군가 텍스쳐를 설정합니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideTexture', data.TextureId);
	});
	
	socket.on('ceil',function(data){
		console.log('누군가 천장을 설정합니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideCeil', data.TextureId);
	});
	
	socket.on('wall',function(data){
		console.log('누군가 벽을 설정합니다.');
		socket.broadcast.to(data.roomId.toString()).emit('othersideWall', data.roomItemObject);
	});

	socket.on('wallHeight', function(data){
		console.log('누군가 벽 길이를 바꿨습니다.');
		socket.broadcast.to(data.roomId.toString()).emit('otherheight', data.height);
	});

	socket.on("disconnect", function(){
		if (rooms.length > 0){
			for (let i = 0; i < rooms.length; i++){
				for (let j = 0; j < rooms[i].userSocketId.length; j++){
					if (rooms[i].userSocketId[j] === socket.id){
						rooms[i].userSocketId.splice(j, 1);
						rooms[i].userCount--;

						console.log(rooms[i].roomId + "번 방에서 유저가 나갔습니다.");
						socket.to(rooms[i].roomId).emit("hangup");

						if (rooms[i].userCount <= 0){
							rooms.splice(i, 1);
						}
						
						showCurrentRooms();
	
						return;
					}
				}
			}
		}
	});

	//----------------
	// 화상 채팅 부분
	//----------------
	socket.on("call", function(data){
		console.log(data.roomId + "번 방에서 화상 채팅을 시도합니다.");

		for (let i = 0; rooms.length; i++){
			if (rooms[i].roomId === data.roomId){
				if (rooms[i].userCount < 2){
					console.log(data.roomId + "번 방에는 혼자 있습니다.");
	
					socket.emit("not-found-target");
					return;
				}
				else {
					socket.to(data.roomId.toString()).emit("call");

					return;
				}
			}
		}
	});

	socket.on("cancel-call", function(data){
		console.log(data.roomId + "번 방에서 화상 채팅 연결을 취소합니다.");

		socket.to(data.roomId.toString()).emit("cancel-call");
	});

	socket.on("answer-call", function(data){
		socket.to(data.roomId.toString()).emit("answer-call", data.answer);
	});

	socket.on("video-offer", function(data){
		console.log(data.roomId + "번 방에서 video offer를 보냅니다.");
		
		socket.to(data.roomId.toString()).emit("video-offer", data.sdp);
	});

	socket.on("video-answer", function(data){
		console.log(data.roomId + "번 방에서 video answer를 보냅니다.");

		socket.to(data.roomId.toString()).emit("video-answer", data.sdp);
	});

	socket.on("new-ice-candidate", function(data){
		console.log(data.roomId + "번 방에서 new ice candidate를 보냅니다.");

		socket.to(data.roomId.toString()).emit("new-ice-candidate", data.candidate);
	});

	socket.on("hangup", function(data){
		console.log(data.roomId + "번 방에서 화상 채팅을 끊습니다.");

		socket.to(data.roomId.toString()).emit("hangup");
	});

	socket.on("otherTitleChange", function(data){
		console.log(data.roomId + "번 방에서 방 이름을 바꿉니다.");
		socket.to(data.roomId).emit("otherTitleChange", data.roomTitle);
	});
});

userPageIO.on("connection", function(socket){
	console.log("유저 페이지 접속");
});

function showCurrentRooms(){
	console.log("[ 현재 방 현황 ]");

	if (rooms.length > 0){
		for (let i = 0; i < rooms.length; i++){
			console.log(rooms[i].roomId + "번 " + rooms[i].userCount + "/2" + " " + rooms[i].userSocketId);
		}
	}
	else {
		console.log("현재 만들어진 방이 하나도 없습니다.");
	}
}

https.listen(3443, function(){
	console.log("fudousan server on!");
});
