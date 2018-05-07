// 카메라 이동 단위
var cameraMoveValue = 100;
// 카메라 회전 단위
var cameraRotateValue = 1;
// 카메라 상하 각도
var cameraLookAngle = 0;
// 카메라, 씬, 렌더러, 카메라 컨트롤
var camera, scene, renderer, controls;
var composer, outlinePass, otherOutlinePass;
// 화면 돌리는 컨트롤 기준 높이
var controlHeight = 150;
// 화면 가로 길이
var width = window.innerWidth;
// 화면 세로 길이
var height = window.innerHeight;
// 방 바닥
var roomFloor;
// 방 천장
var roomCeil;
// 지면
var earth;
// 지면(사이즈)
var earthSize = 200000;
// Raycaster
var raycaster = new THREE.Raycaster();
// 마우스
var mouse = new THREE.Vector2();
// 완성된 벽 들
var walls = new THREE.Group();
// 벽 두께
var wallThickness = 10;
// 현재 화면에 존재하는 아이템들(메시 그룹)
var curRoomItems = [];
// 현재 선택 중인 아이템(메시 그룹)
var curSelected;
// 현재 선택 중인 아이템(메시 그룹)
var curSelectedOriginal;
// 현재 선택 된 아이템이 움직였는가?
var curMoving = false;
// 마우스 다운 이후로 마우스 업이 되었는가?
var isMouseUp = false;
// 정보 화면에서 아이템 정보를 변경하였는가?
var infoDataChange = false;
// 사용자가 시도한 명령들
var commands = [];
// 현재 커맨드 위치(해당 위치 직전까지가 지금까지 실행한 명령들)
var commandIndex = 0;
// 천장 텍스쳐
var ceilTexture;
// 바닥 텍스쳐
var floorTexture;
// 현재 선택한 방 물체
var curSelectedRoomObject;
// 현재 선택된 것이 벽이면 앞/뒤면
var curSelectedWallFace;
// 로딩 매니져
var manager;
initLoadingManager();
// 텍스쳐 로더
var textureLoader = new THREE.TextureLoader(manager);
// 마우스 올렸을때 투명도
var onMouseOpacity = 0.3;
// 모든 벽의 점들
var allDots = [];

$(function() {
	//초기화
	init();
	//화면 그리기
	animate();
	// 벽 그리기
	drawWall();
	// 로딩 끝
	//$( "#blocker" ).hide(); loading manager 로 역할 이동
});

function init() {
	THREE.ImageUtils.crossOrigin = '';
	// Loader Cache Enabled
	THREE.Cache.enabled = true;
	// 카메라 생성 및 초기화
	camera = new THREE.PerspectiveCamera(60, width / height, 10, earthSize);
	camera.position.y = 5000;
	camera.lookAt(0, 0, 0);
	
	// 장면 생성
	scene = new THREE.Scene();
	// 장면 배경색
	scene.background = new THREE.Color(0xf0f0f0);
	// 장면에 AmbientLight(광역 빛) 추가
	scene.add( new THREE.AmbientLight( 0x505050 ) );

	// DirectionalLight(직선 형태의 빛) 추가
	var directionalLight = new THREE.HemisphereLight(0xffffff);
	// 빛의 시작 점
	directionalLight.position.set(0, earthSize, earthSize/2);
	// 빛을 장면에 추가
	scene.add(directionalLight);

	// 렌더러
	renderer = new THREE.WebGLRenderer({
        preserveDrawingBuffer: true
    });
	//renderer = new THREE.WebGLRenderer( { canvas: canvas, antialias: true } );
	renderer.shadowMap.enabled = true;
	renderer.setPixelRatio( window.devicePixelRatio );
	// 렌더러 크기
	renderer.setSize(width, height);
	// 해당 렌더러를 화면에 추가하여서 사용
	document.body.appendChild( renderer.domElement );
	
	// controls
	controls = new THREE.OrbitControls( camera, renderer.domElement );
	//controls.addEventListener( 'change', render ); // call this only in static scenes (i.e., if there is no animation loop)
	controls.enableDamping = true; // an animation loop is required when either damping or auto-rotation are enabled
	controls.dampingFactor = 0.25;
	controls.panningMode = THREE.HorizontalPanning; // default is THREE.ScreenSpacePanning
	controls.minDistance = 100;
	controls.maxDistance = 50000;
	controls.target.set(0, controlHeight, 0);
	//controls.maxPolarAngle = Math.PI / 2;
	
	// 땅
	var earthGeometry = new THREE.PlaneGeometry( earthSize, earthSize, 32 );
	var earthMaterial = new THREE.MeshBasicMaterial({
		map:textureLoader.load("/fudousan/resources/image/Grass.jpg", function(texture) {
			texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
			texture.offset.set( 0, 0 );
			texture.repeat.set( 100, 100 );
		})
	});
	earth = new THREE.Mesh(earthGeometry, earthMaterial);
	earth.rotateX(-90 * Math.PI / 180);
	
	scene.add(earth);
	
	// 바닥
	roomFloor = drawFloor();
	roomFloor.rotateX(-90 * Math.PI / 180);
	roomFloor.position.y += 10;
	scene.add(roomFloor);
	
	// 바닥 텍스쳐
	floorTexture = textureLoader.load(room.floorTexture, function ( texture ) {
		
		roomFloor.geometry.computeBoundingBox();
		
		var w = roomFloor.geometry.boundingBox.max.x - roomFloor.geometry.boundingBox.min.x;
		var h = roomFloor.geometry.boundingBox.max.y - roomFloor.geometry.boundingBox.min.y;

	    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
	    texture.offset.set( 0, 0 );
	    texture.repeat.set( w/texture.image.width, h/texture.image.height );

	} );
	roomFloor.material.map = floorTexture;
	roomFloor.material.needsUpdate = true;
	
	// 천장
	roomCeil = drawFloor(false);
	roomCeil.rotateX(-90 * Math.PI / 180);
	roomCeil.position.y += room.height;
	scene.add(roomCeil);
	
	// 천장 텍스쳐
	ceilTexture = textureLoader.load(room.ceilingTexture, function ( texture ) {
		
		roomFloor.geometry.computeBoundingBox();
		
		var w = roomFloor.geometry.boundingBox.max.x - roomFloor.geometry.boundingBox.min.x;
		var h = roomFloor.geometry.boundingBox.max.y - roomFloor.geometry.boundingBox.min.y;

	    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
	    texture.offset.set( 0, 0 );
	    texture.repeat.set( w/texture.image.width, h/texture.image.height );

	} );
	roomCeil.material.map = ceilTexture;
	roomCeil.material.needsUpdate = true;
	
	// 마우스 이동 이벤트
	renderer.domElement.addEventListener('mousedown', this.onDocumentMouseDown, false);
	renderer.domElement.addEventListener('mousemove', this.onDocumentMouseMove, false);
	document.addEventListener('mouseup', this.onDocumentMouseUp, false);
	
	// 키보드 이벤트
	window.addEventListener('resize', this.onResize, false);
	window.addEventListener('keydown', this.onKeydown, false);
	
	// SKY BOX
	var imagePrefix = "/fudousan/resources/image/skybox/Daylight_Box_";
	var directions  = ["Right", "Left", "Top", "Bottom", "Front", "Back"];
	var imageSuffix = ".bmp";
	var skyGeometry = new THREE.CubeGeometry( earthSize, earthSize, earthSize );	
	
	var materialArray = [];
	for (var i = 0; i < 6; i++)
		materialArray.push( new THREE.MeshBasicMaterial({
			map: textureLoader.load(imagePrefix + directions[i] + imageSuffix),
			side: THREE.BackSide
		}));
	var skyMaterial = new THREE.MeshFaceMaterial( materialArray );
	var skyBox = new THREE.Mesh( skyGeometry, skyMaterial );
	scene.add( skyBox );
	
	// 3차원 축
	var axesHelper = new THREE.AxesHelper( 1000 );
	scene.add( axesHelper );
	
	// postprocessing
	composer = new THREE.EffectComposer( renderer );
	var renderPass = new THREE.RenderPass( scene, camera );
	composer.addPass( renderPass );
	
	// 자신용 아웃라인
	outlinePass = new THREE.OutlinePass( new THREE.Vector2( window.innerWidth, window.innerHeight ), scene, camera );
	outlinePass.edgeStrength = 3;
	outlinePass.edgeThickness = 1;
	outlinePass.visibleEdgeColor.set( 0xFFFFFF );
	composer.addPass( outlinePass );
	
	// 상대방용 아웃라인
	otherOutlinePass = new THREE.OutlinePass( new THREE.Vector2( window.innerWidth, window.innerHeight ), scene, camera );
	otherOutlinePass.edgeStrength = 3;
	otherOutlinePass.edgeThickness = 1;
	otherOutlinePass.visibleEdgeColor.set( 0xFF0000 );
	composer.addPass( otherOutlinePass );
	
	// FXAA
	effectFXAA = new THREE.ShaderPass( THREE.FXAAShader );
	console.dir(effectFXAA);
	effectFXAA.uniforms[ 'resolution' ].value.set( 1 / window.innerWidth, 1 / window.innerHeight );
	effectFXAA.renderToScreen = true;
	composer.addPass( effectFXAA );

	// roomitems 의 배열의 Roomitem VO에 따라 오브젝트 추가
	$.each(roomItems, function(index, obj) {
		placeRoomItem(obj);
	});
	
	
}

