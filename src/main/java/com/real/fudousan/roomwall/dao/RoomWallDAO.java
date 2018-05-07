package com.real.fudousan.roomwall.dao;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.roomwall.vo.RoomWallConnector;

public interface RoomWallDAO {

	@Transactional
	public boolean insertWallAndConnector(int roomId, List<RoomWall> roomWall, Map<Integer, RoomWallConnector> roomConnector);
	
	public List<RoomWall> selectAllWallAndConnector(int roomId);
	
	public boolean updateFrontTexture(int roomWallId, int textureId);
	
	public boolean updateBackTexture(int roomWallId, int textureId);
}
