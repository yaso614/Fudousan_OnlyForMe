/**
 * 3D 모델링 편집 화면 스크립트
 */
// 카메라, 씬, 렌더러
var camera, scene, renderer;
// 카메라 높이(z)
var cameraZ = 1000;
// 화면 가로 길이
var width = window.innerWidth;
// 화면 세로 길이
var height = window.innerHeight;
// 그림판(도면)
var plane;
// 그림판(사이즈)
var planeSize = 999999;
// Raycaster
var raycaster = new THREE.Raycaster();
// 마우스
var mouse = new THREE.Vector2();
// 현재 선택된 도구
// 0 : 벽 그리기
// 1 : 벽 지우기
var toolType = 0;
// 벽 시작 지점
var startPoint;
// 벽 끝 지점
var endPoint;
// 현재 그리는 중인가?
var isDrawing = false;
// 그리기 선
var drawingLine;
// 현재 화면 index
var curIndex = 0;
// 완성된 벽 들
var walls = [originalWalls];
// 완성된 점 들
var dots = [originalConnectors];
// 현재 그려진 벽
var sceneLines = [];
// 현재 그려진 점
var sceneDots = [];
// 보조 선
var supportLines = [];
// 배경 보조선
var backgroundLines = [];
// 단위 길이
var vectorPerLength = 10;
// 점 스냅 가중치
var pointSnapAdv = 1;
// 인터섹트 검사시 기존 점과의 가까운 한계 길이 값 (해당 값 이하는 값은 점으로 간주)
var closeTolerance = 20;
// 카메라 이동 단위
var cameraMoveValue = 100;

document.addEventListener("DOMContentLoaded", function(){
	//초기화
	init();
	//화면 그리기
	animate();
	
	redraw();
	});

function init() {
	// 카메라 생성 및 초기화
	camera = new THREE.PerspectiveCamera(60, width / height, 0.1, 100000);
	// 카메라 기본 위치를 z 방향으로 200만큼 위에 위치
	camera.position.z = cameraZ;

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
	renderer.setSize(width, height);
	// 해당 렌더러를 화면에 추가하여서 사용
	document.body.appendChild( renderer.domElement );

	var planeGeometry = new THREE.PlaneGeometry(planeSize, planeSize);
	var planeMaterial = new THREE.MeshBasicMaterial({color:0xffffff, sid:THREE.DoubleSice});
	plane = new THREE.Mesh(planeGeometry, planeMaterial);
	scene.add(plane);
	
	drawBackgroundLines();

	renderer.domElement.addEventListener('mousedown', this.onDocumentMouseDown, false);
	renderer.domElement.addEventListener('mousemove', this.onDocumentMouseMove, false);
	document.addEventListener('mouseup', this.onDocumentMouseUp, false);
	
	window.addEventListener('resize', this.onResize, false);
	window.addEventListener('keydown', this.onKeydown, false);
	
	document.getElementById("btn_back").addEventListener("click", back);
	document.getElementById("btn_forward").addEventListener("click", forward);
}

function onResize() {
	// 화면 가로 길이
	width = window.innerWidth;
	// 화면 세로 길이
	height = window.innerHeight;

	camera.aspect = width / height;
	camera.updateProjectionMatrix();
	
	renderer.setSize(width, height);
	
	drawBackgroundLines();
}

function onKeydown(event) {
	switch(event.key) {
	case 'w':
		camera.position.y += cameraMoveValue;
		break;
	case 'a':
		camera.position.x -= cameraMoveValue;
		break;
	case 's':
		camera.position.y -= cameraMoveValue;
		break;
	case 'd':
		camera.position.x += cameraMoveValue;
		break;
	case 'q':
		camera.position.z += cameraMoveValue;
		break;
	case 'e':
		camera.position.z -= cameraMoveValue;
		break;
	}
	//drawBackgroundLines();
}