function initLoadingManager() {
	// LOADING
	manager = new THREE.LoadingManager();
	manager.onStart = function ( url, itemsLoaded, itemsTotal ) {

		$("#blocker").show();
		$("#blocker p").html( 'Started loading file: ' + url + '.\nLoaded ' + itemsLoaded + ' of ' + itemsTotal + ' files.' );
		console.log( 'Started loading file: ' + url + '.\nLoaded ' + itemsLoaded + ' of ' + itemsTotal + ' files.' );

	};

	manager.onLoad = function ( ) {

		$("#blocker").hide();
		$("#blocker p").empty();
		console.log( 'Loading complete!');

	};


	manager.onProgress = function ( url, itemsLoaded, itemsTotal ) {

		$("#blocker").show();
		$("#blocker p").html(url+" Loading... ("+itemsLoaded+"/"+itemsTotal+")");
		console.log( 'Loading file: ' + url + '.\nLoaded ' + itemsLoaded + ' of ' + itemsTotal + ' files.' );

	};

	manager.onError = function ( url ) {

		$("#blocker p").html('There was an error loading ' + url);
		console.log( 'There was an error loading ' + url );

	};
}

function animate(time) {
	//renderer.clear();
	// 다음 프레임 지정
	requestAnimationFrame( animate );
	// 화면 회전 정보 갱신
	controls.update(); // only required if controls.enableDamping = true, or if controls.autoRotate = true
	
	composer.render();

	
	TWEEN.update(time);
}

/**
 * 화면 크기 조정
 * @returns
 */
function onResize() {
	// 화면 가로 길이
	width = window.innerWidth;
	// 화면 세로 길이
	height = window.innerHeight;
	// 카메라 비율 재정의
	camera.aspect = width / height;
	camera.updateProjectionMatrix();
	// 렌더러 화면 크기 변경
	renderer.setSize(width, height);
	composer.setSize( width, height );
}

function onKeydown(event) {
	/*switch ( event.keyCode ) {
	case 38: // up
	case 87: // w
		camera.position.add(new THREE.Vector3(0, cameraMoveValue, 0).applyAxisAngle(new THREE.Vector3(0, 0, 1), camera.rotation.y));
		break;
	case 37: // left
	case 65: // a
		camera.position.sub(new THREE.Vector3(cameraMoveValue, 0, 0).applyAxisAngle(new THREE.Vector3(0, 0, 1), camera.rotation.y));
		break;
	case 40: // down
	case 83: // s
		camera.position.sub(new THREE.Vector3(0, cameraMoveValue, 0).applyAxisAngle(new THREE.Vector3(0, 0, 1), camera.rotation.y));
		break;
	case 39: // right
	case 68: // d
		camera.position.add(new THREE.Vector3(cameraMoveValue, 0, 0).applyAxisAngle(new THREE.Vector3(0, 0, 1), camera.rotation.y));
		break;
	case 81: // q
		camera.rotateOnAxis(new THREE.Vector3(0, 1, 0), cameraMoveValue/1000);
		break;
	case 69: // e
		camera.rotateOnAxis(new THREE.Vector3(0, 1, 0), -cameraMoveValue/1000);
		break;
	case 82: // r
		camera.position.add(new THREE.Vector3(0, 0, cameraMoveValue).applyAxisAngle(new THREE.Vector3(0, 0, 1), camera.rotation.z));
		break;
	case 70: // f
		camera.position.sub(new THREE.Vector3(0, 0, cameraMoveValue).applyAxisAngle(new THREE.Vector3(0, 0, 1), camera.rotation.z));
		break;
	case 90: // z
		cameraLookAngle -= cameraMoveValue/1000;
		camera.rotateOnAxis(new THREE.Vector3(1, 0, 0), cameraMoveValue/1000);
		break;
	case 88: // x
		cameraLookAngle += cameraMoveValue/1000;
		camera.rotateOnAxis(new THREE.Vector3(1, 0, 0), -cameraMoveValue/1000);
		break;
	}
	console.log(camera);*/
}

/**
 * 마우스 누름
 * @param event
 * @returns
 */
function onDocumentMouseDown(event) {
	closeTextureMenu();
	isMouseUp = false;
	deSelect(true);
	
	raycaster.setFromCamera(mouse, camera);
	var intersects = raycaster.intersectObjects(curRoomItems, true);
	if (intersects.length > 0) {
		
		// 현재 배치된 모든 아이템의 매시에 클릭이 되니까, 그 메시 그룹을 가져온다.
		//select(intersects[0].object.parent);
		NewCommand.select(intersects[0].object.parent.roomItem);
		
		// 화면 돌리기 불가
		controls.enabled = false;
	} else {
		// 벽 선택
		raycaster.setFromCamera(mouse, camera);
		var intersects = raycaster.intersectObjects(walls.children, true);
		if (intersects.length > 0) {
			
			curSelectedRoomObject = intersects[0].object.roomWall;
			var index = Math.floor( intersects[0].faceIndex / 2 );
		      switch (index) {
		         case 2: // front
		        	 curSelectedWallFace = index;
						openTextureMenu();
		         case 3: // back
		        	 curSelectedWallFace = index;
						openTextureMenu();
		      }
		      
		} else {
			// 바닥/ 천장 선택
			raycaster.setFromCamera(mouse, camera);
			var intersects = raycaster.intersectObjects([roomFloor, roomCeil], true);
			if (intersects.length > 0) {
				if(intersects[0].object == roomFloor) {
					curSelectedRoomObject = "roomFloor";
					openTextureMenu();
				} else if (intersects[0].object == roomCeil) {
					curSelectedRoomObject = "roomCeil";
					openTextureMenu();
				}
			}
			
		}
	}
	
}

/**
 * 마우스 이동
 * @param event
 * @returns
 */
function onDocumentMouseMove(event) {
	// 마우스 이동 저장
	if ( !moveMouse(event) ) {
		// 제자리 그대로면 종료
		return;
	}
	
	// 벽 투명도 초기화
	for ( var i = 0; i < walls.children.length; i++ ) {
		for ( var j = 0; j < walls.children[i].material.length; j++ ) {
			walls.children[i].material[j].opacity = 1;
		}
	}
	
	raycaster.setFromCamera(mouse, camera);
	var intersects = raycaster.intersectObjects([roomFloor]);
	if (intersects.length > 0) {
		if (curSelected != null && !isMouseUp && !controls.enabled && enabledEdit == true) {
			// 드래그 중인 아이템이 있으면 지면에 맞게 움직인다.
			var x = curSelected.roomItem.item.itemX;
			var y = curSelected.roomItem.item.itemY;
			var z = curSelected.roomItem.item.itemZ;
	
			// 원점 보정해서 움직임
			move(curSelected, intersects[0].point.x+x, intersects[0].point.y+y, intersects[0].point.z+z, false);
			
			// 움직이고 나서 움직였음을 표시한다.
			curMoving = true;
		}

		// 벽 반 투명
		raycaster.setFromCamera(mouse, camera);
		var intersects = raycaster.intersectObjects(walls.children, true);
		for(var j = 0; j < intersects.length; j++) {
			for ( var i = 0; i < intersects[j].object.material.length; i++ ) {
			    intersects[j].object.material[i].opacity = onMouseOpacity;
			    //intersects[j].object.material[i].transparent = true;
			    //intersects[j].object.material[i].needsUpdate = true;
			}
		}
	}
}

/**
 * 마우스 누름 해제
 * @param event
 * @returns
 */
function onDocumentMouseUp(event) {
	isMouseUp = true;
	if(curSelected != null) {
		// 움직였으면 DB 저장
		if(curMoving) {
			var param = curSelected.roomItem.clone();
			param.x = curSelected.position.x;
			param.y = curSelected.position.y;
			param.z = curSelected.position.z;
			NewCommand.move(param);
			
			curMoving = false;
			saveRoomItem(curSelected.roomItem);
			deSelect(true);
		}
	}
	// 컨트롤 활성화
	controls.enabled = true;
}

