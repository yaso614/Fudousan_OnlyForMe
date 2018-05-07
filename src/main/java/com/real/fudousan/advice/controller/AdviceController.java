package com.real.fudousan.advice.controller;

import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.real.fudousan.advice.service.AdviceService;
import com.real.fudousan.advice.vo.Advice;
import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.favorite.service.FavoriteService;
import com.real.fudousan.favorite.vo.Favorite;
import com.real.fudousan.member.controller.MemberController;
import com.real.fudousan.member.service.MemberService;
import com.real.fudousan.member.vo.Member;
import com.real.fudousan.room.service.RoomService;
import com.real.fudousan.room.vo.Room;

@SessionAttributes("loginId")
@Controller
public class AdviceController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private AdviceService Aservice;
	@Autowired
	private FavoriteService Fservice;
	@Autowired
	private RoomService Rservice;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MemberService mservice;
	
	@RequestMapping(value="cancelAdviceTrue", method=RequestMethod.GET)
	public String cancelAdviceCall(Model model,Integer customer , Integer interior, Integer roomNum){
		logger.info("Advice_Controller 사용자의 도움요청 취소 Start");
		Advice advice = new Advice();
		advice.setRequestMemberId(customer);
		advice.setRequestedMemberId(interior);
		Room room = new Room();
		room.setRoomId(roomNum);
		advice.setRoom(room);
		
		boolean tfresult = false;
		tfresult = Aservice.cancelAdvice(advice);
		model.addAttribute("tfresult",tfresult);
		
        List<Favorite> flist = Fservice.showAllFavorite(customer);
        List<Advice> alist = Aservice.getRequestList(customer, Advice.REQUEST);
        List<Advice> rclist = Aservice.getRequestList(customer, Advice.DENIED);
		List<Room> allRooms = Rservice.showAllRoom(customer);
        
		model.addAttribute("rlist",allRooms);
        model.addAttribute("flist", flist);
        model.addAttribute("alist", alist);
        model.addAttribute("rclist", rclist);
		logger.info("Advice_Controller 사용자의 도움요청 취소 End");
	       return  "user/mypagecustomer";
	}
	
	@ResponseBody
	@RequestMapping(value="unconfirm", method=RequestMethod.GET)
	public boolean unConfirm(@ModelAttribute("loginId") int memberId, Advice advice, Room room) {
		logger.info("unConfirm("+advice+") Start");
		advice.setRequestedMemberId(memberId);
		advice.setRoom(room);
		boolean result = false;
		result = Aservice.unConfirm(advice);
		logger.info("unConfirm("+advice+") End");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "helpCall", method = RequestMethod.GET)
	 public void mailSending(HttpServletRequest request, HttpSession session, int roomId, String email) {
		   
		logger.info("메일 전송 시작");
		  String setfrom = "2017scit@gmail.com";         
		    String tomail  = email;     // 받는 사람 이메일
		    String title   = "fudousan에서 요청 메일이 왔습니다";     // 제목
		    String content =  session.getAttribute("loginEmail") + "님이 요청을 보냈습니다."+ " 이동하기 > http://localhost:8888/fudousan/" ;     // 내용
		    try {
		      MimeMessage message = mailSender.createMimeMessage();
		      MimeMessageHelper messageHelper 
		                        = new MimeMessageHelper(message, true, "UTF-8");
		 
		      messageHelper.setFrom(setfrom);  // 보내는사람 생략하거나 하면 정상작동을 안함
		      messageHelper.setTo(tomail);     // 받는사람 이메일
		      messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
		      messageHelper.setText(content);  // 메일 내용
		     
		      int rquestId = (int)session.getAttribute("loginId");
		      
		      Member member = new Member();
		      
		      member.setEmail(tomail);
		      
		      Member memberOne = mservice.selectMemberOne(member);
		      int requestedId = memberOne.getMemberId(); //null pointer
		      int roomNum = roomId;
		      
		      Advice advice = new Advice();
		      advice.setRequestMemberId(rquestId);
		      advice.setRequestedMemberId(requestedId);
		      
		      Room room = new Room();
		      room.setRoomId(roomNum);
		      advice.setRoom(room);
		      Date date = new Date();
		      String ddd = date.toString();
		      advice.setId(ddd);
		      boolean abc = Aservice.requestAdvice(advice);
		      logger.info("이 요청은 성공적입니다.");
		      
		      if(abc){
		    	  logger.info("이 요청은 성공적입니다.");
		      }
		      
		      
		      mailSender.send(message);
		    } catch(Exception e){
		      System.out.println(e);
		    }
		    logger.info("메일 전송 성공");
	}
	
}

