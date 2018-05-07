package com.real.fudousan.reply.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.real.fudousan.member.controller.MemberController;
import com.real.fudousan.member.vo.Member;

import com.real.fudousan.reply.service.ReplyService;
import com.real.fudousan.reply.vo.Reply;

@Controller
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	ReplyService service;
	
	@ResponseBody
	@RequestMapping(value="insertReply", method=RequestMethod.POST)
	public int insertReply(Reply reply, String estateId, String memberId, String text){
		logger.info("Insert Reply Start - controller");
		Member member = new Member();
		reply.setText(text);
		System.out.println(reply);
		String estateIdTrim = estateId.trim();
		int estateIdResult = Integer.parseInt(estateIdTrim);
		int memberIdResult = Integer.parseInt(memberId);
		member.setMemberId(memberIdResult);
		reply.setEstateId(estateIdResult);
		reply.setMember(member);
		service.insertReply(reply);
		int replyId = reply.getReplyId();
		System.out.println(replyId);
		logger.info("Insert Reply End - controller");
		return replyId;
	}
	
	@ResponseBody
	@RequestMapping(value="selectReply", method=RequestMethod.POST)
	public List<Reply> selectReply(){
		logger.info("Select Reply Start - Controller");
		List<Reply> reply = service.selectReply();

		logger.info("Select Reply End - Controller");
		return reply;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteReply", method=RequestMethod.POST)
	public Boolean deleteReply( int replyId){
		logger.info("Delete Reply Start - Controller");
		boolean result = service.deleteReply(replyId);
		logger.info("Delete Reply End - Controller");
		return result;
	}
	@ResponseBody
	@RequestMapping(value="selectOne", method=RequestMethod.POST)
	public Reply selectOne(int replyId){
		logger.info("SelectOne Start - Controller");
		Reply result = service.selectOne(replyId);
		logger.info("SelectOne End - Controller");
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="updateReply", method=RequestMethod.POST)
	public Reply updateReply(Reply reply, String text){
		logger.info("update Start - Controller");
		Reply resultSelect= null; 
		reply.setText(text);
		System.out.println("text::"+text);
		
		int result = service.updateReply(reply);
		if (result == 1) {
			int replyId = reply.getReplyId();
		    resultSelect=service.selectOne(replyId);			
		}
		
		logger.info("update End - Controller");
		return resultSelect;
	};

	@ResponseBody
	@RequestMapping(value="selectReplyEstate", method=RequestMethod.POST)
	public List<Reply> selectReplyEstate(int estateId){
		logger.info("selectReplyEstate - Controller");
		List<Reply> result = service.selectReplyEstate(estateId);
		
		logger.info("selectReplyEstate - Controller");
		return result;
	}
	
}
