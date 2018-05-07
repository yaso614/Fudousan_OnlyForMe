package com.real.fudousan.member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.real.fudousan.agency.service.AgencyService;
import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.common.util.FileService;
import com.real.fudousan.member.service.MemberService;
import com.real.fudousan.member.vo.Member;

@SessionAttributes("loginId")
@Controller
@RequestMapping(value="memberupdate")
public class UpdateMemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateMemberController.class);
	public static final String uploadPath ="/memberfile";
	
	@Autowired
	private MemberService service;
	@Autowired
	private AgencyService agencyService;

	@Autowired
	private AgencyService aService; 
	
	@RequestMapping(value="memberupdate", method=RequestMethod.GET)
	public String memberupdate(String email, Model model){
		logger.info("start memberupdate - controller");
		System.out.println("email:::"+email);
		Member member= new Member();
		member.setEmail(email);
		Member result=service.selectMemberOne(member);
		model.addAttribute("member", result);
		logger.info("end memberupdate - controller");
		
		return "memberupdate/memberupdate";
	}
	
	@RequestMapping(value="agencyupdate", method=RequestMethod.GET)
	public String agencyupdate(@ModelAttribute("loginId") int loginId, Model model){
		logger.info("agencyupdate("+loginId+") Start");
		
		List<Agency> agency = agencyService.viewAgencyByMemberId(loginId);
		logger.debug("Result : " + agency);
		model.addAttribute("agency", agency.get(0));

		logger.info("agencyupdate("+loginId+") End");
		return "memberupdate/agencyupdate";
	}
	
	@RequestMapping(value="modifyMember", method=RequestMethod.POST)
	public String modifyMember(
			@ModelAttribute("member") Member member
			, MultipartFile file
			, Model model
			, HttpSession session){

			logger.info("회원 수정 시작(member)");
			if (!file.isEmpty()) {
				// 변환
				String savedFileName=FileService.saveFile(file, uploadPath, false);
				// 검색 
				Member result=service.selectMemberOne(member);
				if( result.getPicture() != null ) {
					// 삭제 
					FileService.deleteFile(result.getPicture());
				}
				// 등록 
				member.setPicture(uploadPath+"/"+savedFileName);
			}
			
			
			
			System.out.println(member);
			boolean result = service.modifyMember(member, file);
			
			if (result == false) {
				
				model.addAttribute("errorMsg", "수정 실패");
				logger.info("회원 수정 실패");
				return "memberupdate/memberupdate";
				
			}
			else{
				// get member name 
				String resultMemberName = member.getMemberName();
					
				session.removeAttribute("permissionId");
				session.setAttribute("permissionId", member.getPermission().getPermissionId());
				
				logger.info("회원 수정 종료");
				
				return "redirect:/";
			}
	}
	
	@RequestMapping(value="modifyAgency", method=RequestMethod.POST)
	public String updateAgency(
			@ModelAttribute("member") Member member
			, Agency agency
			, MultipartFile file
			, Model model
			, HttpSession session
			, String main 
			, String agencytext
			, String membertext
			){
		
		logger.info("회원 수정 시작(agency)");
		member.setText(membertext);
		agency.setText(agencytext);
		// get login Id
		String loginEmail=(String)session.getAttribute("loginEmail");
		//search member
		Member searchMember = new Member();
		searchMember.setEmail(loginEmail);
		// search login member
		Member loginMember = service.login(searchMember);
		
		// member to set login member_id
		member.setMemberId(loginMember.getMemberId());
		System.out.println(member);
		// start member update
		// set address member 
		agency.setAddressMain(main);
		// set email address to member
		member.setEmail(loginEmail);
		System.out.println(file);
		
		logger.info("member 등록 시작");
		if (file != null && !file.isEmpty()) {
			// 검색 
			Member result=service.selectMemberOne(member);
			if( result.getPicture() != null ) {
				// 삭제 
				FileService.deleteFile(result.getPicture());
			}
			// 저장
			String savedFileName=FileService.saveFile(file, uploadPath, false);
			// 등록 
			member.setPicture(uploadPath+"/"+savedFileName);
		}
	
		boolean memberUpdateResult = service.modifyAgencyMember(member, file);
		logger.info("member 등록 종료");
		
		// start agency update
		logger.info("agency 등록 시작");
		// set member 
		agency.setMember(member);
				
		boolean agencyUpdateResult = service.modifyAgency(agency);
		logger.info("agency 등록 종료 ");
		
		if (agencyUpdateResult && memberUpdateResult) {
			// true 
			logger.info("회원 등록 성공");
			model.addAttribute(memberUpdateResult);

			session.removeAttribute("permissionId");
			session.setAttribute("permissionId", member.getPermission().getPermissionId());
			
			return "redirect:/";
		}
		else{
			logger.info("회원 등록 실패");
			model.addAttribute(memberUpdateResult);
			return "redirect:agencyupdate";
		}
	}
	
	
	

	

	
}