/**
 * 마우스 이동
 * @param event
 * @returns
 */
function moveMouse(event) {
	// Get mouse position
	var mouseX = (event.clientX / window.innerWidth) * 2 - 1;
	var mouseY = -(event.clientY / window.innerHeight) * 2 + 1;
	
	if ( mouse.x != mouseX || mouse.y != mouseY ) {
		mouse.x = mouseX;
		mouse.y = mouseY;
	} else {
		return false;
	}
	return true;
}

function changeHeigthListener(height) {
	changeHeigth(height);
	if (CommandCallBack.onHeightChange !== undefined) {
		CommandCallBack.onHeightChange(height);
	}
}

/**
 * 천장 높이 변경을 반영한다.
 * @returns
 */
function changeHeigth(height) {
	room.height = height;
	roomCeil.position.y = room.height;
	drawWall();
}

/**
 * DB대로 벽을 생성한다.
 * @returns
 */
function drawWall() {
	scene.remove(walls);
	walls = new THREE.Group(); 
	for(var i = 0; i < originalWalls.length; i++) {
		var c1 = new THREE.Vector3(originalWalls[i].roomWallConnector1.x, originalWalls[i].roomWallConnector1.y, roomFloor.z);
		var c2 = new THREE.Vector3(originalWalls[i].roomWallConnector2.x, originalWalls[i].roomWallConnector2.y, roomFloor.z);
		
		var width = c1.manhattanDistanceTo(c2);
		// Cube
		var geometry = new THREE.BoxGeometry(c1.clone().sub(c2).length(), wallThickness, room.height );
		
		/*for ( var j = 0; j < geometry.faces.length; j += 2 ) {
			var hex = Math.random() * 0xffffff;
			geometry.faces[ j ].color.setHex( hex );
			geometry.faces[ j + 1 ].color.setHex( hex );
		}*/
		
		//var material = new THREE.MeshFaceMaterial( { vertexColors: THREE.FaceColors, overdraw: 0.5 } );
		
		var frontMat = new THREE.MeshBasicMaterial({
			transparent : true
		});
		var frontTexture;
		if (originalWalls[i].frontTextureURL != "" ) {
			frontTexture = textureLoader.load(originalWalls[i].frontTextureURL, function ( texture ) {

			    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
			    texture.offset.set( 0, 0 );
			    //texture.repeat.set( width/texture.width, width/texture.width );
			    texture.repeat.set( width/texture.image.width, room.height/texture.image.height );

			} );
			if( frontTexture.image !== undefined ) {
				frontTexture.repeat.set( width/frontTexture.image.width, room.height/frontTexture.image.height );
			}
			frontMat.map = frontTexture;
			frontMat.needsUpdate = true;
		}
		
		var backMat = new THREE.MeshBasicMaterial({
			transparent : true
		});
		var backTexture;
		if (originalWalls[i].backTextureURL != "" ) {
			backTexture = textureLoader.load(originalWalls[i].backTextureURL, function ( texture ) {
				
			    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
			    texture.offset.set( 0, 0 );
			    texture.repeat.set( width/texture.image.width, room.height/texture.image.height );

			} );
			if( backTexture.image !== undefined ) {
				backTexture.repeat.set( width/backTexture.image.width, room.height/backTexture.image.height );
			}
			backMat.map = backTexture;
			backMat.needsUpdate = true;
		}
		var material = new THREE.MeshFaceMaterial([
	        new THREE.MeshBasicMaterial({
	            color: 'black'
	        }),
	        new THREE.MeshBasicMaterial({
	            color: 'black'
	        }),
	        frontMat,
	        backMat,
	        new THREE.MeshBasicMaterial({
	            color: 'black'
	        }),
	        new THREE.MeshBasicMaterial({
	            color: 'black'
	        })
	    ]);
		
		cube = new THREE.Mesh( geometry, material );
		
		cube.geometry.computeBoundingBox();
		
		/*var TEXTURE_SIZE = 64;

	    var max = cube.geometry.boundingBox.max;
	    var min = cube.geometry.boundingBox.min;
	    var height = max.y - min.y;
	    var width = max.x - min.x;
	    frontTexture.repeat.set(width / TEXTURE_SIZE , height / TEXTURE_SIZE);
	    console.log("x :" + width + "y : " + width);
	    frontTexture.needsUpdate = true;
	    
	    var max = cube.geometry.boundingBox.max;
	    var min = cube.geometry.boundingBox.min;
	    var height = max.y - min.y;
	    var width = max.x - min.x;
	    backTexture.repeat.set(width / TEXTURE_SIZE , height / TEXTURE_SIZE);
	    backTexture.needsUpdate = true;*/
	    
	    
		var cubePosition= new THREE.Vector3().copy(c1).lerp(c2, 0.5);
		//cubePosition.y += room.height/2;
		cube.position.copy(cubePosition);
		
		var normal = c1.clone().sub(c2).normalize();
		var angle = Math.atan2(normal.y, normal.x);
		cube.rotateZ(angle);
		
		cube.roomWall = originalWalls[i];
		
		walls.add( cube );
	}
	walls.rotateX(-90*Math.PI/180);
	walls.position.y += room.height/2;
	scene.add(walls);
}

/**
 * 바닥 그리기
 * @param side true : 앞면 / false : 뒷면
 * @returns
 */
