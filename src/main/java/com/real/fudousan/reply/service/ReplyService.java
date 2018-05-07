package com.real.fudousan.reply.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.fudousan.member.controller.MemberController;

import com.real.fudousan.reply.dao.ReplyDAO;
import com.real.fudousan.reply.vo.Reply;

@Service
public class ReplyService {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	ReplyDAO dao;
	
	public int insertReply(Reply reply){
		logger.info("Insert Reply Start - service");
		int result = dao.insertReply(reply);
		logger.info("Insert Reply End - service ");
		return result;
	}
	
	public List<Reply> selectReply(){
		logger.info("Select Reply Start - service");
		List<Reply> reply = dao.selectReply();
		logger.info("Select Reply End - service");
		return reply; 
	}
	
	public boolean deleteReply(int replyId){
		logger.info("Delete Reply Start - service");
		boolean result = dao.deleteReply(replyId);
		logger.info("Delete Reply End - service");
		return result; 
	}
	
	public Reply selectOne(int replyId){
		logger.info("selectOne Start - service");
		Reply result = dao.selectOne(replyId);
		logger.info("selectOne End - service");
		return result; 
	}
	
	public int updateReply(Reply reply){
		logger.info("update Reply Start - service");
		int result = dao.updateReply(reply);
		logger.info("update Reply End - service");
		return result;
	};
	
	public List<Reply>selectReplyEstate(int estateId){
		logger.info("selectReplyEstate Start - service");
		List<Reply> result = dao.selectReplyEstate(estateId);
		logger.info("selectReplyEstate End - service");
		return result;
	}

}
