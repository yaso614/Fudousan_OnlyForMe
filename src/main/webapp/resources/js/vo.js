// 모든 파라미터 1번째는 RoomItem
var CommandCallBack = {
	onCreate : undefined,		// 아이템 생성
	onDelete : undefined,		// 아이템 삭제
	onMove : undefined,			// 아이템 이동(드래그)
	onItemChange : undefined,	// 아이템 변경(아이템 상세 변경 적용, 이동과 회전)
	onModelLoad : undefined,	// 아이템 모델파일 로딩 완료
	onModelError: undefined,	// 아이템 모델파일 로딩 에러
	onSelect : undefined,		// 아이템 선택
	onDeselect : undefined,		// 아이템 선택 해제
	onReset : undefined,		// 룸 리셋
	onSnapShot : undefined,		// 룸 스냅샷 성공, 1번째 파라미터 해당 스냅샷 url
	onForward: undefined,		// 앞으로가기
	onBack : undefined,			// 뒤로가기
	onFloorTexture : undefined,	// 천장 텍스쳐 변경, 1번째 파라미터로 TextureId
	onCeilTexture : undefined,	// 바닥 텍스쳐 변경, 1번째 파라미터로 TextureId
	onWallTexture : undefined,	// 벽 텍스쳐 변경, 1번째 파라미터(RoomWall), 2번째 파라미터(TextureId), 3번째 파라미터(벽 인덱스, 2 또는 3)
	onHeightChange : undefined	// 방 높이 변경, 1번째 파라미터(방 높이)
}

function Command() {
	this.name = "";
	this.onDoRoomItem = null;
	this.onRedoRoomItem = null;
	this.onDo = null;
	this.onRedo = null;
}

/**
 * Item VO
 * @returns
 */
function Item() {
	this.fileDirectory = "";
	this.itemId = -1;
	this.itemName = "";
	this.itemType = new ItemType();
	this.modelFileName = "";
	this.refSiteSet = [];
	this.text = "";
	this.itemScale = 1;
	this.itemRotateX = 0;
	this.itemRotateY = 0;
	this.itemRotateZ = 0;
	this.itemX = 0;
	this.itemY = 0;
	this.itemZ = 0;
	this.clone = function() {
		var c = new Item();
		c.fileDirectory = this.fileDirectory;
		c.itemId = this.itemId;
		c.itemName = this.itemName;
		c.itemType = this.itemType.clone();
		c.modelFileName = this.modelFileName;
		c.refSiteSet = this.refSiteSet.slice();
		c.text = this.text;
		c.itemScale = this.itemScale;
		c.itemRotateX = this.itemRotateX;
		c.itemRotateY = this.itemRotateY;
		c.itemRotateZ = this.itemRotateZ;
		c.itemX = this.itemX;
		c.itemY = this.itemY;
		c.itemZ = this.itemZ;
		return c;
	}
}

/**
 * ItemType VO
 * @param itemTypeId
 * @param itemTypeName
 * @returns
 */
function ItemType(itemTypeId, itemTypeName) {
	this.itemTypeId = itemTypeId;
	this.itemTypeName = itemTypeName;
	this.clone = function() {
		var c = new ItemType(this.itemTypeId, this.itemTypeName);
		return c;
	}
}

/**
 * RefSite VO
 * @param creDate
 * @param id
 * @param itemId
 * @param text
 * @param url
 * @returns
 */
function RefSite(creDate, id, itemId, text, url) {
	this.creDate = creDate;
	this.id = id;
	this.itemId = itemId;
	this.text = text;
	this.url = url;
}

/**
 * Roomitem VO
 * @returns
 */
function RoomItem() {
	this.color = 0;
	this.item = new Item();
	this.roomId = -1;
	this.roomItemId = -1;
	this.rotateX = 0;
	this.rotateY = 0;
	this.rotateZ = 0;
	this.x = 0;
	this.y = 0;
	this.z = 0;
	this.clone = function() {
		var c = new RoomItem();
		c.color = this.color;
		c.item = this.item.clone();
		c.roomId = this.roomId;
		c.roomItemId = this.roomItemId;
		c.rotateX = this.rotateX;
		c.rotateY = this.rotateY;
		c.rotateZ = this.rotateZ;
		c.x = this.x;
		c.y = this.y;
		c.z = this.z;
		return c;
	}
}

/**
 * js object를 roomItem vo로 변환
 * @param roomitemObj
 * @returns roomItem VO
 */
function objToRoomItem(roomitemObj) {
	
	console.log("다음의 오브젝트를 roomitem으로 변환 하려합니다.");
	console.dir(roomitemObj);
	
	var roomItem = new RoomItem();
	roomItem.color = roomitemObj.color;
	roomItem.roomId = roomitemObj.roomId;
	roomItem.roomItemId = roomitemObj.roomItemId;
	roomItem.rotateX = roomitemObj.rotateX;
	roomItem.rotateY = roomitemObj.rotateY;
	roomItem.rotateZ = roomitemObj.rotateZ;
	roomItem.x = roomitemObj.x;
	roomItem.y = roomitemObj.y;
	roomItem.z = roomitemObj.z;
	roomItem.item = objToItem(roomitemObj.item);
	return roomItem;
	
}

/**
 * js object를 item vo로 변환
 * @param itemObj
 * @returns item VO
 */
function objToItem(itemObj) {
	
	console.log("다음의 오브젝트를 item으로 변환 하려합니다.");
	console.dir(itemObj);
	
	var item = new Item();
	item.fileDirectory = itemObj.fileDirectory;
	item.itemId = itemObj.itemId;
	item.itemName = itemObj.itemName;
	item.itemType = new ItemType(itemObj.itemType.itemTypeId, itemObj.itemType.itemTypeName);
	item.modelFileName = itemObj.modelFileName;
	item.text = itemObj.text;
	item.itemScale = itemObj.itemScale;
	item.itemRotateX = itemObj.itemRotateX;
	item.itemRotateY = itemObj.itemRotateY;
	item.itemRotateZ = itemObj.itemRotateZ;
	item.itemX = itemObj.itemX;
	item.itemY = itemObj.itemY;
	item.itemZ = itemObj.itemZ;
	$.each(itemObj.refSiteSet, function(index, obj) {
		item.refSiteSet.push(new RefSite(obj.creDate, obj.id, obj.itemId, obj.text, obj.url));
	});
	return item;
}

function RoomWall(backTextureURL, frontTextureURL, roomWallId, roomId, c1Id, c1x, c1y, c2Id, c2x, c2y, type) {
	this.backTextureURL = backTextureURL;
	this.frontTextureURL = frontTextureURL;
	this.roomWallId = roomWallId;
	this.roomId = roomId;
	this.roomWallConnector1 = new RoomWallConnector(c1Id, c1x, c1y);
	this.roomWallConnector2 = new RoomWallConnector(c2Id, c2x, c2y);
	this.type = type;
}

function RoomWallConnector(id, x, y) {
	this.connectorId = id;
	this.x = x;
	this.y = y;
}