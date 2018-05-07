var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var cors = require('cors');
var room = ""; //방번호
var users = []; //유저로그인
app.use(cors());

app.get('/', function(req, res) {
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket) {
  console.log('a user connected');
	socket.on('disconnect', function(){
		
		for (var i = 0; i < users.length; i++){
			var user = users[i];
			
			if (user.socketId === socket.id){
					users.splice(i, 1);		
					console.dir(users);break;
			}
		}
		
		console.log('user disconnected');
	});

		socket.on('room_join',function(data){
			room = data.roomId;
		socket.join(room);
		
		
		var user = {};
		user.userName = data.userName;
		user.socketId = socket.id;
		users.push(user);
		console.dir(users);	
		
	});

	
	
	
	socket.on('addItem',function(data){
		console.dir(data); //아이템 추가시 아이템 정보
		console.log('누군가 아이템을 추가합니다');
		socket.broadcast.to(room).emit('othersideAdd',data); 
		//자기자신을 제외한 방안에 모두에게 데이터를 전달해주고 있다.
	});
	
	socket.on('delItem',function(data){
		console.dir(data); //아아템 삭제시 아이템 정보
		console.log('누군가 아이템을 삭제합니다');
		socket.broadcast.to(room).emit('othersideDelete',data);
	});
	
	socket.on('moveItem',function(data){
		console.dir(data);//아이템 이동시 아이템 정보
		console.log('누군가 아이템을 옮깁니다.');
		socket.broadcast.to(room).emit('othersideMove',data);
	})
	
	socket.on('changeItem',function(data){
		console.dir(data);//아이템 변경시 아이템 정보
		console.log('누군가 아이템을 변경합니다.');
		socket.broadcast.to(room).emit('othersideChange',data);
	});
	
	socket.on('modeLoad',function(data){
		console.dir(data);//모델 로드시 정보
		console.log('모델이 로드됩니다.');
		socket.broadcast.to(room).emit('othersideModeLoad',data);
	});
	
	socket.on('modeError',function(data){
		console.dir(data); //모델 에러시 정보
		console.log('모델이 에러납니다');
		socket.broadcast.to(room).emit('othersideModeError',data);
	});
	
	socket.on('selectItem',function(data){
		console.dir(data); //선택된 아이템 정보
		console.log('아이템을 선택합니다');
		socket.broadcast.to(room).emit('othersideSelectItem',data);
	});
	
	socket.on('deselectItem',function(data){
		console.dir(data); //선택해제된 아이템 정보
		console.log('아이템을 선택해제 합니다.');
		socket.broadcast.to(room).emit('othersideDeselectItem',data);
	});
	
	socket.on('roomReset',function(){
		console.log('누군가 리셋해서 너도 리셋하라고 요청합니다.') 
		socket.broadcast.to(room).emit('othersideReset');
	});
	
	
	socket.on('SnapShot',function(data){
		console.log('누군가 스냅샷을 찍었습니다.');
		socket.broadcast.to(room).emit('otherSnapShot',data);
	});
	
	socket.on('forward',function(data){
		console.log('누군가 앞으로가기를 눌렀습니다,');
		socket.broadcast.to(room).emit('otherForward',data);
	});
	
	socket.on('back',function(){
		console.log('누군가 뒤로가기를 눌렀습니다.');
		socket.broadcast.to(room).emit('back');
	})
	
	
});
	

function rooma(roomId){
	return "room"+roomId;
}



http.listen(8000, function() {
  console.log('Http server started with :8000');
});