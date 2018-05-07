package com.real.fudousan.favorite.controller;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.real.fudousan.advice.service.AdviceService;
import com.real.fudousan.advice.vo.Advice;
import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.favorite.service.FavoriteService;
import com.real.fudousan.favorite.vo.Favorite;
import com.real.fudousan.room.service.RoomService;
import com.real.fudousan.room.vo.Room;

@Controller
public class FavoriteController {
	private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

	@Autowired
	private FavoriteService service;
	@Autowired
	private RoomService Rservice;

	@Autowired
	private AdviceService Aservice;
	
	@ResponseBody
	@RequestMapping(value="searchFavorite",method=RequestMethod.GET)
	public List<Favorite> saerchFavorite(Model model,int memberId, String favoSearch){
		List<Favorite> seacrhFavorite = null;
		seacrhFavorite = service.showSearchFavorite(memberId, favoSearch);
		logger.info("찜하기 매물의 검색값  : "+favoSearch);
		model.addAttribute("flist",seacrhFavorite);
		
		/*List<Room> allRooms = Rservice.showAllRoom(memberId);
        List<Advice> alist = Aservice.getRequestList(memberId, Advice.REQUEST);
        List<Advice> rclist = Aservice.getRequestList(memberId, Advice.CONFIRMED);
        model.addAttribute("rlist",allRooms);
        model.addAttribute("alist", alist);
        model.addAttribute("rclist", rclist);
        HashMap<String,Object> map = new HashMap<>();
        map.put("rlist", allRooms);
        map.put("alist", alist);
        map.put("rclist", rclist);*/
		return seacrhFavorite;
	}
	
	@ResponseBody
	@RequestMapping(value="insertFavorite", method=RequestMethod.POST)
	public String insertFavorite(String estateId, String memberId ){
			logger.info("찜하기 등록 시작");
			Favorite favorite = new Favorite();
			Estate estate = new Estate();
			
			String estateIdTrim = estateId.trim();
			int estateIdResult = Integer.parseInt(estateIdTrim);
			int memberIdResult = Integer.parseInt(memberId);
			
			//estate setting 
			estate.setEstateId(estateIdResult);
			
			// favorite setting 
			favorite.setMemberId(memberIdResult);
			favorite.setEstate(estate);
			
			service.add(favorite);
			logger.info("찜하기 등록 종료");
			
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="selectFavorite", method=RequestMethod.POST)
	public Favorite selectFavorite(String estateId, String memberId ){
			logger.info("찜하기  가져오기 시작");
			Favorite favorite = new Favorite();
			Favorite result = new Favorite();
			Estate estate = new Estate();
			
			try {
				String estateIdTrim = estateId.trim();
				String memberIdTrim = memberId.trim();
				int estateIdResult = Integer.parseInt(estateIdTrim);
				int memberIdResult = Integer.parseInt(memberIdTrim);
				//estate setting 
				estate.setEstateId(estateIdResult);
				
				// favorite setting 
				favorite.setMemberId(memberIdResult);
				favorite.setEstate(estate);
				// result setting 
				result=service.selectFavorite(favorite);
			} catch (Exception e) {
				// TODO: handle exception
				;
			}
		
			
			
			logger.info("찜하기 가져오기 종료");
			
		return result;
	}

	@ResponseBody
	@RequestMapping(value="deleteFavorite", method=RequestMethod.POST)
	public String deleteFavorite(String estateId, String memberId ){
		logger.info("찜하기 삭제 시작");
		Favorite favorite = new Favorite();
		Estate estate = new Estate();
		int estateIdResult = Integer.parseInt(estateId);
		int memberIdResult = Integer.parseInt(memberId);
		
		//estate setting 
		estate.setEstateId(estateIdResult);
		
		// favorite setting 
		favorite.setMemberId(memberIdResult);
		favorite.setEstate(estate);
		
		service.deleteFavorite(favorite);
		logger.info("찜하기 삭제 종료");
		return "";
	}
	
	
	
	
	
	
}
