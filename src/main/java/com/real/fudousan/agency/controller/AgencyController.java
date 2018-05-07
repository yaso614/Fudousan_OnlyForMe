package com.real.fudousan.agency.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.real.fudousan.agency.service.AgencyService;
import com.real.fudousan.agency.vo.Agency;

import com.real.fudousan.member.controller.MemberController;

@RequestMapping("agency")
@Controller
public class AgencyController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private AgencyService service;
	
	 @RequestMapping(value="detailedinformation", method=RequestMethod.GET)
	public String detailedInformation(String id, Model model){
		logger.info("detailedInformation("+id+") Start");

		String result[] = id.split(":");
		if(result.length == 0) {
			logger.info("id가 없이 에이전시 정보를 요청하였습니다.");
			 return "redirect:/";
		}
		int agencyId = Integer.parseInt(result[1]);
		
		model.addAttribute("agency", service.selectAgencyOne(agencyId));
		
		logger.info("detailedInformation("+id+") End");
		 return "agency/detailedinformation";
	}
	
}
