package com.real.fudousan.room.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.real.fudousan.common.util.FileService;
import com.real.fudousan.estate.service.EstateService;
import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.room.controller.RoomController;
import com.real.fudousan.room.dao.RoomDAO;
import com.real.fudousan.room.vo.Room;
import com.real.fudousan.roomwall.dao.RoomWallDAO;
import com.real.fudousan.roomwall.service.RoomWallService;
import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.roomwall.vo.RoomWallConnector;

@Service
public class RoomService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
	
	@Autowired
	private RoomDAO dao;
	
	@Autowired
	private RoomWallService wallService;
	
	@Autowired
	private EstateService estateService;
	
	private final String snapShotDir = "/snapshot/";
	
	/**
	 * 자기 매물 정보 검색
	 * @param roomSearch
	 * @param memberId
	 * @return 검색자의 매물정보중 검색대상
	 */
	public List<Room> showMyRoom(String roomSearch, int memberId) {
		List<Room> srlist = null;
		srlist = dao.searchRoomInfo(roomSearch, memberId);
		/*logger.info("srlist에 들어온값 "+srlist.get(0));*/
		return srlist;
	}
	
	/**
	 * 자기가 작성한 매물 리스트 보기
	 * @param memberId
	 * @return 사용자가 작성한  매물들의 리스트
	 */
	public List<Room> showAllRoom(Integer memberId) {
		List<Room> rlist = null;
		rlist = dao.allMyRoom(memberId);
		return rlist;
	}
	
	/**
	 * 사용자 페이지에서 자기가 작성한 매물 리스트 보기
	 * @param memberId
	 * @param isRealRoom 실제로 존재하는 방인가 아닌가
	 * @return 사용자가 작성한  매물들의 리스트
	 */
	
	public List<Room> selectEstateRoom(Integer estateId, int startRecord, int coutPerPage ) {
		List<Room> rlist = null;
		rlist = dao.selectEstateRoom(estateId, startRecord, coutPerPage);
		return rlist;
	}
	/**
	 * 메물 상세보기 페이지에서 매물 이름에 해당하는 3d 방 리스트 보기
	 * @param estateId
	 * @param startRecord
	 * @param coutPerPage
	 * @return 3d 방 리스트 보기 리스트
	 */
	
	public List<Room> showAllRoom(Integer memberId, boolean isRealRoom) {
		logger.info("showAllRoom("+memberId+", "+isRealRoom+") Start");
		List<Room> result = null;
		result = dao.selectByIdOnReal(memberId, isRealRoom);
		logger.info("showAllRoom("+memberId+", "+isRealRoom+") End");
		return result;
	}
	

	/**
	 * 3D 모델링 생성
	 * @param room
	 * @return 해당 모델링 ID
	 */
	@Transactional
	public int createRoom(Room room) {
		logger.info("createRoom(" + room + ") Start");
		
		int roomId = dao.insert(room);
		Room r = dao.select(roomId);
		logger.debug("room : " + r);
		room.setHeight(r.getHeight());
		
		// 실제 방이 있는 룸일 경우, 대표 방의 벽을 복사한다.
		if(r.getEstate() != null && r.getEstate().getEstateId() != null && r.getEstate().getBaseRoomId() != null) {
			int baseRoomId = r.getEstate().getBaseRoomId();
			Map<String, List<?>> map = wallService.getWallAndConnector(baseRoomId);
			logger.debug("BaseRoomId : " + baseRoomId);
			
			List<RoomWall> roomWallList = (List<RoomWall>) map.get("walls");
			Map<Integer, RoomWallConnector> roomWallConnectorMap = new HashMap<>();
			for(RoomWallConnector connector : (List<RoomWallConnector>)map.get("connectors")) {
				roomWallConnectorMap.put(connector.getConnectorId(), connector.clone());
			}
			wallService.save(roomId, roomWallList, roomWallConnectorMap);
		}
		
		logger.info("createRoom(" + room + ") End");
		return roomId;
	}
	
	/**
	 * 해당 매물의 대표방 생성
	 * @param memberId
	 * @param estateId
	 * @return
	 */
	@Transactional
	public int createBaseRoom(int memberId, int estateId) {
		logger.info("createBaseRoom(" + memberId + ", "+estateId+") Start");
		int result = -1;
		
		Room room = new Room();
		room.setMemberId(memberId);
		room.setRoomPublic(1);
		Estate estate = new Estate();
		estate.setEstateId(estateId);
		room.setEstate(estate);
		
		if ( ( result = dao.insert( room ) ) >= 0 ) {
			
			// 대표방 설정
			if ( !estateService.updateBaseRoomId( estateId, result ) ) {
				
				result = -1;
				
			}
			
		}
		
		
		logger.info("createBaseRoom(" + memberId + ", "+estateId+") End");
		return result;
	}
	
	/**
	 * 천장 텍스쳐 변경
	 * @param roomId
	 * @param textureId
	 * @return
	 */
	public boolean changeCeilingTexture(int roomId, int textureId) {
		logger.info("changeCeilingTexture("+roomId+", "+textureId+") Start");
		boolean result = false;
		result = dao.updateCeilingTexture( roomId,  textureId);
		logger.info("changeCeilingTexture("+roomId+", "+textureId+") End");
		return result;
	}
	
	/**
	 * 바닥 텍스쳐 변경
	 * @param roomId
	 * @param textureId
	 * @return
	 */
	public boolean changeFloorTexture(int roomId, int textureId) {
		logger.info("changeCeilingTexture("+roomId+", "+textureId+") Start");
		boolean result = false;
		result = dao.updateFloorTexture( roomId,  textureId);
		logger.info("changeCeilingTexture("+roomId+", "+textureId+") End");
		return result;
	}
	
	/**
	 * 해당 방 아이디에 해당하는 모든 방을 가져온다.
	 * @param roomIds
	 * @return
	 */
	public List<Room> getRooms(Set<Integer> roomIds) {
		logger.info("getRooms("+roomIds+") Start");
		List<Room> result = null;
		result = dao.select(roomIds);
		logger.info("getRooms("+roomIds+") End");
		return result;
	}
	
	/**
	 * 특정 회원이 작성한 특정 방의 공개 여부를 변경한다.
	 * @param memberId
	 * @param roomId
	 * @param roomPublic
	 * @return
	 */
	public boolean changeRoomPublic(int memberId, int roomId, int roomPublic) {
		logger.info("changeRoomPublic("+memberId+", "+roomId+", "+roomPublic+") Start");
		boolean result = false;
		result = dao.updateRoomPublic(memberId, roomId, roomPublic);
		logger.info("changeRoomPublic("+memberId+", "+roomId+", "+roomPublic+") End");
		return result;
	}
	
	/**
	 * 특정 회원이 특정 방 삭제
	 * @param memberId
	 * @param roomId
	 * @return
	 */
	public boolean deleteRoom(int memberId, int roomId) {
		logger.info("changeRoomPublic("+memberId+", "+roomId+") Start");
		boolean result = false;
		result = dao.deleteRoom(memberId, roomId);
		logger.info("changeRoomPublic("+memberId+", "+roomId+") End");
		return result;
	}

	public boolean deletionLogical(int memberId, int roomId){
		logger.info("deletionLogical("+memberId+","+roomId+") Start");
		boolean result = false;
		result = dao.deletionLogical(memberId, roomId);
		logger.info("deletionLogical("+memberId+","+roomId+") End");
		return result;
				
	}
	

	/**
	 * 방 보기
	 * @param roomId
	 * @return
	 */
	public Room showRoom(int roomId) {
		logger.info("showRoom("+roomId+") Start");
		Room result = null;
		result = dao.select(roomId);
		logger.info("showRoom("+roomId+") End");
		return result;
	}
	

	public int getTotal(Integer estateId){
		logger.info("getTotal_Service_Start");
		int result = 0; 
		result = dao.getTotal(estateId);
		logger.info("getTotal_Service_End");
		return result; 
	}


	//백 높이 수정 
	public int wallheightchange(Room room){
		
		int result = 0;
		result = dao.wallheightchange(room);
		
		return  result;
	}

	//방 이름 수정
	public int roomtitleChange(Room room){
		
		int result = 0;
		result = dao.roomtitleChange(room);
		
		return  result;
	}
	
	public String saveSnapShot(int roomId, MultipartFile file) {
		logger.info("saveSnapShot("+roomId+", "+file.getOriginalFilename()+") Start");
		
		String result = null;
		FileService.deleteFile(snapShotDir + file.getOriginalFilename());
		result = FileService.saveFile(file, snapShotDir, true);
		result = snapShotDir+result;
		
		Room room = new Room();
		room.setRoomId(roomId);
		room.setSnapshot(result);
		
		// DB 저장
		if ( dao.updateRoomSanpShot(room) != 1 ) {
			FileService.deleteFile(snapShotDir+result);
			result = null;
		}

		logger.info("saveSnapShot("+roomId+", "+file.getOriginalFilename()+") End");
		return result;
	}
	
	public boolean downloadSnapShotFile(String fileName, OutputStream os) {
		logger.info("downloadSnapShotFile({}) Start", fileName);
		boolean result = false;
		FileService.writeFile(snapShotDir + fileName, os);
		result = true;
		logger.info("downloadSnapShotFile({}) End", fileName);
		return result;
	}
}