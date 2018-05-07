package com.real.fudousan.reply.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.fudousan.member.controller.MemberController;
import com.real.fudousan.reply.vo.Reply;

@Repository
public class ReplyDAOOracle implements ReplyDAO {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int insertReply(Reply reply){
		logger.info("Insert Reply Start - DAO Oracle");
		int result = 0; 
		
		try{
			ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
			result = replyMapper.insertReply(reply);
		} catch(Exception e){
			e.printStackTrace();
		}
		logger.info("Insert Reply End - DAO Oracle");
		return result;
	}
	@Override
	public List<Reply> selectReply(){
		logger.info("Select Reply Start - DAO Oracle");
		List<Reply> result = null; 
		try{
			ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
			result = replyMapper.selectReply();
		} catch(Exception e){
			e.printStackTrace();
		}
		logger.info("Select Reply End - DAO Oracle");
		return result;
	}
	@Override
	public boolean deleteReply(int replyId){
		logger.info("Delete Reply Start - DAO Oracle");
		boolean result = false; 
		try{
			ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
			result = replyMapper.deleteReply(replyId);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		logger.info("Delete Reply End - DAO Oracle");
		return result; 
	}
	@Override
	public Reply selectOne(int replyId){
		logger.info(" selectOne Start - DAO Oracle");
		Reply  result = null; 
		try{
			ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
			result = replyMapper.selectOne(replyId);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		logger.info(" selectOne End - DAO Oracle");
		return result; 
	}
	@Override
	public int updateReply(Reply reply){
		logger.info(" update Reply Start - DAO Oracle");
		int result = 0; 
		try{
			ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
			result = replyMapper.updateReply(reply);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		logger.info(" update Reply End - DAO Oracle");
		return result;
	}
	@Override
	public List<Reply>selectReplyEstate(int estateId){
		logger.info(" select Estate Reply Start - DAO Oracle");
		List<Reply> result = null; 
		try{
			ReplyMapper replyMapper = sqlSession.getMapper(ReplyMapper.class);
			result = replyMapper.selectReplyEstate(estateId);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		logger.info(" select Estate End - DAO Oracle");
		return result;
	}
}
