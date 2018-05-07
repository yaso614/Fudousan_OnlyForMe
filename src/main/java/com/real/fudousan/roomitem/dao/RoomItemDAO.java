package com.real.fudousan.roomitem.dao;

import java.util.Set;

import com.real.fudousan.roomitem.vo.RoomItem;

public interface RoomItemDAO {

	public int insert(RoomItem roomItem);
	
	public RoomItem select(int roomitemId);
	
	public Set<RoomItem> selectAllByRoomId(int roomId);
	
	public int update(RoomItem roomItem);
	
	public boolean delete(int roomItemId);
	
	public boolean deleteAll(int memberId, int roomId);
}