function drawBackgroundLines() {
	for(var i = 0; i < backgroundLines.length; i++) {
		scene.remove(backgroundLines[i]);
	}
	var cameraToPlane = new THREE.Vector3().copy(camera.position);
	var z = 0;
	var y = 0;
	var x = 0;
	var xLength = Math.tan((90-camera.fov/2) / Math.PI * 180)*cameraToPlane.length()*3;
	var yLength = Math.tan((90-camera.fov/2) / Math.PI * 180)*cameraToPlane.length()/camera.aspect*3;
	for(var i = 0; i < xLength && i < planeSize; i += vectorPerLength) {
		var material = new THREE.LineBasicMaterial({color:0xdddddd});
		if(i % (vectorPerLength * 5) == 0) material = new THREE.LineBasicMaterial({color:0xaaaaaa, linewidth:1.5});
		if(i == 0 ) material = new THREE.LineBasicMaterial({color:0x000000, linewidth:2});

		if(i <= yLength) {
			var geometry = new THREE.Geometry();
			geometry.vertices.push(new THREE.Vector3(x-xLength,y+i,z));
			geometry.vertices.push(new THREE.Vector3(x+xLength,y+i,z));
			var line = new THREE.Line(geometry, material);
	
			scene.add(line);
			backgroundLines.push(line);
	
			geometry = new THREE.Geometry();
			geometry.vertices.push(new THREE.Vector3(x-xLength,y-i,z));
			geometry.vertices.push(new THREE.Vector3(x+xLength,y-i,z));
			line = new THREE.Line(geometry, material);
			
			scene.add(line);
			backgroundLines.push(line);
		}
		
		geometry = new THREE.Geometry();
		geometry.vertices.push(new THREE.Vector3(x+i,y-yLength,z));
		geometry.vertices.push(new THREE.Vector3(x+i,y+yLength,z));
		line = new THREE.Line(geometry, material);
		
		scene.add(line);
		backgroundLines.push(line);
		
		geometry = new THREE.Geometry();
		geometry.vertices.push(new THREE.Vector3(x-i,y-yLength,z));
		geometry.vertices.push(new THREE.Vector3(x-i,y+yLength,z));
		line = new THREE.Line(geometry, material);
		
		scene.add(line);
		backgroundLines.push(line);
	}
}

function animate() {
	// 화면을 렌더러에 그림
	renderer.render( scene, camera );
	// 다음 프레임 지정
	requestAnimationFrame( animate );
}

function onDocumentMouseDown(event) {
	moveMouse(event);
	
	raycaster.setFromCamera(mouse, camera);
	var intersects = raycaster.intersectObjects([plane]);

	if (intersects.length > 0 && toolType == 0) {
		isDrawing = true;
		
		// 마우스 끝 지점 생성
		endPoint = new THREE.Mesh(new THREE.CircleGeometry(vectorPerLength/2, 32), new THREE.MeshBasicMaterial({color:0x000000}));
		endPoint.position.copy(snapVector3(intersects[0].point, event.shiftKey, event.ctrlKey));
		
		drawingLine = new THREE.Line(new THREE.Geometry(), new THREE.LineBasicMaterial({color:0x000000}));
		scene.add(drawingLine);
	}
}

function onDocumentMouseMove(event) {
	moveMouse(event);

	raycaster.setFromCamera(mouse, camera);
	var intersects = raycaster.intersectObjects([plane]);

	if (intersects.length) {
		if (isDrawing) {
			// 선 그리기 및 끝점
			endPoint.position.copy(snapVector3(intersects[0].point, event.shiftKey, event.ctrlKey));
			scene.add(endPoint);
			// 그리기 선
			scene.remove(drawingLine);
			var geometry = new THREE.Geometry();
			geometry.vertices.push(startPoint.position);
			geometry.vertices.push(endPoint.position);
			drawingLine = new THREE.Line(geometry, new THREE.LineBasicMaterial({color:0x333333}));
			scene.add(drawingLine);
		} else {
			// 마우스 시작 지점 생성
			if(startPoint !=null) scene.remove(startPoint);
			startPoint = new THREE.Mesh(new THREE.CircleGeometry(vectorPerLength/2, 32), new THREE.MeshBasicMaterial({color:0x000000}));
			startPoint.position.copy(snapVector3(intersects[0].point, event.shiftKey, event.ctrlKey));
			scene.add(startPoint);
		}
	}
}

