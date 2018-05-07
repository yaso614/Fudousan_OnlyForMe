package com.real.fudousan.advice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.fudousan.advice.dao.AdviceDAO;
import com.real.fudousan.advice.vo.Advice;
import com.real.fudousan.item.dao.ItemDAOOracle;

@Service
public class AdviceService {
	private static final Logger logger = LoggerFactory.getLogger(AdviceService.class);
	@Autowired
	private AdviceDAO dao;

	/**
	 * 신청한 모든 목록
	 * @param id 신청한 사람
	 * @param status Adivce의 상태
	 * @return
	 */
	public List<Advice> getRequestList(int id, int status) {
		logger.info("getRequestList("+id+")("+status+") Start");
		List<Advice> helpCall = null;
		helpCall = dao.selectByIdAndStatus(id, status);
		logger.info("getRequestList("+id+")("+status+") End");
		return helpCall;
		
	}
	
	/**
	 * 신청 받은 모든 목록
	 * @param id
	 * @return
	 */
	public List<Advice> getRequestedList(int id) {
		logger.info("getRequestedList("+id+") Start");
		List<Advice> reverseCall = null;
		reverseCall = dao.reverseCall(id);
		logger.info("getRequestedList("+id+") End");
		return reverseCall;
	}
	
	/**
	 * 내가 인테리어 업자의 조언 요청 취소하기
	 * @param advice
	 * @return
	 */
	public boolean cancelAdvice(Advice advice) {
		logger.info("cancelAdvice("+advice+") Start");
			boolean cancelOk = false;
			cancelOk = dao.cancelAdvice(advice);
			logger.info("cancelAdvice("+advice+") End");
		return cancelOk;
	}
	
	/**
	 * 승인 하기
	 * @param advice 승인 대상
	 * @return
	 */
	public boolean confirm(Advice advice) {
		logger.info("confirm("+advice+") Start");
		logger.info("confirm("+advice+") End");
		
		return false;
	}
	
	/**
	 * 요청 거부 하기
	 * @param advice
	 * @return
	 */
	public boolean unConfirm(Advice advice) {
		logger.info("unConfirm("+advice+") Start");
		advice.setState(Advice.DENIED);
		boolean result = false;
		result = dao.updateState(advice);
		logger.info("unConfirm("+advice+") End");
		return result;
	}
	
	/**
	 * 이메일1이 이메일2한테 요청
	 * @param email1
	 * @param email2
	 * @return
	 */
	public boolean request(String email1, String email2) {
		logger.info("request("+email1+", "+email2+") Start");
		logger.info("request("+email1+", "+email2+") End");
		
		return false;
	}
	
	/**
	 * 일반자사용자가 인테리어 업자에게 도움을 요청
	 * @param Advice advice
	 * @return
	 */
	public boolean requestAdvice(Advice advice) {
		logger.info("request(advice의 일반사용자 : , "+advice+") Start");
		boolean result = false;
		result = dao.addviceRequest(advice);
		logger.info("request(advice의 일반사용자 : , "+advice+") End");
		return result;
	}
}
