package com.real.fudousan.room.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.real.fudousan.room.vo.Room;

public interface RoomMapper {

	public List<Room> searchRoomInfo(String roomSearch,int memberId);
	
	public List<Room> allMyRoom(Integer memberId);
	
	public List<Room> selectByIds(Set<Integer> roomIds);
	

	public List<Room> selectEstateRoom(Integer estateId, RowBounds rb);
	
	public int getTotal(Integer estateId);
	
	public Room selectById(int roomId);
	
	/**
	 * 파라미터에 따라 실제 매물이 존재하는 방 or 존재하지 않는 방 으로 구분하여 select 한다.<br>
	 * 파라미터 맵<br>
	 * memberId : 해당 방을 등록한 멤버 ID<br>
	 * isRealRoom : 해당 방에 대한 매물이 존재하는지 여부(true, false)
	 * @param param 파라미터 맵
	 * @return
	 */
	public List<Room> selectByIdOnEsate(Map<String, Object> param);

	/**
	 * ROOM테이블에서 MEMBER_ID(memberId)와 ROOM_ID(roomId)를 WHERE 조건으로 하여 ROOM_PUBLIC(roomPublic)을 update한다.
	 * @param memberId MEMBER_ID
	 * @param roomId ROOM_ID
	 * @param roomPublic ROOM_PUBLIC
	 * @return
	 */
	public boolean updateRoomPublic(int memberId, int roomId, int roomPublic);
	
	/**
	 * MEMBER_ID(memberId) 와 ROOM_ID(roomId)가 일치하는 해당 컬럼을 DELETE 한다.
	 * @param memberId
	 * @param roomId
	 * @return
	 */
	public boolean deleteRoom(int memberId, int roomId);
	
	
	public boolean deletionLogical(int memberId,int roomId);
	
	
	/**
	 * MEMBER_ID(memberId), ROOM_PUBLIC(roomPublic)로 새로운 컬럼을 추가한다.
	 * 
	 * @return ROOM_ID
	 */
	public int insert(Room room);
	

	/*벽높이*/
	public int wallheightchange(Room room);
	/*방이름*/
	public int roomtitleChange(Room room);

	
	public int updateRoomSanpShot(Room room);

	
	public boolean updateCeilingTexture(int roomId, int textureId);
	
	public boolean updateFloorTexture(int roomId, int textureId);
}
