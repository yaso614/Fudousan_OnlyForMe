package com.real.fudousan.member.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.real.fudousan.advice.service.AdviceService;
import com.real.fudousan.advice.vo.Advice;
import com.real.fudousan.agency.service.AgencyService;
import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.estate.service.EstateService;
import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.favorite.vo.Favorite;
import com.real.fudousan.item.service.ItemService;
import com.real.fudousan.item.vo.Item;
import com.real.fudousan.item.vo.ItemType;
import com.real.fudousan.item.vo.RefSite;
import com.real.fudousan.member.service.MemberService;
import com.real.fudousan.room.service.RoomService;
import com.real.fudousan.room.vo.Room;

@SessionAttributes("loginId")
@RequestMapping("interior")
@Controller
public class InteriorAgentController {
	private static final Logger logger = LoggerFactory.getLogger(InteriorAgentController.class);

	@Autowired
	private AdviceService adviceService;
	
	@Autowired
	private RoomService roomService;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String interiorPage(@ModelAttribute("loginId") int memberId, Model model) {
		logger.info("interiorPage() Start");
		
		// 요청 받은 목록
		List<Advice> adviceList = adviceService.getRequestedList(memberId);
		
		// 실제 매물이 존재하는 모델링 목록
		List<Room> realRoomList = roomService.showAllRoom(memberId, true);
		
		// 실제 매물이 존재하지 않는 모델링 목록
		List<Room> notRealRoomList = roomService.showAllRoom(memberId, false);
		
		model.addAttribute("adviceList", adviceList);
		model.addAttribute("realRoomList", realRoomList);
		model.addAttribute("notRealRoomList", notRealRoomList);
		
		logger.info("interiorPage() End");
		return "interior/interiorPage";
	}
}
