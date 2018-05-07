/**
 * 
 */
$(function() {
	if ( $( "#ax" ).length ) {

		var value = parseInt( $( "#ax" ).text(), 10 );
		$( "#ax" ).empty().slider({
			value: value,
			min: 0,
			max: 360,
			step: 1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.rotation.x = ui.value*Math.PI/180;
				$("input[name='itemRotateX']").val(ui.value);
			}
		});
		var value = parseInt( $( "#ay" ).text(), 10 );
		$( "#ay" ).empty().slider({
			value: value,
			min: 0,
			max: 360,
			step: 1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.rotation.y = ui.value*Math.PI/180;
				$("input[name='itemRotateY']").val(ui.value);
			}
		});
		var value = parseInt( $( "#az" ).text(), 10 );
		$( "#az" ).empty().slider({
			value: value,
			min: 0,
			max: 360,
			step: 1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.rotation.z = ui.value*Math.PI/180;
				$("input[name='itemRotateZ']").val(ui.value);
			}
		});
		var value = parseInt( $( "#px" ).text(), 10 );
		$( "#px" ).empty().slider({
			value: value,
			min: -1000,
			max: 1000,
			step: 0.1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.position.x = ui.value;
				$("input[name='itemX']").val(ui.value);
			}
		});
		var value = parseInt( $( "#py" ).text(), 10 );
		$( "#py" ).empty().slider({
			value: value,
			min: -1000,
			max: 1000,
			step: 0.1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.position.y = ui.value;
				$("input[name='itemY']").val(ui.value);
			}
		});
		var value = parseInt( $( "#pz" ).text(), 10 );
		$( "#pz" ).empty().slider({
			value: value,
			min: -1000,
			max: 1000,
			step: 0.1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.position.z = ui.value;
				$("input[name='itemZ']").val(ui.value);
			}
		});
		var value = parseInt( $( "#scale" ).text(), 10 );
		$( "#scale" ).empty().slider({
			value: value,
			min: 0,
			max: 100,
			step: 0.1,
			orientation: "horizontal",
			range: "min",
			animate: true,
			slide: function( event, ui ) {
				curObject.scale.x = ui.value;
				curObject.scale.y = ui.value;
				curObject.scale.z = ui.value;
				$("input[name='itemScale']").val(ui.value);
			}
		});
	}
});

// 카메라, 씬, 렌더러, 카메라 컨트롤
var camera, scene, renderer, controls;
// 화면 가로 길이
var width = window.innerWidth;
// 화면 세로 길이
var height = window.innerHeight;
// 지면들
var plane = [];
// 지면(사이즈)
var planeSize = 100;
// Raycaster
var raycaster = new THREE.Raycaster();
// 마우스
var mouse = new THREE.Vector2();
// 현재 아이템
var curObject = null;
// 아이템 초기치
var objectInitValues;
// 미리보기 창
var previewObject;

var originalSiteList = "";

// 배경 표시 여부
var backgroundEnable = true;

// 축 헬퍼
var axesHelper;

function addsitecolumn() {
	var column = "<div class='form-group'>";
	column += "<div class='form-group'>";
	column += "<label>タイトル</label>";
	column += "<input class='form-control' name='titles' type='text'>";
	column +=  "</div>";
	column += "<label>アドレス</label>";
	column += "<input class='form-control' name='sites' type='text'>";
	column += "</div>";
	$("#sitelist").append(column);
}

function formreset() {
	if (confirm("リセットなさいますか。")) {
		$("#sitelist").html(originalSiteList);
		return true;
	}
	return false;
}

function backupSiteList() {
	originalSiteList = $("#sitelist").html();
	//$("#sitelist").html(originalSiteList);
}

function delSite(id) {
	$("#s"+id).remove();
}

function threeJSInit(x, y, z, rx, ry, rz, s, itemId, modelFileName) {
	//초기화
	init();
	
	objectInitValues = {
			x:x,
			y:y,
			z:z,
			rx:rx,
			ry:ry,
			rz:rz,
			s:s
	};
	
	//화면 그리기
	animate();
	
	place(itemId, modelFileName);
}

