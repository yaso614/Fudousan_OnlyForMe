package com.real.fudousan.advice.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.fudousan.advice.vo.Advice;
import com.real.fudousan.room.dao.RoomDAOOracle;

@Repository
public class AdviceDAOOracle implements AdviceDAO {

	private static final Logger logger = LoggerFactory.getLogger(RoomDAOOracle.class);
	
	@Autowired
	SqlSession sqlsession;
	
	@Override
	public List<Advice> selectByIdAndStatus(int id, int status) {
		logger.info("Ad_DAOORACLE에서 selectByIdAndStatus-Start");
		AdviceMapper mapper = sqlsession.getMapper(AdviceMapper.class);
		List<Advice> helpCall = null;
		try{
			helpCall = mapper.selectByIdAndStatus(id, status);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Ad_DAOORACLE에서 selectByIdAndStatus-End");
		return helpCall;
	}

	@Override
	public List<Advice> reverseCall(int id) {
		logger.info("Advice_DAOORACLE_역요청 받기 Start");
		AdviceMapper mapper = sqlsession.getMapper(AdviceMapper.class);
		List<Advice> reverseCall = null;
		try{
			reverseCall = mapper.reverseCall(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Advice_DAOORACLE_역요청 받기 End");
		return reverseCall;
	}

	@Override
	public boolean cancelAdvice(Advice advice) {
		logger.info("캔슬어드바이스  Oracle Start");
		AdviceMapper mapper = sqlsession.getMapper(AdviceMapper.class);
		boolean realcancle = false;
		try{
			realcancle = mapper.cancleAdvice(advice);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("캔슬어드바이스  Oracle End");
		return realcancle;
	}

	@Override
	public boolean updateState(Advice advice) {
		logger.info("updateState("+advice+") Start");
		AdviceMapper mapper = sqlsession.getMapper(AdviceMapper.class);
		boolean result = false;
		try{
			result = mapper.updateState(advice);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("updateState("+advice+") End");
		return result;
	}

	@Override
	public boolean addviceRequest(Advice advice) {
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info("addviceRequest("+advice+") Start");
		AdviceMapper mapper = sqlsession.getMapper(AdviceMapper.class);
		boolean result = false;
		try{
			result = mapper.addviceRequest(advice);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("addviceRequest("+advice+") End");
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		return result;
	}



}