function drawFloor(side) {
	if ( originalWalls.length == 0 ) {
		// 벽이 없으면 그냥 매우 큰 땅 생성
		var roomFloorGeometry = new THREE.PlaneGeometry( earthSize, earthSize, 32 );
		var roomFloorMaterial = new THREE.MeshBasicMaterial({color:0x002200, side:((side===undefined||side)?THREE.FrontSide:THREE.BackSide)});
		floor = new THREE.Mesh(roomFloorGeometry, roomFloorMaterial);
		
		return floor;
	}
	
	// 커넥터 추출
	var con = [];
	for(var i = 0; i < originalWalls.length; i++) {
		var c1 = new THREE.Vector2(originalWalls[i].roomWallConnector1.x, originalWalls[i].roomWallConnector1.y);
		var c2 = new THREE.Vector2(originalWalls[i].roomWallConnector2.x, originalWalls[i].roomWallConnector2.y);
		
		var flag1 = false;
		var flag2 = false;
		for(var j = 0; j < con.length; j++ ) {
			if ( c1.equals( con[j] )) flag1 = true;
			if ( c2.equals( con[j] )) flag2 = true;
		}
		
		if(!flag1) con.push(c1);
		if(!flag2) con.push(c2);
	}
	allDots = con;
	// 방문 기록
	var visit = [];
	// 인접 행렬 생성
	var adjMatrix = new Array(con.length);
	for(var i = 0; i < con.length; i++) {
		adjMatrix[i] = new Array(con.length);
		for(var j = 0; j < adjMatrix[i].length; j++) {
			adjMatrix[i][j] = 0;
		}
	}
	for(var i = 0; i < originalWalls.length; i++) {
		var c1 = new THREE.Vector2(originalWalls[i].roomWallConnector1.x, originalWalls[i].roomWallConnector1.y);
		var c2 = new THREE.Vector2(originalWalls[i].roomWallConnector2.x, originalWalls[i].roomWallConnector2.y);
		
		for(var j = 0; j < con.length; j++) {
			if ( con[j].equals(c1) ) {
				c1 = j;
			}
			if ( con[j].equals(c2) ) {
				c2 = j;
			}
		}
		// 대칭
		adjMatrix[c1][c2] = 1;
		adjMatrix[c2][c1] = 1;
		
	}

	/*
	var shape = new THREE.Shape();
	// 탐색 시작
	DFS(0, adjMatrix, visit, shape, con);
	

	var shape = new THREE.Shape();
	//shape.autoClose = true;
	// 벽 대로 선 긋기
	for(var i = 0; i < originalWalls.length; i++) {
		shape.moveTo(originalWalls[i].c1.x, originalWalls[i].c1.y);
		shape.lineTo(originalWalls[i].c2.x, originalWalls[i].c2.y);
	}
	
	// 가장 바깥쪽 선 으로 이어서 만들기
	// 1. 시작점 (가장 왼쪽, 가장 아래)
	var startIndex = 0;
	for(var i = 1; i < con.length; i++) {
		if(con[i].x < con[startIndex].x) {
			startIndex = i;
		}
	}
	console.log("start index : " + startIndex);
	
	var pastIndex = startIndex;
	// 2. 시작점에서 연결된 지점에서 가장 왼쪽의 점으로 이동하기
	shape.moveTo(con[startIndex].x, con[startIndex].y);
	var count = 1;
	while(count <= con.length) {
		var minAngle = -1000;
		var minIndex;
		for(var i=0; i < adjMatrix[startIndex].length; i++) {
			if (adjMatrix[startIndex][i] == 1 && pastIndex != i) {
				console.log("i : " + i);
				var pastAngle = con[pastIndex].clone().sub(con[startIndex]).angle();
				var curAngle = con[i].clone().sub(con[startIndex]).angle();
				curAngle = curAngle-pastAngle;
				if(curAngle < 0) curAngle *= -1;
				console.log(con[startIndex].x + ":" + con[startIndex].y + " to " + con[i].x + ":" + con[i].y + " = " + (curAngle*180/Math.PI));
				if ( curAngle > minAngle ) {
					minAngle = curAngle;
					minIndex = i;
				}
			}
		}
		// 현재 점은 체크
		pastIndex = startIndex;
		shape.lineTo(con[minIndex].x, con[minIndex].y);
		if (minIndex != startIndex) {
			// 다음 점이 있을 경우,
			console.log("다음 점 으로 : " + minIndex);
			startIndex = minIndex;
			count++;
		} else {
			// 다음 점 없다.
			console.log("끝 : " + minIndex);
			break;
		}
	}*/

	// x 정렬(오름차순)
	var sortX = [];
	for(var i = 0; i < con.length; i++) {
		sortX.push(i);
	}
	for(var i = 0; i < sortX.length; i++) {
		for(var j=0; j < sortX.length-i-1; j++) {
			if ( con[sortX[j]].x > con[sortX[j+1]].x ) {
				var t = sortX[j];
				sortX[j] = sortX[j+1];
				sortX[j+1] = t;
			}
		}
	}
	
	// y 정렬(오름차순)
	var sortY = [];
	for(var i = 0; i < con.length; i++) {
		sortY.push(i);
	}
	for(var i = 0; i < sortY.length; i++) {
		for(var j=0; j < sortY.length-i-1; j++) {
			if ( con[sortY[j]].y > con[sortY[j+1]].y ) {
				var t = sortY[j];
				sortY[j] = sortY[j+1];
				sortY[j+1] = t;
			}
		}
	}
	
	// 최종 외곽선 배열
	var outline = [];
	
	// 최상/최하/최우/최좌
	var top = sortY[sortY.length-1];
	var bottom = sortY[0];
	var left = sortX[0];
	var right = sortX[sortX.length-1];
	console.log("top("+top+"), bottom("+bottom+"), left("+left+"), right("+right+")");
	//var edge = [top, bottom, left, right];
	
	// 1사분면(top to right)
	/*var possible = [top];
	var curX = con[top].x;
	for(var i = sortY.length-2; i >= 0 && con[sortY[i]].y >= con[right].y; i--) {
		if ( con[sortY[i]].x >= curX ) {
			possible.push(sortY[i]);
			curX = con[sortY[i]].x;
		}
	}*/
	/*var possible = searchOutline( top, right, con, adjMatrix, 1, edge );

	possible.forEach(function (item, index, array) {
		outline.push(item);
		});*/
	
	
	// 2사분면(right to bottom)
	/*var possible = [right];
	var curY = con[right].y;
	for( var i = sortX.length-2; i >= 0 && con[sortX[i]].x >= con[bottom].x; i--) {
		if ( con[sortX[i]].y <= curY ) {
			possible.push(sortX[i]);
			curY = con[sortX[i]].y;
		}
	}*/
	/*var possible = searchOutline( right, bottom, con, adjMatrix, 2, edge );

	possible.forEach(function (item, index, array) {
		outline.push(item);
		});*/
	
	// 3사분면(bottom to left)
	/*var possible = [bottom];
	var curX = con[bottom].x;
	for(var i = 1; i < sortY.length-1 && con[sortY[i]].y <= con[left].y; i++) {
		if ( con[sortY[i]].x <= curX ) {
			possible.push(sortY[i]);
			curX = con[sortY[i]].x;
		}
	}*/

	/*var possible = searchOutline( bottom, left, con, adjMatrix, 3, edge );
	
	possible.forEach(function (item, index, array) {
		outline.push(item);
		});*/

	// 4사분면(left to top)
	/*var possible = [left];
	var curY = con[left].y;
	for( var i = 1; i < sortX.length-1 && con[sortX[i]].x <= con[top].x; i++) {
		if ( con[sortX[i]].y >= curY ) {
			possible.push(sortX[i]);
			curY = con[sortX[i]].y;
		}
	}*/

	/*var possible = searchOutline( left, top, con, adjMatrix, 4, edge );
	
	possible.forEach(function (item, index, array) {
		outline.push(item);
		});*/
	
	outline = searchOutline(top, con, adjMatrix);
	
	var shape = new THREE.Shape();
	shape.moveTo(con[outline[0]].x, con[outline[0]].y);
	for (var i = 1; i < outline.length; i++) {
		shape.lineTo(con[outline[i]].x, con[outline[i]].y);
	}
	
	//var roomFloorGeometry = new THREE.PlaneGeometry( earthSize, earthSize, 32 );
	var roomFloorGeometry = new THREE.ShapeGeometry( shape );
	var roomFloorMaterial = new THREE.MeshBasicMaterial({color:0xffffff, side:((side===undefined||side)?THREE.FrontSide:THREE.BackSide)});
	
	var geometry = roomFloorGeometry;
	
	geometry.computeBoundingBox();

	var max = geometry.boundingBox.max,
	    min = geometry.boundingBox.min;
	var offset = new THREE.Vector2(0 - min.x, 0 - min.y);
	var range = new THREE.Vector2(max.x - min.x, max.y - min.y);
	var faces = geometry.faces;

	geometry.faceVertexUvs[0] = [];

	for (var i = 0; i < faces.length ; i++) {

	    var v1 = geometry.vertices[faces[i].a], 
	        v2 = geometry.vertices[faces[i].b], 
	        v3 = geometry.vertices[faces[i].c];

	    geometry.faceVertexUvs[0].push([
	        new THREE.Vector2((v1.x + offset.x)/range.x ,(v1.y + offset.y)/range.y),
	        new THREE.Vector2((v2.x + offset.x)/range.x ,(v2.y + offset.y)/range.y),
	        new THREE.Vector2((v3.x + offset.x)/range.x ,(v3.y + offset.y)/range.y)
	    ]);
	}
	
	floor = new THREE.Mesh(roomFloorGeometry, roomFloorMaterial);
	
	return floor;
}

/*
function searchOutline( startPoint, endPoint, points, connectMap, area, edge ) {
	var top = edge[0];
	var bottom = edge[1];
	var left = edge[2];
	var right = edge[3];
	var possible = [startPoint];
	var curIndex = startPoint;
	var pastIndex = startPoint;
	
	// 시작 과 끝 점이 같으면 그 점만 반환
	if ( startPoint != endPoint ) {

		for(var i = 0; i < points.length; i++) {
			var moveIndex = -1;
			// 1. 현재 연결 배열 검색하기
			for ( var j = 0; j < connectMap[curIndex].length; j++ ) {
				// 2. 연결 된 점 찾기
				if ( j != pastIndex && connectMap[curIndex][j] == 1 ) {
					// 2-1. 현재 사분면에 해당 하지 않는 점은 제외
					switch ( area ) {
					case 1:
						if (!( (points[j].x >= points[top].x && points[j].x <= points[right].x) && (points[j].y <= points[top].y && points[j].y >= points[right].y) )) continue;
						break;
					case 2:
						if (!( (points[j].x <= points[right].x && points[j].x >= points[bottom].x) && (points[j].y <= points[right].y && points[j].y >= points[bottom].y) )) continue;
						break;
					case 3:
						if (!( (points[j].x <= points[bottom].x && points[j].x >= points[left].x) && (points[j].y >= points[bottom].y && points[j].y <= points[left].y) )) continue;
						break;
					case 4:
						if (!( (points[j].x >= points[left].x && points[j].x <= points[top].x) && (points[j].y >= points[left].y && points[j].y <= points[top].y) )) continue;
						break;
					}
					if (moveIndex == -1) {
						// 3-0. 첫 번째 점은 무조건 이동하기
						moveIndex = j;
					} else {
						// 3-1. 사분면에 따라 진행 경로 결정
						switch ( area ) {
						case 1:
							if ( points[j].x > points[moveIndex].x ) {
								moveIndex = j;
							}
							break;
						case 2:
							if ( points[j].y < points[moveIndex].y ) {
								moveIndex = j;
							}
							break;
						case 3:
							if ( points[j].x < points[moveIndex].x ) {
								moveIndex = j;
							}
							break;
						case 4:
							if ( points[j].y > points[moveIndex].y ) {
								moveIndex = j;
							}
							break;
						}
					}
					console.log(curIndex + " / " + moveIndex + " : " + points[curIndex].x + " / " + points[moveIndex].x);
				}
			}
			console.log("moveIndex("+moveIndex+")");
			// 4. 가장 오른쪽 점 선택
			possible.push(moveIndex);
			// 5. 이전 지점 저장해두기
			pastIndex = curIndex;
			// 6. 해당 인덱스로 이동
			curIndex = moveIndex;
			// 7. 현재 인덱스가 가장 오른쪽 인덱스이면 종료
			if ( curIndex == endPoint ) {
				console.log("reach to end(" + curIndex + ")");
				break;
			}
			console.log("move to " + curIndex);
		}
		
	}
	console.log("end area(" + area + ")");
	console.log(possible);
	return possible;
}*/