function onDocumentMouseUp(event) {
	moveMouse(event);

	if (event.altKey || toolType == 1) {
		raycaster.setFromCamera(mouse, camera);
		var intersects = raycaster.intersectObjects([plane]);
		if (intersects.length > 0) {
			var nearWall = findNearWall(intersects[0].point, 1);
			if(nearWall != null) {
				// 삭제 시작
				addNewStatus();
				
				var deleteWall = [];
				if(nearWall instanceof THREE.Vector3) {
					console.log("점 삭제 클릭!!!");
				} else {
					deleteWall.push(nearWall);
				}

				for(var d = 0; d < deleteWall.length; d++) {
					var index = deleteWall[d];
					// 해당 벽에서 사용하는 점 저장
					var sp = walls[curIndex][index].startPoint;
					var ep = walls[curIndex][index].endPoint;
					// 벽 제거
					walls[curIndex].splice(index, 1);
					
					// 해당 벽에서만 사용하는 점이 있으면 삭제
					for(var i = 0; i < walls[curIndex].length; i++ ) {
						if(sp == walls[curIndex][i].startPoint || sp == walls[curIndex][i].endPoint) {
							sp = -1;
						}
						if(ep == walls[curIndex][i].startPoint || ep == walls[curIndex][i].endPoint) {
							ep = -1;
						}
					}
					if(sp != -1) {
						console.log("sp 삭제 : " + sp);
						dots[curIndex].splice(sp, 1);
					}
					if(ep != -1) {
						if(sp != -1 && sp <= ep) {
							console.log("ep-1 삭제 : " + (ep-1));
							dots[curIndex].splice(ep-1, 1);
						} else {
							console.log("ep 삭제 : " + ep);
							dots[curIndex].splice(ep, 1);
						}
					}
					// 해당 인덱스 이후의 점 인덱스 변경
					for(var i = 0; i < walls[curIndex].length; i++ ) {
						if(sp != -1) {
							if(walls[curIndex][i].startPoint > sp) {
								walls[curIndex][i].startPoint -= 1;
								if(ep != -1 && walls[curIndex][i].startPoint > ep-1) {
									walls[curIndex][i].startPoint -= 1;
								}
							}
							if(walls[curIndex][i].endPoint > sp) {
								walls[curIndex][i].endPoint -= 1;
								if(ep != -1 && walls[curIndex][i].endPoint > ep-1) {
									walls[curIndex][i].endPoint -= 1;
								}
							}
						}
						if(ep != -1) {
							if(walls[curIndex][i].startPoint > ep) {
								walls[curIndex][i].startPoint -= 1;
								if(sp != -1 && sp <= ep) {
									walls[curIndex][i].startPoint -= 1;
								}
							}
							if(walls[curIndex][i].endPoint > ep) {
								walls[curIndex][i].endPoint -= 1;
								if(sp != -1 && sp <= ep) {
									walls[curIndex][i].endPoint -= 1;
								}
							}
						}
					}
				}
				// 삭제 종료
			}
		}
	} else {
		if (isDrawing && toolType == 0) {
			console.log("벽 생성 시작");
			// 벽 생성 시작
			addNewStatus();
			
			// 현재 위치에 대한 점이 존재하는가?
			// START
			var start = -1;
			var end = -1;
			for(var d = 0; d < dots[curIndex].length; d++) {
				if (checkEQ(dots[curIndex][d], startPoint.position)) {
					start = d;
				}
				if (checkEQ(dots[curIndex][d], endPoint.position)) {
					end = d;
				}
			}
			if(start == -1) {
				dots[curIndex].push(startPoint.position);
				start = dots[curIndex].length-1;
			}
			if(end == -1) {
				dots[curIndex].push(endPoint.position);
				end = dots[curIndex].length-1;
			}
			// END

			// 검사 전 점들의 갯수
			var beforeDots = dots[curIndex].length;
			// 새로 추가된 벽 갯수
			var newWalls = 0;
			// 새로 추가하는 선을 쪼개기 위한 정렬
			var array = [start, end];
			// 기존에 겹치는 지점이 있는지 검사
			for(var i = 0; i < walls[curIndex].length-newWalls; i++) {
				console.log("for i : " + i);
				var direction = endPoint.position.clone();
				var directionVector = direction.sub(startPoint.position);
				var ray = new THREE.Raycaster(startPoint.position, directionVector.normalize(), 0, startPoint.position.clone().sub(endPoint.position).length());
				ray.linePrecision = 1;
				
				var material = new THREE.LineBasicMaterial();
				var geometry = new THREE.Geometry();
				geometry.vertices.push(dots[curIndex][walls[curIndex][i].startPoint]);
				geometry.vertices.push(dots[curIndex][walls[curIndex][i].endPoint]);
	
				var intersectPoint = null;
				var rayIntersects = ray.intersectObjects([new THREE.Line(geometry, material)], true);
				if(rayIntersects.length > 0) {
					// 시작 포인트나 끝 포인트와 완전 동일 검사 (제외 시킴)
					/*if(!rayIntersects[0].point.equals(startPoint.position) && !rayIntersects[0].point.equals(endPoint.position)) {
						// 시작 포인트나 끝 포인트와 매우 가까운지 검사
						if(rayIntersects[0].point.manhattanDistanceTo(startPoint.position) < closeTolerance) {
							startPoint.position.copy(rayIntersects[0].point);
						}
						if(rayIntersects[0].point.manhattanDistanceTo(endPoint.position) < closeTolerance) {
							endPoint.position.copy(rayIntersects[0].point);
						}
						intersectPoint = rayIntersects[0].point;
						console.log(intersectPoint);
					}*/
					intersectPoint = rayIntersects[0].point;
				}
				console.log(intersectPoint);
				if(intersectPoint != null) {
					// 교차 포인트가 기존의 시작 점이나 끝 점일 경우, 무시하고, 그리는 선에서만 추가함
					var check = checkEQ(intersectPoint, dots[curIndex][walls[curIndex][i].startPoint]);
					if(check) {
						array.push(walls[curIndex][i].startPoint);
						console.log("startPoint" + walls[curIndex][i].startPoint);
						continue;
					}
					check |= checkEQ(intersectPoint, dots[curIndex][walls[curIndex][i].endPoint]);
					if(check) {
						array.push(walls[curIndex][i].endPoint);
						console.log("endPoint" + walls[curIndex][i].endPoint);
						continue;
					}
					if(!check) {
						// 교차점이 시작 점이나 끝 점일 경우 해당 점으로 교체, 아니면 새로 추가
						var curPointIndex = dots[curIndex].length;
						if(checkEQ(intersectPoint, startPoint.position)) {
							curPointIndex = start;
							console.log("start p" + curPointIndex);
						} else if(checkEQ(intersectPoint, endPoint.position)) {
							curPointIndex = end; 
							console.log("end p" + curPointIndex);
						} else {
							console.log("add dot");
							dots[curIndex].push(intersectPoint);
						}
						// 겹치는 벽이 존재할 경우 쪼개기
						if(walls[curIndex][i].startPoint != curPointIndex) {
							walls[curIndex].push({
								startPoint:walls[curIndex][i].startPoint,
								endPoint:curPointIndex
								});
						}
						if(walls[curIndex][i].endPoint != curPointIndex) {
							walls[curIndex].push({
								startPoint:walls[curIndex][i].endPoint,
								endPoint:curPointIndex
								});
						}

						walls[curIndex].splice(i, 1);
						
						i--;
						newWalls += 2;
					}
				}
			}
			for(var i = beforeDots; i < dots[curIndex].length; i++) {
				array.push(i);
			}
			console.dir(array);
			for(var i = 0; i < array.length; i++) {
				for(var j = i+1; j < array.length; j++) {
					if(array[i] == array[j]) {
						array.splice(j, 1);
						j -= 1;
						continue;
					}
					// 정렬하여 순차적으로 이어지게 하기
					if(dots[curIndex][array[i]].x < dots[curIndex][array[j]].x) {
						var t = array[i];
						array[i] = array[j];
						array[j] = t;
					}
				}
			}
			console.dir(array);
			// 정렬된 순서대로 시작부터 끝까지 벽 추가
			for(var i = 0; i < array.length-1; i++) {
				walls[curIndex].push({startPoint:array[i], endPoint:array[i+1]});
			}
			
			// 벽 생성 종료

			console.log("벽 생성 종료");
		}
	}
	isDrawing = false;
	scene.remove(startPoint);
	scene.remove(endPoint);
	scene.remove(drawingLine);
	redraw();
	
	
	raycaster.setFromCamera(mouse, camera);
	var intersects = raycaster.intersectObjects([plane]);

	if (intersects.length > 0) {
	}
}

