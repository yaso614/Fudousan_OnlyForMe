package com.real.fudousan.roomitem.dao;

import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.fudousan.member.dao.MemberMapper;
import com.real.fudousan.member.vo.Member;
import com.real.fudousan.roomitem.vo.RoomItem;

@Repository
public class RoomItemDAOOracle implements RoomItemDAO {
	private static final Logger logger = LoggerFactory.getLogger(RoomItemDAOOracle.class);
	@Autowired
	SqlSession sqlSession;

	@Override
	public int insert(RoomItem roomItem) {
		logger.info("insert("+roomItem+") Start");
		RoomItemMapper roomItemMapper = sqlSession.getMapper(RoomItemMapper.class);
		
		int result = -1;
		
		try {
			result = roomItemMapper.insert(roomItem);
		} catch(Exception e){
			e.printStackTrace();
		}

		logger.info("insert("+roomItem+") End");
		return result;
	}

	@Override
	public RoomItem select(int roomitemId) {
		logger.info("select("+roomitemId+") Start");
		RoomItemMapper roomItemMapper = sqlSession.getMapper(RoomItemMapper.class);
		
		RoomItem result = null;
		
		try {
			result = roomItemMapper.selectByRoomItemId(roomitemId);
			logger.debug("result : "+result);
		} catch(Exception e){
			e.printStackTrace();
		}

		logger.info("select("+roomitemId+") End");
		return result;
	}

	@Override
	public Set<RoomItem> selectAllByRoomId(int roomId) {
		logger.info("selectAllByRoomId("+roomId+") Start");
		RoomItemMapper roomItemMapper = sqlSession.getMapper(RoomItemMapper.class);
		
		Set<RoomItem> result = null;
		
		try {
			result = roomItemMapper.selectAllByRoomId(roomId);
			logger.debug("result : "+result);
		} catch(Exception e){
			e.printStackTrace();
		}

		logger.info("selectAllByRoomId("+roomId+") End");
		return result;
	}

	@Override
	public int update(RoomItem roomItem) {
		logger.info("update("+roomItem+") Start");
		RoomItemMapper roomItemMapper = sqlSession.getMapper(RoomItemMapper.class);
		
		int result = -1;
		
		try {
			result = roomItemMapper.update(roomItem);
		} catch(Exception e){
			e.printStackTrace();
		}

		logger.info("update("+roomItem+") End");
		return result;
	}

	@Override
	public boolean delete(int roomItemId) {
		logger.info("delete("+roomItemId+") Start");
		RoomItemMapper roomItemMapper = sqlSession.getMapper(RoomItemMapper.class);
		
		boolean result = false;
		
		try {
			result = roomItemMapper.delete(roomItemId);
		} catch(Exception e){
			e.printStackTrace();
		}

		logger.info("delete("+roomItemId+") End");
		return result;
	}

	@Override
	public boolean deleteAll(int memberId, int roomId) {
		logger.info("deleteAll("+memberId+", "+roomId+") Start");
		RoomItemMapper roomItemMapper = sqlSession.getMapper(RoomItemMapper.class);
		
		boolean result = false;
		
		try {
			result = roomItemMapper.deleteAll(memberId, roomId);
		} catch(Exception e){
			e.printStackTrace();
		}

		logger.info("deleteAll("+memberId+", "+roomId+") End");
		return result;
	}

}
