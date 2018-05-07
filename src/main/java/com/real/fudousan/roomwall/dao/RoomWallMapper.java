package com.real.fudousan.roomwall.dao;

import java.util.List;

import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.roomwall.vo.RoomWallConnector;

public interface RoomWallMapper {

	public int insertWall(RoomWall roomWall);
	
	public int insertConnector(RoomWallConnector roomWallConnector);
	
	public int deleteWallsByRoomId(int roomId);
	
	public List<RoomWall> selectAllRoomWallByRoomId(int roomId);
	
	public boolean updateFrontTexture(int roomWallId, int textureId);
	public boolean updateBackTexture(int roomWallId, int textureId);
}