function redraw() {
	console.log("redraw() start");
	console.log(curIndex);
	console.log(walls[curIndex]);
	console.log(dots[curIndex]);
	if(startPoint != null) scene.remove(startPoint);
	if(endPoint != null) scene.remove(endPoint);
	if(drawingLine != null) scene.remove(drawingLine);
	// 예전 벽 삭제
	for(var i = 0; i < sceneLines.length; i++) {
		scene.remove(sceneLines[i]);
	}
	sceneLines = [];
	// 예전 점 삭제
	for(var i = 0; i < sceneDots.length; i++) {
		scene.remove(sceneDots[i]);
	}
	sceneDots = [];
	// 화면에 벽 그리기
	for(var i = 0; i < walls[curIndex].length; i++) {
		var wallLineGeometry = new THREE.Geometry();
		
		var start = new THREE.Vector3();
		start.x = dots[curIndex][walls[curIndex][i].startPoint].x;
		start.y = dots[curIndex][walls[curIndex][i].startPoint].y;
		start.z = plane.position.z;
		
		var end = new THREE.Vector3();
		end.x = dots[curIndex][walls[curIndex][i].endPoint].x;
		end.y = dots[curIndex][walls[curIndex][i].endPoint].y;
		end.z = plane.position.z;
		
		wallLineGeometry.vertices.push(start);
		wallLineGeometry.vertices.push(end);
		sceneLines.push(new THREE.Line(wallLineGeometry, new THREE.LineBasicMaterial({color:0x0000ff})));
	}
	// 갱신된 벽 추가
	for(var i = 0; i < sceneLines.length; i++) {
		scene.add(sceneLines[i]);
	}
	
	// 화면에 점 그리기
	for(var i = 0; i < dots[curIndex].length; i++) {
		var geometry = new THREE.CircleGeometry( vectorPerLength/2, 32 );
		var material = new THREE.MeshBasicMaterial( { color: 0xffff00 } );
		var circle = new THREE.Mesh( geometry, material );
		circle.position.copy(dots[curIndex][i]);
		sceneDots.push(circle);
	}
	// 갱신된 점 추가
	for(var i = 0; i < sceneDots.length; i++) {
		scene.add(sceneDots[i]);
	}
	console.log("redraw() end");
}