function init() {
	previewObject = document.getElementById("preview");
	width = $(previewObject).width();
	height = $(previewObject).height();
	// Loader Cache Enabled
	THREE.Cache.enabled = true;
	// 카메라 생성 및 초기화
	camera = new THREE.PerspectiveCamera(60, width / height, 0.1, 100000);
	camera.position.y = 1000;
	camera.lookAt(0, 0, 0);
	
	// 장면 생성
	scene = new THREE.Scene();
	// 장면 배경색
	scene.background = new THREE.Color(0xFFFFFF);
	// 장면에 AmbientLight(광역 빛) 추가
	scene.add( new THREE.AmbientLight( 0x505050 ) );

	// DirectionalLight(직선 형태의 빛) 추가
	var directionalLight = new THREE.DirectionalLight(0xffeedd);
	// 빛의 시작 점
	directionalLight.position.set(0, 0, 2);
	// 빛을 장면에 추가
	scene.add(directionalLight);

	// 렌더러
	renderer = new THREE.WebGLRenderer({
        preserveDrawingBuffer: true
    });
	renderer.setPixelRatio( window.devicePixelRatio );
	// 렌더러 크기
	renderer.setSize(width, height);
	// 해당 렌더러를 화면에 추가하여서 사용
	previewObject.appendChild( renderer.domElement );

	// controls
	controls = new THREE.OrbitControls( camera, renderer.domElement );
	controls.enableKeys = false;

	var planeGeometry = new THREE.PlaneGeometry(planeSize, planeSize);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0x002200, sid:THREE.DoubleSice});
	plane.push(new THREE.Mesh(planeGeometry, planeMaterial));
	plane[0].rotateX(-90 * Math.PI / 180);
	scene.add(plane[0]);
	
	var planeGeometry = new THREE.PlaneGeometry(planeSize*2, planeSize*2);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0x004400, sid:THREE.DoubleSice});
	plane.push(new THREE.Mesh(planeGeometry, planeMaterial));
	plane[1].rotateX(-90 * Math.PI / 180);
	plane[1].position.y -= 0.5;
	scene.add(plane[1]);
	
	var planeGeometry = new THREE.PlaneGeometry(planeSize*3, planeSize*3);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0x006600, sid:THREE.DoubleSice});
	plane.push(new THREE.Mesh(planeGeometry, planeMaterial));
	plane[2].rotateX(-90 * Math.PI / 180);
	plane[2].position.y -= 1;
	scene.add(plane[2]);
	
	var planeGeometry = new THREE.PlaneGeometry(planeSize*4, planeSize*4);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0x008800, sid:THREE.DoubleSice});
	plane.push(new THREE.Mesh(planeGeometry, planeMaterial));
	plane[3].rotateX(-90 * Math.PI / 180);
	plane[3].position.y -= 1.5;
	scene.add(plane[3]);
	
	var planeGeometry = new THREE.PlaneGeometry(planeSize*5, planeSize*5);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0x00AA00, sid:THREE.DoubleSice});
	plane.push(new THREE.Mesh(planeGeometry, planeMaterial));
	plane[4].rotateX(-90 * Math.PI / 180);
	plane[4].position.y -= 2;
	scene.add(plane[4]);
	
	window.addEventListener('resize', this.onResize, false);
	
	axesHelper = new THREE.AxesHelper( 1000 );
	scene.add( axesHelper );
}

function animate() {
	// 다음 프레임 지정
	requestAnimationFrame( animate );
	// 화면 회전 정보 갱신
	controls.update(); // only required if controls.enableDamping = true, or if controls.autoRotate = true

	renderer.render( scene, camera );
}

function onResize() {
	width = $(previewObject).width();
	height = $(previewObject).height();
	// 카메라 비율 재정의
	camera.aspect = width / height;
	camera.updateProjectionMatrix();
	// 렌더러 화면 크기 변경
	renderer.setSize(width, height);
}

function place(itemId, modelFileName) {
	$("#blocker").show();
	// 외부 모델 로더 생성
	const loader = new THREE.TDSLoader();
	// 해당 모델의 텍스쳐 경로 설정
	loader.setPath(itemId+"/");
	// 모델 데이터 경로 설정 및 로딩 완료시 리스너 지정
	loader.load(itemId+"/"+modelFileName, (object) => {
		scene.add( object );

		curObject = object;
		
		object.position.x = objectInitValues.x;
		object.position.y = objectInitValues.y;
		object.position.z = objectInitValues.z;
		object.rotateX(objectInitValues.rx * Math.PI / 180);
		object.rotateY(objectInitValues.ry * Math.PI / 180);
		object.rotateZ(objectInitValues.rz * Math.PI / 180);
		object.scale.x = objectInitValues.s;
		object.scale.y = objectInitValues.s;
		object.scale.z = objectInitValues.s;
		
		$("#blocker").hide();
	}, (object)=>{$("#blocker").hide();});
	
}

function onModelFileChange(itemId) {
	place(itemId, $("#model").val());
}

function backgroundToggle() {
	if ( backgroundEnable ) {
		for ( var i = 0; i < plane.length; i++ ) {
			scene.remove(plane[i]);
		}
		scene.remove(axesHelper);
	} else {
		for ( var i = 0; i < plane.length; i++ ) {
			scene.add(plane[i]);
		}
		scene.add(axesHelper);
	}
	backgroundEnable = !backgroundEnable;
	
}