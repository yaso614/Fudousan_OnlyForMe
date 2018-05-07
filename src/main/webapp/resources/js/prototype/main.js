var controls;
var camera, scene, renderer;
var width = window.innerWidth;
var height = window.innerHeight;
var plane, selection;
var offset = new THREE.Vector3();
var mouse = new THREE.Vector2();
var connection_try = 0; //동시변경 확인을 위한 접속횟수체크 변수
var whoAmI = ''; // 사용자가 누군지 판단하는 변수

//의자
var chair;
var isSelected;
//드래그 될 오브젝트 들을 저장하는 배열
var objects = [];
//Raycaster
var raycaster = new THREE.Raycaster();
//마우스
var mouse = new THREE.Vector2();
//초기화
init();
//화면 그림
animate();

function init() {
	// 카메라 생성 및 초기화
	camera = new THREE.PerspectiveCamera(60, width / height, 0.1, 100000);
	// 카메라 기본 위치를 z 방향으로 200만큼 위에 위치
	camera.position.z = 6000;

	// 카메라에 맞춰서 돌리기 컨트롤을 생성
	controls = new THREE.TrackballControls(camera);

	// 장면 생성
	scene = new THREE.Scene();
	// 장면 배경색
	scene.background = new THREE.Color(0xf0f0f0);
	// 장면에 AmbientLight(광역 빛) 추가
	scene.add( new THREE.AmbientLight( 0x505050 ) );

	// DirectionalLight(직선 형태의 빛) 추가
	var directionalLight = new THREE.DirectionalLight(0xffeedd);
	// 빛의 시작 점
	directionalLight.position.set(0, 0, 2);
	// 빛을 장면에 추가
	scene.add(directionalLight);

	// 렌더러
	renderer = new THREE.WebGLRenderer();
	renderer.setPixelRatio( window.devicePixelRatio );
	// 렌더러 크기
	renderer.setSize( window.innerWidth, window.innerHeight );
	// 해당 렌더러를 화면에 추가하여서 사용
	document.getElementById("threejsShow").appendChild( renderer.domElement );

	// 외부 모델 로더 생성
	const loader = new THREE.TDSLoader();
	// 해당 모델의 텍스쳐 경로 설정
	loader.setPath("/fudousan/resources/model/testchair/");
	// 모델 데이터 경로 설정 및 로딩 완료시 리스너 지정
	loader.load("/fudousan/resources/model/testchair/Armchair.3ds", (object) => {
		// x축 기준으로 -90도 회전
		object.rotation.x = Math.PI * -90 / 180;

		// 해당 모델을 가장 가깝게 에워싸는 육면체인 BoundingBox 생성
		var boundingBox = new THREE.Box3();
		boundingBox.setFromObject(object);

		// 바운딩 박스의 z 값을 이용하여 이동
		object.position.z = boundingBox.max.z;
		// 화면에 추가
		scene.add(object);
		// 완료 Alert 띄움
		alert("방이 생성됩니다.");

		objects.push(object);	// 해당 object는 Groups 객체로써 DragControls와 호환 X
		chair = object;
	});

	// 큐브 형태(가로 500, 세로 500, 높이 500)
	var cubeGeometry = new THREE.CubeGeometry(500, 500, 500);
	// 큐브 재질
	var cubeMaterial = new THREE.MeshLambertMaterial({ color: 0x1ec876 });
	// 큐브 메시(객체)
	var cube = new THREE.Mesh(cubeGeometry, cubeMaterial);
	// 큐브 y축 45도 회전
	cube.rotation.y = Math.PI * 45 / 180;
	// 장면에 큐브 추가
	//scene.add(cube);
	
	
	
	// 큐브를 배열에 저장
	objects.push(cube);
	// DragControls 생성(objects의 메시들을 camera를 기준으로 드래그)
	var dragControls = new THREE.DragControls( objects, camera, renderer.domElement );
	// 드래그 시작
	dragControls.addEventListener( 'dragstart', function ( event ) {
		// 화면 회전 불가
		controls.enabled = false; 
	} );
	// 드래그 종료
	dragControls.addEventListener( 'dragend', function ( event ) {
		// 화면 회전 가능
		controls.enabled = true;
	} );

	
	
	var planeGeometry = new THREE.PlaneGeometry(5000, 6000);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0xffff00, sid:THREE.DoubleSice});
	plane = new THREE.Mesh(planeGeometry, planeMaterial);
	scene.add(plane);


	document.getElementById("threejsShow").addEventListener('mousedown', this.onDocumentMouseDown, false);
	document.getElementById("threejsShow").addEventListener('mousemove', this.onDocumentMouseMove, false);
	document.getElementById("threejsShow").addEventListener('mouseup', this.onDocumentMouseUp, false);

}


function animate() {
	// 화면 회전 정보 갱신
	controls.update();
	// 화면을 렌더러에 그림
	renderer.render( scene, camera );
	// 다음 프레임 지정
	requestAnimationFrame( animate );
}


function onDocumentMouseDown(event) {
	if (chair != null) {
		// Get mouse position
		var mouseX = (event.clientX / window.innerWidth) * 2 - 1;
		var mouseY = -(event.clientY / window.innerHeight) * 2 + 1;
		
		mouse.x = mouseX;
		mouse.y = mouseY;
		
		raycaster.setFromCamera(mouse, camera);
		var intersects = raycaster.intersectObjects(chair.children);

		if (intersects.length > 0) {
			isSelected = true;
		}
	}
}

function onDocumentMouseMove(event) {
	// Get mouse position
	var mouseX = (event.clientX / window.innerWidth) * 2 - 1;
	var mouseY = -(event.clientY / window.innerHeight) * 2 + 1;
	
	mouse.x = mouseX;
	mouse.y = mouseY;

	raycaster.setFromCamera(mouse, camera);
	var intersects = raycaster.intersectObjects(chair.children);
	
	if (intersects.length > 0) {

		if (isSelected) {
			raycaster.setFromCamera(mouse, camera);
			
			var intersects = raycaster.intersectObjects([plane]);
			console.log('intersects  :' + intersects);
			for ( var i = 0; i < intersects.length; i++ ) {
				chair.position.copy(intersects[i].point);
			}
		}
	}
}

function onDocumentMouseUp(event) {
	isSelected = false;
	controls.enabled = true;
	console.log('chair : '+chair);
	
}

