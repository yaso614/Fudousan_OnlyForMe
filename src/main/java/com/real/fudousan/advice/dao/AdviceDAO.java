package com.real.fudousan.advice.dao;

import java.util.List;

import com.real.fudousan.advice.vo.Advice;

public interface AdviceDAO {

	public List<Advice> selectByIdAndStatus(int id, int status);

	public List<Advice> reverseCall(int id);
	
	public boolean cancelAdvice(Advice advice);
	
	public boolean updateState(Advice advice);
	
	public boolean addviceRequest(Advice advice); //어드바이스 요청

}