/**
 * 연결 맵에서 인덱스의 점 부터 외곽선을 찾아서 배열로 반환
 * @param startPoint
 * @param points
 * @param connectMap
 * @returns
 */
function searchOutline(startPoint, points, connectMap) {
	var possible = [startPoint];
	var curIndex = startPoint;
	var pastIndex = startPoint;
		for(var i = 0; i < points.length; i++) {
			var moveIndex;
			var minAngle = 1000;
			for ( var j = 0; j < connectMap[curIndex].length; j++ ) {
				if ( j != pastIndex && connectMap[curIndex][j] == 1 ) {
					var pastVector = new THREE.Vector2(points[pastIndex].x, points[pastIndex].y).sub(new THREE.Vector2(points[curIndex].x, points[curIndex].y));
					//var pastVector = new THREE.Vector2(points[curIndex].x, points[curIndex].y).sub(new THREE.Vector2(points[pastIndex].x, points[pastIndex].y));
					var curVector = new THREE.Vector2(points[j].x, points[j].y).sub(new THREE.Vector2(points[curIndex].x, points[curIndex].y));
					//console.log(pastVector);
					//console.log(curVector);
					var pastAngle = pastVector.angle()*180/Math.PI;
					var curAngle = curVector.angle()*180/Math.PI;
					//console.log(pastAngle);
					//console.log(curAngle);
					var p_c = pastAngle-curAngle;
					if ( p_c < 0 ) p_c = 360+p_c;
					//console.log("check j : "+j+", p-c = " + p_c);
					
					if ( p_c < minAngle ) {
						minAngle = p_c;
						moveIndex = j;
						//console.log("possible select("+moveIndex+"), angle : "+minAngle);
					}
				}
			}
			//console.log("moveIndex("+moveIndex+")");
			possible.push(moveIndex);
			pastIndex = curIndex;
			curIndex = moveIndex;
			if ( curIndex == startPoint ) {
				//console.log("END : reach to start index(" + curIndex + ")");
				break;
			}
			//console.log("move to " + curIndex);
		}
	//console.log(possible);
	return possible;
}

