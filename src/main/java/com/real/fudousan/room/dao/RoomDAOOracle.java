package com.real.fudousan.room.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import com.real.fudousan.room.vo.Room;
import com.real.fudousan.roomwall.dao.RoomWallMapper;

@Repository
public class RoomDAOOracle implements RoomDAO {

	private static final Logger logger = LoggerFactory.getLogger(RoomDAOOracle.class);

	@Autowired
	SqlSession sqlsession;
	
	//사용자가 자신이 작성한 매물리스트에서 검색해서 정보 가져오기
	@Override
	public List<Room> searchRoomInfo(String roomSearch, int memberId) {
		logger.info("RoomDAOOracle_searchRoomInfo_start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		List<Room> srlist = null;
		try{
			srlist = mapper.searchRoomInfo(roomSearch, memberId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RoomDAOOracle_searchRoomInfo_end");
		return srlist;
	}

	//사용자가 마이페이지에 접속하면 자신의 매물리스트에 있는 정보 전부 가져온다
	@Override
	public List<Room> allMyRoom(Integer memberId) {
		logger.info("RoomDAOOracle_allMyRoom_start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		List<Room> rlist = null;
		try{
			rlist = mapper.allMyRoom(memberId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RoomDAOOracle_allMyRoom_end");
		return rlist;
	}
	
	@Override
	public List<Room> selectEstateRoom(Integer estateId, int startRecord, int coutPerPage){
		logger.info("RoomDAOOracle_selectEstateRoom_start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		
		RowBounds rb = new RowBounds(startRecord, coutPerPage);
		
		List<Room> rlist = null;
		try{
			rlist = mapper.selectEstateRoom(estateId, rb);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RoomDAOOracle_selectEstateRoom_end");
		return rlist;
	}
	
	@Override
	public int getTotal(Integer estateId){
		logger.info("RoomDAOOracle_getTotal_start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		int result = 0; 
		try{
			result = mapper.getTotal(estateId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RoomDAOOracle_allMyRoom_end");
		return result;
	}
	
	@Override
	public List<Room> select(Set<Integer> roomIds) {
		logger.info("select("+roomIds+") Start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		List<Room> rlist = null;
		try{
			rlist = mapper.selectByIds(roomIds);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("select("+roomIds+") End");
		return rlist;
	}

	@Override
	public List<Room> selectByIdOnReal(int memberId, boolean isRealRoom) {
		logger.info("selectByIdOnReal("+memberId+", "+isRealRoom+") Start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		List<Room> result = null;
		
		Map<String, Object> param = new HashMap<>();
		param.put("memberId", memberId);
		param.put("isRealRoom", isRealRoom);
		
		try{
			result = mapper.selectByIdOnEsate(param);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("selectByIdOnReal("+memberId+", "+isRealRoom+") End");
		return result;
	}

	@Override
	public boolean updateRoomPublic(int memberId, int roomId, int roomPublic) {
		logger.info("updateRoomPublic("+memberId+", "+roomId+", "+roomPublic+") Start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		boolean result = false;

		try{
			result = mapper.updateRoomPublic(memberId, roomId, roomPublic);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("updateRoomPublic("+memberId+", "+roomId+", "+roomPublic+") End");
		return result;
	}

	@Override
	public boolean deletionLogical(int memberId, int roomId) {
		logger.info("deletionLogical("+memberId+","+roomId+") Start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		boolean result = false;
		try{
			result = mapper.deletionLogical(memberId, roomId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deletionLogical("+memberId+","+roomId+") End");
		return result;
	}
	
	@Override
	public boolean deleteRoom(int memberId, int roomId) {
		logger.info("deleteRoom("+memberId+", "+roomId+") Start");
		/*RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		boolean result = false;

		try{
			result = mapper.deleteRoom(memberId, roomId);
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		boolean result = deletionLogical(memberId, roomId);
		logger.info("deleteRoom("+memberId+", "+roomId+") End");
		return result;
	}

	@Override
	public int insert(Room room) {
		logger.info("insert("+room+") Start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		int result = -1;

		try{
			result = mapper.insert(room);
			logger.debug("result : " + result + ":" + room);
			if(result == 1) {
				result = room.getRoomId();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("insert("+room+") End");
		return result;
	}

	@Override
	public Room select(int roomId) {
		logger.info("select("+roomId+") Start");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		Room result = null;
		
		try{
			result = mapper.selectById(roomId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("result : " + result);
		logger.info("select("+roomId+") End");
		return result;
	}
	
	/*방높이 변경*/
	@Override
	public int wallheightchange(Room room){
		
		logger.info("방 높이변경 시작");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		int result = 0;
		
		try{
			result = mapper.wallheightchange(room);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("방 높이 변경 종료");
		return result;
	}

	/*방이름 변경*/
	@Override
	public int roomtitleChange(Room room) {
		logger.info("방 이름변경 시작");
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		int result = 0;
		
		try{
			result = mapper.roomtitleChange(room);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("방 이름변경 종료");
		return result;
	}
	
	@Override
	public int updateRoomSanpShot(Room room) {
		logger.info("updateRoomSanpShot("+room+") Start");
		
		RoomMapper mapper = sqlsession.getMapper(RoomMapper.class);
		int result = 0;
		
		try{
			result = mapper.updateRoomSanpShot(room);
		}catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("updateRoomSanpShot("+room+") End");
		return result;
	}



	@Override
	public boolean updateCeilingTexture(int roomId, int textureId) {
		logger.info("updateCeilingTexture("+roomId+", "+textureId+") Start");
		boolean result = false;

		try {
			RoomMapper roomMapper = sqlsession.getMapper(RoomMapper.class);
			
			result = roomMapper.updateCeilingTexture(roomId, textureId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("updateCeilingTexture("+roomId+", "+textureId+") End");
		return result;
	}


	@Override
	public boolean updateFloorTexture(int roomId, int textureId) {
		logger.info("updateFloorTexture("+roomId+", "+textureId+") Start");
		boolean result = false;

		try {
			RoomMapper roomMapper = sqlsession.getMapper(RoomMapper.class);
			
			result = roomMapper.updateFloorTexture(roomId, textureId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("updateFloorTexture("+roomId+", "+textureId+") End");
		return result;
	}

	
	
	
	
}
