package com.real.fudousan.roomwall.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.real.fudousan.roomwall.service.RoomWallService;
import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.roomwall.vo.RoomWallConnector;

@Repository
public class RoomWallDAOOracle implements RoomWallDAO {
	private static final Logger logger = LoggerFactory.getLogger(RoomWallDAOOracle.class);
	@Autowired
	private SqlSession session;

	@Transactional
	@Override
	public boolean insertWallAndConnector(int roomId, List<RoomWall> roomWall, Map<Integer, RoomWallConnector> roomConnector) {
		logger.info("insertWallAndConnector() Start");
		boolean result = true;
		
			RoomWallMapper roomWallMapper = session.getMapper(RoomWallMapper.class);
			if(roomWall.size() == 0) {
				result &= roomWallMapper.deleteWallsByRoomId(roomId) >= 0;
			} else if(result &= (roomWallMapper.deleteWallsByRoomId(roomId) >= 0)) {
				// 치환 맵
				Map<Integer, Integer> convertMap = new HashMap<>();
				// 커넥트를 넣으면서 DB PK로 치환한다.
				for(Entry<Integer, RoomWallConnector> entry : roomConnector.entrySet()) {
					// 커넥터 우선 insert
					RoomWallConnector connector = entry.getValue();
					// 기존 ID를 저장해둔다. 여기선 기존의 연결점이 중복되지 않는 상황이기에 중복을 확인하지 않는다.
					int connectorId = connector.getConnectorId();
					// INSERT
					result &= roomWallMapper.insertConnector(connector) == 1;
					// 치환 맵에 추가
					convertMap.put(connectorId, connector.getConnectorId());
				}
				// DB의 커넥터 PK로 수정하여서 insert
				for(RoomWall wall : roomWall) {
					wall.setRoomId(roomId);
					logger.debug(wall.toString());
					// 치환 맵에서 치환된 PK로 변경
					Integer c1 = convertMap.get(wall.getRoomWallConnector1().getConnectorId());
					Integer c2 = convertMap.get(wall.getRoomWallConnector2().getConnectorId());
					// 둘다 있으면 치환
					if(c1 != null && c2 != null) {
						wall.getRoomWallConnector1().setConnectorId(c1);
						wall.getRoomWallConnector2().setConnectorId(c2);
					} else {
						logger.debug("c1 : " + c1);
						logger.debug("c2 : " + c2);
						logger.debug("convertMap : " + convertMap);
						// 만일 없으면 에러
						throw new RuntimeException("기존 연결점에 해당하는 연결점 ID가 존재하지 않습니다.");
					}
					// 만일 두 커넥트가 동일한 벽이 있으면 insert 패스
					if(wall.getRoomWallConnector1().equals(wall.getRoomWallConnector2())) {
						continue;
					}
					if(!(result &= (roomWallMapper.insertWall(wall) == 1))) {
						throw new RuntimeException("벽 insert 에러");
					}
				}
			}
		
		
		
		
		logger.info("insertWallAndConnector() end");
		return result;
	}


	@Override
	public List<RoomWall> selectAllWallAndConnector(int roomId) {
		logger.info("selectAllWallAndConnector("+roomId+") Start");
		List<RoomWall> result = null;

		try {
			RoomWallMapper roomWallMapper = session.getMapper(RoomWallMapper.class);
			
			result = roomWallMapper.selectAllRoomWallByRoomId(roomId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.debug("result : " + result);
		logger.info("selectAllWallAndConnector("+roomId+") End");
		return result;
	}


	@Override
	public boolean updateFrontTexture(int roomWallId, int textureId) {
		logger.info("updateFrontTexture("+roomWallId+", "+textureId+") Start");
		boolean result = false;

		try {
			RoomWallMapper roomWallMapper = session.getMapper(RoomWallMapper.class);
			
			result = roomWallMapper.updateFrontTexture(roomWallId, textureId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("updateFrontTexture("+roomWallId+", "+textureId+"End");
		return result;
	}


	@Override
	public boolean updateBackTexture(int roomWallId, int textureId) {
		logger.info("updateBackTexture("+roomWallId+", "+textureId+") Start");
		boolean result = false;

		try {
			RoomWallMapper roomWallMapper = session.getMapper(RoomWallMapper.class);
			
			result = roomWallMapper.updateBackTexture(roomWallId, textureId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("updateBackTexture("+roomWallId+", "+textureId+"End");
		return result;
	}
}