function moveMouse(event) {
	// Get mouse position
	var mouseX = (event.clientX / window.innerWidth) * 2 - 1;
	var mouseY = -(event.clientY / window.innerHeight) * 2 + 1;
	
	mouse.x = mouseX;
	mouse.y = mouseY;
}

function snapVector3(vector3, shiftKey, ctrlKey, altKey) {
	vector3.z = plane.position.z;
	
	if(walls[curIndex].length > 0) {
		var v3 = findNearWall(vector3, 0);
		if(v3 != null) vector3 = v3;
	}
	
	if(shiftKey == null || shiftKey == false) {
		// 쉬프트 키가 눌리지 않았으면 스냅 하지 않는다.
		return vector3;
	}
	
	var v3 = new THREE.Vector3().copy(vector3);
	v3.x = Math.round(vector3.x / vectorPerLength) * vectorPerLength;
	v3.y = Math.round(vector3.y / vectorPerLength) * vectorPerLength;
	
	if(ctrlKey == null || ctrlKey == false) {
		// 컨트롤 키가 눌리지 않았으면, 처음 위치값 만큼 보정해준다.
		v3.x += startPoint.position.x%vectorPerLength;
		v3.y += startPoint.position.y%vectorPerLength;
	}
	
	return v3;
}

function findNearWall(vector3, returnType) {
	
	var minLength;
	var minIndex;
	var minPoint;
	var minWall;

	// AB의 선과 C의 점과의 가장 가까운 위치 및 거리
	// http://gpgstudy.com/forum/viewtopic.php?t=22736
	for(var i = 0; i < walls[curIndex].length; i++) {
		// 점 스냅 우선순위
		// 현재 점 근처인가?
		if (returnType == 0 && vector3.clone().manhattanDistanceTo(dots[curIndex][walls[curIndex][i].startPoint]) < closeTolerance*pointSnapAdv) {
							// && new THREE.Vector3().copy(vector3).sub(dots[curIndex][walls[curIndex][i].startPoint]).length() < vectorPerLength*pointSnapAdv) {
			return dots[curIndex][walls[curIndex][i].startPoint];
		}
		if (returnType == 0 && vector3.clone().manhattanDistanceTo(dots[curIndex][walls[curIndex][i].endPoint]) < closeTolerance*pointSnapAdv) {
							//&& new THREE.Vector3().copy(vector3).sub(dots[curIndex][walls[curIndex][i].endPoint]).length() < vectorPerLength*pointSnapAdv) {
			return dots[curIndex][walls[curIndex][i].endPoint];
		}
		
		// 각 벽 부터 거리 계산하여 최소 거리의 벽 계산
		// 우선 A->B
		var ab = new THREE.Vector3().subVectors(dots[curIndex][walls[curIndex][i].endPoint], dots[curIndex][walls[curIndex][i].startPoint]);
		// A->B 길이 저장
		var abLength = ab.length();
		// 단위 벡터 로 변환
		ab.normalize();
		// A->C
		var ac = new THREE.Vector3().subVectors(vector3, dots[curIndex][walls[curIndex][i].startPoint]);
		// dot product(투영된 벡터의 길이)
		var dp = ab.dot(ac);
		// 가장 가까운 점
		var d = new THREE.Vector3().copy(dots[curIndex][walls[curIndex][i].startPoint]).add(ab.multiplyScalar(dp));
		// 길이
		var l = new THREE.Vector3().copy(d).sub(vector3).length();
		// 선분안에 들어가는가?
		var da = new THREE.Vector3().copy(d).sub(dots[curIndex][walls[curIndex][i].startPoint]);
		var db = new THREE.Vector3().copy(d).sub(dots[curIndex][walls[curIndex][i].endPoint]);
		var dadbLength = da.length() + db.length();
		if(dadbLength > abLength - 0.00001 && dadbLength < abLength + 0.00001){
			// 제일 짧은가?
			if (minPoint == null || l < minLength) {
				minLength = l;
				minIndex = i;
				minPoint = d;
				minWall = walls[curIndex][i];
			}
		}
	}
	// 일정 거리 안인가?
	if (minPoint != null && new THREE.Vector3().copy(minPoint).sub(vector3).length() < vectorPerLength) {
		switch(returnType) {
		case 0:
			return minPoint;
		case 1:
			return minIndex;
		}
	} 

	// 보조선 처리
	if (supportLines != null) {
		for (var i = 0; i < supportLines.length; i++) {
			scene.remove(supportLines[i]);
		}
		supportLines = [];
	}
	
	var nearDots = [];
	
	for(var i = 0; i < walls[curIndex].length; i++) {
		// 우선 A->B
		var ab = new THREE.Vector3().subVectors(dots[curIndex][walls[curIndex][i].endPoint], dots[curIndex][walls[curIndex][i].startPoint]);
		// A->B 길이 저장
		var abLength = ab.length();
		// 단위 벡터 로 변환
		ab.normalize();
		// A->C
		var ac = new THREE.Vector3().subVectors(vector3, dots[curIndex][walls[curIndex][i].startPoint]);
		// dot product(투영된 벡터의 길이)
		var dp = ab.dot(ac);
		// 가장 가까운 점
		var d = new THREE.Vector3().copy(dots[curIndex][walls[curIndex][i].startPoint]).add(ab.multiplyScalar(dp));
		// 길이
		var l = new THREE.Vector3().copy(d).sub(vector3).length();
		
		if (l < vectorPerLength) {
			nearDots.push(d);
			var lineGeometry = new THREE.Geometry();
			// AC
			//var ac = new THREE.Vector3().subVectors(d, dots[walls[i].startPoint]);
			// BC
			//var bc = new THREE.Vector3().subVectors(d, dots[walls[i].endPoint]);
			
			//if(ac.length() < bc.length()) {
				lineGeometry.vertices.push(dots[curIndex][walls[curIndex][i].startPoint]);
			//} else {
				lineGeometry.vertices.push(dots[curIndex][walls[curIndex][i].endPoint]);
			//}
			lineGeometry.vertices.push(d);
			var lineMaterial = new THREE.LineDashedMaterial({
				color: 0xbb00bb,
				dashSize: 1,
				gapSize: 0.5,
			});
			var supportLine = new THREE.Line(lineGeometry, lineMaterial);
			supportLines.push(supportLine);
		}
	}
	for(var i=0; i < supportLines.length; i++) {
		scene.add(supportLines[i]);
	}
	
	if (nearDots.length > 0) {
		for(var i=0; i< nearDots.length; i++) {
			switch(returnType) {
			case 0:
				return nearDots[i];
			}
		}
	}
	
	return null;
}

