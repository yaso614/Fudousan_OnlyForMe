package com.real.fudousan.roomwall.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.fudousan.roomwall.dao.RoomWallDAO;
import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.roomwall.vo.RoomWallConnector;

@Service
public class RoomWallService {
	private static final Logger logger = LoggerFactory.getLogger(RoomWallService.class);
	
	@Autowired
	private RoomWallDAO dao;

	public boolean save(int roomId, List<RoomWall> roomWallList, Map<Integer, RoomWallConnector> roomWallConnectorMap) {
		logger.info("save() Start");
		logger.debug("roomWallList : " + roomWallList.toString());
		logger.debug("roomWallConnectorMap : " + roomWallConnectorMap.toString());
		
		boolean result = dao.insertWallAndConnector(roomId, roomWallList, roomWallConnectorMap);
		
		logger.info("save() End");
		return result;
	}
	

	/**
	 * 해당 Room 안의 모든 벽과 연결점을 가져온다.
	 * @param roomId
	 * @return "walls":List of RoomWall , "connectors":List of RoomWallConnector
	 */
	public Map<String, List<?>> getWallAndConnector(int roomId) {
		logger.info("getWallAndConnector("+roomId+") Start");
		
		Map<String, List<?>> result = new HashMap<>();
		
		List<RoomWall> wallFromDAO = dao.selectAllWallAndConnector(roomId);
		
		List<RoomWallConnector> connectorFromWall = new ArrayList<>();
		
		// 변환 맵
		Map<Integer, Integer> convertMap = new HashMap<>();
		
		// 벽에 들어있는 점을 꺼내서 차례대로 저장한다.
		for(RoomWall wall : wallFromDAO) {
			// 우선 연결점을 꺼내기
			RoomWallConnector con1 = wall.getRoomWallConnector1();
			RoomWallConnector con2 = wall.getRoomWallConnector2();
			logger.debug("con1 : con2 -> " + con1 + ":" + con2);
			// 점 1이 기존의 변환 맵에 있으면 인덱스만 변환
			Integer convertValue = convertMap.get(con1.getConnectorId());
			if(convertValue == null) {
				// 없으면 넣고 해당 Index로 설정
				connectorFromWall.add(con1);
				convertValue = connectorFromWall.size() - 1;
				convertMap.put(con1.getConnectorId(), convertValue);
			}
			con1.setConnectorId(convertValue);
			
			// 점 2가 기존의 변환 맵에 있으면 인덱스만 변환
			convertValue = convertMap.get(con2.getConnectorId());
			if(convertValue == null) {
				connectorFromWall.add(con2);
				convertValue = connectorFromWall.size() - 1;
				convertMap.put(con2.getConnectorId(), convertValue);
			}
			con2.setConnectorId(convertValue);
		}
		
		result.put("walls", wallFromDAO);
		result.put("connectors", connectorFromWall);
		
		logger.info("getWallAndConnector("+roomId+") End");
		return result;
	}
	
	public boolean changeFrontTexture(int roomWallId, int textureId) {
		logger.info("changeFrontTexture("+roomWallId+", "+ textureId+") Start");
		boolean result = false;
		result = dao.updateFrontTexture(roomWallId, textureId);
		logger.info("changeFrontTexture("+roomWallId+", "+ textureId+") End");
		return result;
	}
	
	public boolean changeBackTexture(int roomWallId, int textureId) {
		logger.info("changeBackTexture("+roomWallId+", "+ textureId+") Start");
		boolean result = false;
		result = dao.updateBackTexture(roomWallId, textureId);
		logger.info("changeBackTexture("+roomWallId+", "+ textureId+") End");
		return result;
	}
}