function changeFloorTexture(textureId) {
	
	$.ajax({
		url:"changeFloorTexture",
		type:"get",
		data: {
			roomId:room.roomId,
			textureId:textureId
		},
		dataType:"json",
		success:function(data) {
			if(data != null && data != false && data != "false") {

				var url = $("#img"+textureId).attr("src");
				floorTexture = textureLoader.load(url, function ( texture ) {
					
					roomFloor.geometry.computeBoundingBox();
					
					var w = roomFloor.geometry.boundingBox.max.x - roomFloor.geometry.boundingBox.min.x;
					var h = roomFloor.geometry.boundingBox.max.y - roomFloor.geometry.boundingBox.min.y;

				    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
				    texture.offset.set( 0, 0 );
				    texture.repeat.set( w/texture.image.width, h/texture.image.height );

				} );
				roomFloor.material.map = floorTexture;
				
			} else {
				alert("바닥 텍스쳐 변경에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			alert("바닥 텍스쳐 변경 중 오류가 발생하였습니다.");
		}
	});
}

function changeCeilTexture(textureId) {
	$.ajax({
		url:"changeCeilTexture",
		type:"get",
		data: {
			roomId:room.roomId,
			textureId:textureId
		},
		dataType:"json",
		success:function(data) {
			if(data != null && data != false && data != "false") {

				var url = $("#img"+textureId).attr("src");


				ceilTexture = textureLoader.load(url, function ( texture ) {
					
					roomFloor.geometry.computeBoundingBox();
					
					var w = roomFloor.geometry.boundingBox.max.x - roomFloor.geometry.boundingBox.min.x;
					var h = roomFloor.geometry.boundingBox.max.y - roomFloor.geometry.boundingBox.min.y;

				    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
				    texture.offset.set( 0, 0 );
				    texture.repeat.set( w/texture.image.width, h/texture.image.height );

				} );
				roomCeil.material.map = ceilTexture;
			} else {
				alert("천장 텍스쳐 변경에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			alert("천장 텍스쳐 변경 중 오류가 발생하였습니다.");
		}
	});
}

function changeWallTexture(roomWall, textureId, wallIndex) {
	var url;
	var wallFace;
	switch(wallIndex) {
	case 2:
		url = "wall/changeFrontTexture";
		wallFace = wallIndex;
		break;
	case 3:
		url = "wall/changeBackTexture";
		wallFace = wallIndex;
		break;
	default:
		alert("벽을 제대로 클릭해주세요.");
		return;
	}
	$.ajax({
		url:url,
		type:"get",
		data: {
			roomWallId:roomWall.roomWallId,
			textureId:textureId
		},
		dataType:"json",
		success:function(data) {
			if(data != null && data != false && data != "false") {
				var url = $("#img"+textureId).attr("src");
				
				// 기존 벽 데이터 변경
				for(var i = 0; i<originalWalls.length; i++) {
					if(wallFace==2) originalWalls[i].frontTextureURL = url;
					if(wallFace==3) originalWalls[i].backTextureURL = url;
				}
				
				var texture = textureLoader.load(url, function ( texture ) {
					var c1 = walls.children[i].roomWall.roomWallConnector1;
					var c2 = walls.children[i].roomWall.roomWallConnector2;

					var width = new THREE.Vector2(c2.x-c1.x, c2.y-c1.y).length();

				    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
				    texture.offset.set( 0, 0 );
				    texture.repeat.set( width/texture.image.width, room.height/texture.image.height );

				} );
				for(var i = 0; i < walls.children.length; i++) {
					if(walls.children[i].roomWall.roomWallId == roomWall.roomWallId) {
						// 현재 클릭된 면의 텍스쳐만 바꾼다.
						var material = walls.children[i].material[wallFace];
						material.map = texture;
						material.needsUpdate = true;
						return;
					}
				}
				alert("바꾸려는 벽이 없어서 실패하였습니다.");
			} else {
				alert("벽 텍스쳐 변경에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			alert("벽 텍스쳐 변경 중 오류가 발생하였습니다.");
		}
	});
}

/**
 * 아이템을 DB에 추가하고 화면에 배치한다.
 * Item VO 의 경우에는 화면 정 중앙에 배치,
 * RoomItem VO 의 경우에는 해당 VO의 x,y,z에 배치
 * @param item Item VO 또는 RoomItem VO
 * @returns
 */
function createItem(item, onCreate) {
	var x;
	var y;
	var z;
	var itemId;
	if(item instanceof Item) {
		// 화면 가운데
		raycaster.setFromCamera( new THREE.Vector2(), camera ); 
		//raycaster.set( camera.getWorldPosition(), camera.getWorldDirection() );
		
		var intersects = raycaster.intersectObjects([roomFloor]);

		if (intersects.length > 0) {
			x = intersects[0].point.x;
			y = intersects[0].point.y;
			z = intersects[0].point.z;
			itemId = item.itemId;
		}
	} else if(item instanceof RoomItem) {
		x = item.x;
		y = item.y;
		z = item.z;
		itemId = item.item.itemId;
	} else {
		throw new Error("아이템이 아닙니다.");
	}
	
	if (x !== undefined && y !== undefined && z !== undefined) {
		
		// 방 아이템 추가하고 그 아이템 가져오기
		$.ajax({
			url:"roomItem/create",
			type:"GET",
			data:{
				roomId:room.roomId,
				itemId:itemId,
				x:x,
				y:y,
				z:z,
			},
			dataType:"json",
			success:function(data) {
				if(data != null && data != "null") {
					// 받은 데이터를 roomitem vo로 변환
					var roomItem = objToRoomItem(data);
					
					// 메시지
					console.log("DB에 생성된 roomitem");
					console.dir(roomItem);
					
					// roomitem을 화면에 배치
					placeRoomItem(roomItem);
					
					if ( onCreate !== undefined ) {
						onCreate(roomItem);
					}
					
					if ( CommandCallBack.onCreate !== undefined ) {
						CommandCallBack.onCreate(roomItem);
					}
				
				} else {
					console.dir(roomItem);
					alert("아이템 배치에 실패하였습니다.");
				}
			},
			error:function(e) {
				// 메시지
				console.log(e);
				console.dir(item);
				alert("아이템 배치 중 오류가 발생하였습니다.");
			}
		});
	}
}

/**
 * 아이템을 DB와 화면에서 제거한다.
 * @param roomItem VO
 * @param onDelete 성공시 호출
 * @returns
 */
function deleteItem(roomItem) {
	if(!(roomItem instanceof RoomItem)) {
		throw new Error("룸 아이템이 아닙니다.");
	}
	
	$.ajax({
		url:"roomItem/delete",
		type:"GET",
		data:{
			roomItemId:roomItem.roomItemId
		},
		dataType:"json",
		success:function(data) {
			if(data != null && data != "false") {
				deplaceRoomItem(roomItem);
				if ( CommandCallBack.onDelete !== undefined ) {
					CommandCallBack.onDelete(roomItem);
				}
			
			} else {
				console.dir(roomItem);
				alert("아이템 제거에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			console.dir(roomItem);
			alert("아이템 제거 중 오류가 발생하였습니다.");
		}
	});
}

/**
 * RoomItem VO 대로 화면에 Item 을 배치한다.
 * @param roomItem VO
 * @returns
 */
function placeRoomItem(roomItem) {
	if(!(roomItem instanceof RoomItem)) {
		throw new Error("룸 아이템이 아닙니다.");
	}
	console.log("현재는 다음이 화면에 배치되어 있습니다.");
	console.dir(curRoomItems);
	console.log("다음의 roomitem을 화면에 배치하려 합니다.");
	console.dir(roomItem);
	
	// 외부 모델 로더 생성
	const loader = new THREE.TDSLoader(manager);
	// 해당 모델의 텍스쳐 경로 설정
	loader.setPath("/fudousan/item/"+(roomItem.item.itemId)+"/");
	// 모델 데이터 경로 설정 및 로딩 완료시 리스너 지정
	loader.load("/fudousan/item/"+(roomItem.item.itemId)+"/"+(roomItem.item.modelFileName), (object) => {
		// Group 를 확장하여 roomitem VO를 가지도록 한다.
		object.roomItem = roomItem;
		
		object.position.x = roomItem.x;
		object.position.y = roomItem.y;
		object.position.z = roomItem.z;

		/*object.rotation.x = roomItem.rotateX * Math.PI / 180;
		object.rotation.y = roomItem.rotateY * Math.PI / 180;
		object.rotation.z = roomItem.rotateZ * Math.PI / 180;*/
		object.rotateX(roomItem.rotateX * Math.PI / 180);
		object.rotateY(roomItem.rotateY * Math.PI / 180);
		object.rotateZ(roomItem.rotateZ * Math.PI / 180);
		
		object.scale.x = roomItem.item.itemScale;
		object.scale.y = roomItem.item.itemScale;
		object.scale.z = roomItem.item.itemScale;
		
		scene.add( object );

		curRoomItems.push(object);
		
		console.log(roomItem.roomItemId + " 배치 성공");

		if ( CommandCallBack.onModelLoad !== undefined ) {
			CommandCallBack.onModelLoad();
		}
	}, undefined, CommandCallBack.onModelError);
}


/**
 * 해당 룸아이템을 찾아서 배치 해제한다.
 * @param roomItem
 * @returns true 또는 false
 */
function deplaceRoomItem(roomItem) {
	if(!(roomItem instanceof RoomItem)) {
		throw new Error("룸 아이템이 아닙니다.");
	}
	console.log("현재 배치된 아이템은 다음과 같지만,");
	console.dir(curRoomItems);
	console.log("다음을 화면에서 배치 해제하려 합니다.");
	console.dir(roomItem);
	for(var i = 0; i < curRoomItems.length; i++) {
		if ( curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId ) {
			if ( curSelected == curRoomItems[i] ) deSelect(false);
			console.log("배치 해제 대상");
			console.dir(curRoomItems[i].roomItem);
			scene.remove(curRoomItems[i]);
			
			
			curRoomItems.splice(i, 1);
			
			
			return true;
		}
	}
	return false;
}


/**
 * 화면에 배치된 object을 특정 좌표로 이동
 * @param object
 * @param x
 * @param y
 * @param z
 * @param useAni
 * @returns
 */
function move(object, x, y, z, useAni) {
	let targetX, targetY, targetZ;
	
	targetX = ( x != null ) ? x : object.position.x;
	targetY = ( y != null ) ? y : object.position.y;
	targetZ = ( z != null ) ? z : object.position.z;
	
	if ( useAni === undefined || useAni == true ) {
		itemMoveAni(object, targetX, targetY, targetZ);
	} else {
		if(x != null) {
			object.position.x = x;
		}
		if(y != null) {
			object.position.y = y;
		}
		if(z != null) {
			object.position.z = z;
		}
	}
}

/**
 * 해당 룸 아이템을 해당 x,y,z 로 이동
 * @param roomItem
 * @param excuteCallBack 콜백 실행 여부
 * @param useAni 애니메이션 실행 여부
 * @returns
 */
function moveRoomItem(roomItem, excuteCallBack, useAni) {
	for(var i = 0; i < curRoomItems.length; i++) {
		if ( curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId ) {
			var result = curRoomItems[i].roomItem.clone();

			move(curRoomItems[i], roomItem.x, roomItem.y, roomItem.z, useAni);
			
			curRoomItems[i].roomItem.x = roomItem.x;
			curRoomItems[i].roomItem.y = roomItem.y;
			curRoomItems[i].roomItem.z = roomItem.z;
			
			if(excuteCallBack !== undefined && excuteCallBack == true && CommandCallBack.onMove !== undefined) {
				CommandCallBack.onMove(roomItem);
			}
			return result;
		}
	}
	console.log(roomItem.roomItemId + " 가 없어서 이동 실패");
	return null;
}

/**
 * 화면에 배치된 object을 각 축을 기준으로 회전
 * @param object
 * @param rx 도
 * @param ry 도
 * @param rz 도
 * @param useAni 애니메이션 사용여부
 * @returns
 */
function rotate(object, rx, ry, rz, useAni) {
	let targetRX, targetRY, targetRZ;
	
	if ( rx != null ) {
		targetRX = rx * Math.PI / 180;
		//object.roomItem.rotateX = rx;

		if ( useAni !== undefined && useAni == false ) {
			object.rotation.x = targetRX;
		}
	}
	else {
		targetRX = object.rotation.x;
	}
	
	if ( ry != null ) {
		targetRY = ry * Math.PI / 180;
		//object.roomItem.rotateY = ry;

		if ( useAni !== undefined && useAni == false ) {
			object.rotation.y = targetRY;
		}
	}
	else {
		targetRY = object.rotation.y;
	}
	
	if ( rz != null ) {
		targetRZ = rz * Math.PI / 180;
		//object.roomItem.rotateZ = rz;

		if ( useAni !== undefined && useAni == false ) {
			object.rotation.z = targetRZ;
		}
	}
	else {
		targetRZ = object.rotation.z;
	}
	
	if ( useAni === undefined || useAni == true ) {
		itemRotateAni(object, targetRX, targetRY, targetRZ);
	} 
}

/**
 * 해당 룸 아이템을 해당 rx,ry,rz 로 회전
 * @param roomItem
 * @returns
 */
function rotateRoomItem(roomItem) {
	for(var i = 0; i < curRoomItems.length; i++) {
		if ( curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId ) {
			rotate(curRoomItems[i], roomItem.rotateX, roomItem.rotateY, roomItem.rotateZ);
			if(CommandCallBack.onRotate !== undefined) {
				CommandCallBack.onRotate(roomItem);
			}
			return true;
		}
	}
	console.log(roomItem.roomItemId + " 가 없어서 회전 실패");
	return false;
}

/**
 * 내가 아이템을 선택
 * @param roomItem
 * @returns
 */
function select(roomItem) {
	for(var i = 0; i < curRoomItems.length; i++ ) {
		if (curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId) {
			curSelected = curRoomItems[i];
			curSelectedOriginal = roomItem.clone();
			
			// 선택 상태 아웃 라인 표시
			outlinePass.selectedObjects = curSelected.children;

			initInfo();
			
			return;
		}
	}
	alert('해당 아이템이 존재하지 않습니다.');
}

/**
 * 다른 사람이 해당 아이템을 선택
 * @param roomItem
 * @returns
 */
function selectByOther(roomItem) {
	for(var i = 0; i < curRoomItems.length; i++ ) {
		if (curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId) {
			
			// 선택 상태 아웃 라인 표시
			otherOutlinePass.selectedObjects = curRoomItems[i].children;
			
			return;
		}
	}
	alert('상대방이 선택한 해당 아이템이 존재하지 않습니다.');
}


/**
 * 내가 아이템을 선택 해제
 * @param executeCallBack
 * @returns
 */
function deSelect(executeCallBack) {
	if ( infoDataChange ) {
		if(curSelected != null) {
			curSelected.roomItem = curSelectedOriginal;
			applyRoomItem(curSelected);
			
			if(executeCallBack == true && CommandCallBack.onDeselect !== undefined) {
				CommandCallBack.onDeselect(curSelectedOriginal);
			}
		}
		curSelectedOriginal = null;
	}
	
	curSelected = null;
	// 선택 상태 아웃 라인 표시
	outlinePass.selectedObjects = [];
	
	resetInfo();
}

/**
 * 다른 사람이 선택 해제
 * @returns
 */
function deSelectByOther() {
	otherOutlinePass.selectedObjects = [];
}

/**
 * roomitem의 값을 저장한다.
 * @param roomItem
 * @returns
 */
function saveRoomItem(roomItem) {
	if(!(roomItem instanceof RoomItem)) {
		throw new Error("룸 아이템이 아닙니다.");
	}
	
	var refSiteSet = roomItem.item.refSiteSet;
	roomItem.item.refSiteSet = null;
	$.ajax({
		url:"roomItem/save",
		type:"POST",
		data: JSON.stringify(roomItem),
		contentType: 'application/json; charset=utf-8',
		dataType:"json",
		success:function(data) {
			if(data != null && data != 0) {
			} else {
				console.dir(roomItem);
				alert("아이템 저장에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			console.dir(roomItem);
			alert("아이템 저장 중 오류가 발생하였습니다.");
		}
	});
	roomItem.item.refSiteSet = refSiteSet;
}


/**
 * 해당 three js 객체의 roomItem의 값대로 해당 객체를 변경한다.
 * @param object
 * @returns
 */
function applyRoomItem(object) {
	/*object.position.x = object.roomItem.x;
	object.position.y = object.roomItem.y;
	object.position.z = object.roomItem.z;
	object.rotation.x = object.roomItem.rotateX * Math.PI / 180;
	object.rotation.y = object.roomItem.rotateY * Math.PI / 180;
	object.rotation.z = object.roomItem.rotateZ * Math.PI / 180;*/
	
	itemMoveAni(object, object.roomItem.x, object.roomItem.y, object.roomItem.z);
	itemRotateAni(object, object.roomItem.rotateX * Math.PI / 180, object.roomItem.rotateY * Math.PI / 180, object.roomItem.rotateZ * Math.PI / 180);
}

/**
 * 아이템 변경사항을 적용한다.(DB의 룸 아이템을 변경/이동 한다.)
 * @param roomItem 
 * @returns
 */
function applyItemChange(roomItem) {
	if(!(roomItem instanceof RoomItem)) {
		throw new Error("룸 아이템이 아닙니다.");
	}
	
	$( "#blocker" ).show();
	$.ajax({
		url:"roomItem/modify",
		type:"POST",
		data:JSON.stringify(roomItem),
		contentType: 'application/json; charset=utf-8',
		dataType:"json",
		success:function(data) {
			
			if(data != null && data != false && data != "false") {
				infoDataChange = false;

				if(CommandCallBack.onItemChange !== undefined) {
					CommandCallBack.onItemChange(roomItem);
				}
				
			} else {
				
				alert("아이템 변경에 실패하였습니다.");
				
			}

			$( "#blocker" ).hide();
			
		},
		error:function(e) {
			
			console.log(e);
			alert("아이템 변경 중 오류가 발생하였습니다.");

			$( "#blocker" ).hide();
			
		}
	});
}



/**
 * 사용자 화면의 룸 아이템을 회전/이동 한다.
 * @param roomItem
 * @returns
 */
function applyItemChangeLocal(roomItem) {
	var result;
	for(var i = 0; i < curRoomItems.length; i++) {
		if ( curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId ) {
			result = curRoomItems[i].roomItem.clone();
			
			move(curRoomItems[i], roomItem.x, roomItem.y, roomItem.z);
			rotate(curRoomItems[i], roomItem.rotateX, roomItem.rotateY, roomItem.rotateZ);
			return result;
		}
	}
	console.log(roomItem.roomItemId + " 가 없어서 아이템 속성 변경 실패");
	return null;
}

/**
 * DB의 RoomItem을 리셋한다.
 * @returns
 */
function roomReset() {
	$( "#blocker" ).show();
	$.ajax({
		url:"roomItem/reset",
		type:"POST",
		data:{roomId:room.roomId},
		dataType:"json",
		success:function(data) {
			
			if(data != null && data != false && data != "false") {

				roomResetLocal();
				
				if (CommandCallBack.onReset !== undefined) {
					CommandCallBack.onReset();
				}
				
			} else {
				
				alert("리셋에 실패하였습니다.");
				
			}

			$( "#blocker" ).hide();
			
		},
		error:function(e) {
			
			console.log(e);
			alert("리셋 중 오류가 발생하였습니다.");

			$( "#blocker" ).hide();
			
		}
	});
}

/**
 * 사용자 화면을 리셋한다.
 * @returns
 */
function roomResetLocal() {
	for( var i = curRoomItems.length - 1; i >= 0; i--) {
		scene.remove(curRoomItems[i]);
	}
	
	curRoomItems = [];
	curSelected = null;
	curSelectedOriginal = null;
	commandIndex = 0;
	commands = [];
}

/**
 * 현재 화면을 촬영해서 서버에 저장한다.
 * @returns
 */
function takeSnapShot() {
	
	var strMime = "image/jpeg";
	var imgData = renderer.domElement.toDataURL(strMime);
    
    var blob = dataURItoBlob(imgData);

    var formData = new FormData();
    formData.append("file", blob, room.roomId);
    
	$( "#blocker" ).show();
    $.ajax({
    	
		url:"snapshot",
		type:"POST",				
		data:formData,
		processData: false,
	    contentType: false,
		dataType:"text",	
		success:function(data) {
			
			if(data != null) {
				
				refreshSnapshot(data);
				
				if (CommandCallBack.onSnapShot !== undefined) {
					CommandCallBack.onSnapShot(data);
				}
				
			} else {
				
				alert("스냅샷 저장에 실패하였습니다.");
				
			}

			$( "#blocker" ).hide();
			
		},
		error:function(e) {
			
			console.log(e);
			alert("스냅샷 저장 중 오류가 발생하였습니다.");

			$( "#blocker" ).hide();
			
		}
	});
}

/**
 * 스냅샷을 갱신한다.
 * @param url
 * @returns
 */
function refreshSnapshot(url) {
	var snapshotURL = url;
	$("#snapshot").html("<img class='snapshot' src='/fudousan"+snapshotURL+"'>");
}

/**
 * 화면 데이터를 Blob으로 변환
 * @param dataURI
 * @returns
 */
function dataURItoBlob(dataURI)
{
    var byteString = atob(dataURI.split(',')[1]);

    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++)
    {
        ia[i] = byteString.charCodeAt(i);
    }

    var bb = new Blob([ab], { "type": mimeString });
    
    return bb;
}
/**
 * 사용자 명령어 정의
 * 각각에 따라 명령을 수행한다.
 * Local 로 끝나는 것은 해당 결과만 화면에 반영한다.(DB X), 주로 상대가 내린 명령에 대한 짝이다.
 */
var NewCommand = {
		// 생성 커맨드
		create : function(roomItem) {
			var command = new Command();
			command.name = "create";
			command.onDo = function() {
				createItem(command.onDoRoomItem, function(roomItem) {
					renewCommandRoomItemId(command.onDoRoomItem.roomItemId, roomItem.roomItemId);
					command.onRedoRoomItem = roomItem;
					command.onDoRoomItem = roomItem;
					
				});
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				deleteItem(command.onRedoRoomItem);
			};
			command.onRedoRoomItem = undefined;
			addCommand(command);

			createItem(roomItem, function(roomItem) {
				command.onRedoRoomItem = roomItem;
				command.onDoRoomItem = roomItem;
			});
		},
		// 삭제 커맨드
		delete : function(roomItem) {
			var command = new Command();
			command.name = "delete";
			command.onDo = function() {
				deleteItem(command.onDoRoomItem);
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				createItem(command.onRedoRoomItem, function(roomItem) {
					renewCommandRoomItemId(command.onRedoRoomItem.roomItemId, roomItem.roomItemId);
					command.onDoRoomItem = roomItem;
					command.onRedoRoomItem = roomItem;
				});
			};
			command.onRedoRoomItem = roomItem;
			addCommand(command);
			
			deleteItem(roomItem);
		},
		// 단순 배치 커맨드
		place : function(roomItem) {
			var command = new Command();
			command.name = "place";
			command.onDo = function() {
				create(command.onDoRoomItem, function(roomItem) {
					renewCommandRoomItemId(command.onDoRoomItem.roomItemId, roomItem.roomItemId);
					command.onDoRoomItem = roomItem;
					command.onRedoRoomItem = roomItem;
				});
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				deleteItem(command.onRedoRoomItem);
			};
			command.onRedoRoomItem = roomItem;
			addCommand(command);
			
			placeRoomItem(roomItem);
			
		},
		// 단순 배치 해제 커맨드
		deplace : function(roomItem) {
			var command = new Command();
			command.name = "deplace";
			command.onDo = function() {
				deleteItem(command.onDoRoomItem);
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				create(command.onRedoRoomItem, function(roomItem) {
					renewCommandRoomItemId(command.onRedoRoomItem.roomItemId, roomItem.roomItemId);
					command.onDoRoomItem = roomItem.clone();
					command.onRedoRoomItem = roomItem.clone();
				});
			};
			command.onRedoRoomItem = roomItem;
			addCommand(command);
			
			deplaceRoomItem(roomItem);
		},
		// 이동 커맨드
		move : function(roomItem) {
			var command = new Command();
			command.name = "move";
			command.onDo = function() {
				command.onRedoRoomItem = moveRoomItem(command.onDoRoomItem.clone(), true);
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				command.onDoRoomItem = moveRoomItem(command.onRedoRoomItem.clone(), true);
			};
			command.onRedoRoomItem = roomItem;
			addCommand(command);
			
			command.onRedoRoomItem = moveRoomItem(roomItem, true, false);
		},
		// 단순 이동 커맨드
		moveLocal : function(roomItem) {
			var command = new Command();
			command.name = "moveLocal";
			command.onDo = function() {
				command.onRedoRoomItem = moveRoomItem(command.onDoRoomItem.clone(), true);
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				command.onDoRoomItem = moveRoomItem(command.onRedoRoomItem.clone(), true);
			};
			command.onRedoRoomItem = roomItem;
			addCommand(command);
			
			command.onRedoRoomItem = moveRoomItem(roomItem, false);
		},
		// 아이템 속성 변경 커맨드
		itemChange : function(roomItem) {
			var command = new Command();
			command.name = "itemChange";
			command.onDo = function() {
				applyItemChange(command.onDoRoomItem.clone());
				applyItemChangeLocal(command.onDoRoomItem.clone());
			};
			command.onDoRoomItem = roomItem.clone();
			command.onRedo = function() {
				applyItemChange(command.onRedoRoomItem.clone());
				applyItemChangeLocal(command.onRedoRoomItem.clone());
			};
			command.onRedoRoomItem = roomItem.clone();
			addCommand(command);
			
			applyItemChange(roomItem.clone());
			command.onRedoRoomItem = applyItemChangeLocal(command.onDoRoomItem.clone());
		},
		// 단순 아이템 속성 변경 커맨드
		itemChangeLocal : function(roomItem) {
			var command = new Command();
			command.name = "itemChangeLocal";
			command.onDo = function() {
				applyItemChangeLocal(command.onDoRoomItem.clone());
			};
			command.onDoRoomItem = roomItem;
			command.onRedo = function() {
				applyItemChangeLocal(command.onRedoRoomItem.clone());
			};
			command.onRedoRoomItem = roomItem;
			addCommand(command);

			command.onRedoRoomItem = applyItemChangeLocal(command.onDoRoomItem.clone());
		},
		// 선택 커맨드
		select : function(roomItem) {
			for(var i = 0; i < curRoomItems.length; i++) {
				if ( curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId ) {
					select(roomItem, true);
					
					if(CommandCallBack.onSelect !== undefined) {
						CommandCallBack.onSelect(curSelectedOriginal);
					}
				}
			}
		},
		// 상대의 선택 커맨드
		selectLocal : function(roomItem) {
			for(var i = 0; i < curRoomItems.length; i++) {
				if ( curRoomItems[i].roomItem.roomItemId == roomItem.roomItemId ) {
					selectByOther(roomItem);
				}
			}
		},
		// 선택 해제 커맨드
		deselect : function(roomItem) {
			deSelect(true);
		},
		// 상대의 선택 해제 커맨드
		deselectLocal : function(roomItem) {
			deSelectByOther();
		}
}

/**
 * 커맨드 배열에서 기존의 RoomItemId를 새로운 RoomItemId로 변경한다.
 * @param beforeId
 * @param afterId
 * @returns
 */
function renewCommandRoomItemId(beforeId, afterId) {
	for(var i = 0; i < commands.length; i++) {
		if(commands[i].onDoRoomItem.roomItemId == beforeId) {
			commands[i].onDoRoomItem.roomItemId = afterId;
		}
		if(commands[i].onRedoRoomItem.roomItemId == beforeId) {
			commands[i].onRedoRoomItem.roomItemId = afterId;
		}
	}
}


function addCommand(command) {
	commands[commandIndex] = command;
	commandIndex += 1;
	commands.splice(commandIndex);
	console.log("--addCommand 현재 명령 상황--" + commandIndex);
	console.dir(commands);
}

/**
 * 앞으로가기
 * @returns
 */
function forward() {
	// 1. 현재가 마지막 위치면 무시
	if (commandIndex == commands.length) return;
	// 2. 현재 위치의 명령의 do를 실행
	commands[commandIndex].onDo();
	// 3. 현재 위치 1 증가
	commandIndex += 1;
	console.log("--forward 현재 명령 상황--" + commandIndex);
	console.dir(commands);
}

/**
 * 뒤로가기
 * @returns
 */
function back() {
	// 1. 현재 위치가 0이면 무시
	if (commandIndex == 0) return;
	// 2. 현재 위치 -1의 명령어의 onRedo 실행
	commands[commandIndex-1].onRedo();
	// 3. 현재 위치 1 감소
	commandIndex -= 1;
	console.log("--back 현재 명령 상황--" + commandIndex);
	console.dir(commands);
}

function applyTexture(textureId) {
	if(curSelectedRoomObject === undefined) {
		return;
	}
	switch(curSelectedRoomObject) {
	case "roomFloor":
		if( CommandCallBack.onFloorTexture !== undefined ) {
			CommandCallBack.onFloorTexture(textureId);
		}
		changeFloorTexture(textureId);
		break;
	case "roomCeil":
		if( CommandCallBack.onCeilTexture !== undefined ) {
			CommandCallBack.onCeilTexture(textureId);
		}
		changeCeilTexture(textureId);
		break;
	default:
		if( CommandCallBack.onWallTexture !== undefined ) {
			CommandCallBack.onWallTexture(curSelectedRoomObject, textureId, curSelectedWallFace);
		}
		changeWallTexture(curSelectedRoomObject, textureId, curSelectedWallFace);
		break;
	}
	curSelectedRoomObject = undefined;
	curSelectedWallFace = undefined;
	closeTextureMenu();
}

//-------------
// 애니메이션
//-------------
function itemMoveAni(object, targetX, targetY, targetZ){
	// 애니메이션 시작 위치
	let start = {
		x: object.position.x,
		y: object.position.y,
		z: object.position.z
	};

	// 애니메이션 끝 위치
	let target = {
		x: targetX,
		y: targetY,
		z: targetZ
	};
	
	// 애니메이션 설정
	let tween = new TWEEN.Tween(start).to(target, 1000);
	tween.onUpdate(function(){
		object.position.x = start.x;
		object.position.y = start.y;
		object.position.z = start.z;
	});
	tween.easing(TWEEN.Easing.Exponential.Out);
	
	// 애니메이션 적용
	tween.start();
}

function itemRotateAni(object, targetRX, targetRY, targetRZ){
	// 애니메이션 시작 위치
	let start = {
		x: object.rotation.x,
		y: object.rotation.y,
		z: object.rotation.z
	};

	// 애니메이션 끝 위치
	let target = {
		x: targetRX,
		y: targetRY,
		z: targetRZ
	};
	
	// 애니메이션 설정
	let tween = new TWEEN.Tween(start).to(target, 1000);
	tween.onUpdate(function(){
		object.rotation.x = start.x;
		object.rotation.y = start.y;
		object.rotation.z = start.z;
	});
	tween.easing(TWEEN.Easing.Exponential.Out);
	
	// 애니메이션 적용
	tween.start();
}