function checkEQ(vector, vector1) {
	/*var value = 10;
	var v = new THREE.Vector3().copy(vector).multiplyScalar(value).floor().divideScalar(value);
	var v1 = new THREE.Vector3().copy(vector1).multiplyScalar(value).floor().divideScalar(value);*/
	//return vector.equals(vector1);
	return vector.manhattanDistanceTo(vector1) < closeTolerance;
}

function getIntersectPoint(AP1, AP2, BP1, BP2) {
	// 일정 이하의 소숫점 버리기
	/*var value = 10;
	var AP1 = new THREE.Vector3().copy(AP1).multiplyScalar(value).floor().divideScalar(value);
	var AP2 = new THREE.Vector3().copy(AP2).multiplyScalar(value).floor().divideScalar(value);
	var BP1 = new THREE.Vector3().copy(BP1).multiplyScalar(value).floor().divideScalar(value);
	var BP2 = new THREE.Vector3().copy(BP2).multiplyScalar(value).floor().divideScalar(value);*/
	
	if(AP1 == BP1 || AP1 == BP2 || AP2 == BP1 || AP2 == BP2) return null;
	// http://www.gisdeveloper.co.kr/?p=89
	var t;
	var s; 
	var under = (BP2.y-BP1.y)*(AP2.x-AP1.x)-(BP2.x-BP1.x)*(AP2.y-AP1.y);
	if(under==0) return null;
	
	var _t = (BP2.x-BP1.x)*(AP1.y-BP1.y) - (BP2.y-BP1.y)*(AP1.x-BP1.x);
	var _s = (AP2.x-AP1.x)*(AP1.y-BP1.y) - (AP2.y-AP1.y)*(AP1.x-BP1.x); 
	
	t = _t/under;
	s = _s/under; 
	
	if(t<0 || t>1 || s<0 || s>1) return null;
	if(_t==0 && _s==0) return null; 
	
	var IP = new THREE.Vector3();
	IP.x = AP1.x + t * (AP2.x-AP1.x);
	IP.y = AP1.y + t * (AP2.y-AP1.y);
	IP.z = plane.position.z;
	
	return IP;
}

