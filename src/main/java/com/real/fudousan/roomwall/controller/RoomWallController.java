package com.real.fudousan.roomwall.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.real.fudousan.roomwall.service.RoomWallService;
import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.roomwall.vo.RoomWallConnector;

@RequestMapping("wall")
@Controller
public class RoomWallController {
	private static final Logger logger = LoggerFactory.getLogger(RoomWallController.class);
	
	@Autowired
	private RoomWallService service;

	@RequestMapping(value="wallPage", method=RequestMethod.GET)
	public String wallPage(int roomId, Model model) {
		logger.info("wallPage("+roomId+") Start");
		model.addAttribute("roomId", roomId);
		if (roomId >= 0) {
			Map<String, List<?>> map = service.getWallAndConnector(roomId);
			model.addAttribute("wallsAndConnectors", map);
			logger.debug("가져온 벽과 연결점 : " + map);
		}
		logger.info("wallPage("+roomId+") End");
		return "wall/wallPage";
	}
	
	@ResponseBody
	@RequestMapping(value="save", method=RequestMethod.POST)
	public boolean save(
			int roomId,
			String walls,
			String dots) {
		logger.info("save() Start");
		boolean result = false;
		logger.debug("roomId : "+ roomId);
		logger.debug("walls : "+ walls);
		logger.debug("dots : "+ dots);
		
		Map<Integer, RoomWallConnector> roomWallConnectorMap = new HashMap<>();
		List<RoomWall> roomWallList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode dotsRoot = mapper.readTree(dots);
			Iterator<JsonNode> dotsElements = dotsRoot.elements();
			while(dotsElements.hasNext()) {
				JsonNode element = dotsElements.next();
				roomWallConnectorMap.put(element.get("connectorId").asInt(), new RoomWallConnector(element.get("connectorId").asInt(), element.get("x").asDouble(), element.get("y").asDouble()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		try {
			JsonNode wallsRoot = mapper.readTree(walls);
			Iterator<JsonNode> wallsElements = wallsRoot.elements();
			while(wallsElements.hasNext()) {
				JsonNode element = wallsElements.next();
				RoomWall rw = new RoomWall();
				
				
				rw.setRoomWallConnector1(roomWallConnectorMap.get(element.get("startPoint").asInt()).clone());
				rw.setRoomWallConnector2(roomWallConnectorMap.get(element.get("endPoint").asInt()).clone());
				
				
				rw.setRoomId(roomId);
				rw.setType("Normal");
				
				roomWallList.add(rw);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("roomWallConnectorMap : " + roomWallConnectorMap.toString());
		logger.debug("roomWallList : " + roomWallList.toString());
		result = service.save(roomId, roomWallList, roomWallConnectorMap);
		logger.info("save() End");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="changeFrontTexture", method=RequestMethod.GET) 
	public boolean changeFrontTexture(int roomWallId,  int textureId) {
		logger.info("changeFrontTexture("+roomWallId+", "+textureId+") Start");
		boolean result = false;
		result = service.changeFrontTexture(roomWallId, textureId);
		logger.info("changeFrontTexture("+roomWallId+", "+textureId+") End");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="changeBackTexture", method=RequestMethod.GET) 
	public boolean changeBackTexture(int roomWallId,  int textureId) {
		logger.info("changeFrontTexture("+roomWallId+", "+textureId+") Start");
		boolean result = false;
		result = service.changeBackTexture(roomWallId, textureId);
		logger.info("changeBackTexture("+roomWallId+", "+textureId+") End");
		return result;
	}
}