function addNewStatus() {
	walls.splice(curIndex+1);
	dots.splice(curIndex+1);
	
	walls.push([]);
	for(var i = 0; i < walls[curIndex].length; i++) {
		walls[walls.length-1].push({startPoint:walls[curIndex][i].startPoint, endPoint:walls[curIndex][i].endPoint});
	}
	dots.push(dots[curIndex].slice());
	curIndex++;
}

function changeTool(type) {
	toolType = type;
}

function reset(event) {
	if(confirm("정말 초기화 하시겠습니까?(취소 불가능)")) {
		dots = [[]];
		walls = [[]];
		curIndex = 0;
		redraw();
	}
}

function back(event) {
	event.preventDefault();
	event.stopPropagation();
	if(curIndex > 0) curIndex -= 1;
	redraw();
	
}

function forward(event) {
	event.preventDefault();
	event.stopPropagation();
	if(curIndex < walls.length-1) curIndex += 1;
	redraw();
}

function save() {
	console.log("save()");
	// jQuery
	var dotsData = [];
	for(var i = 0; i < dots[curIndex].length; i++) {
		dotsData.push({connectorId:i, x:dots[curIndex][i].x, y:dots[curIndex][i].y});
	}
	var wallsData = [];
	for(var i = 0; i < walls[curIndex].length; i++) {
		wallsData.push({startPoint:walls[curIndex][i].startPoint, endPoint:walls[curIndex][i].endPoint});
	}

	$.ajax({
		url:"./save",
		type:"POST",
		data:{
			roomId : roomId,
			dots : JSON.stringify(dotsData),
			walls : JSON.stringify(wallsData)
		},
		success:function(data) {
			if(data || data == "true") {
				alert("저장 성공");
			} else {
				alert("저장에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			alert("저장 중 오류가 발생하였습니다.");
		}
	});
}