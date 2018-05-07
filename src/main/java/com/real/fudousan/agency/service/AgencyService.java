package com.real.fudousan.agency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.fudousan.agency.dao.AgencyDAO;

import com.real.fudousan.agency.vo.Agency;

@Service
public class AgencyService {
	private static final Logger logger = LoggerFactory.getLogger(AgencyService.class);
	@Autowired
	private AgencyDAO dao;

	/**
	 * 비 승인된 부동산 중개소 리스트
	 * @return
	 */
	public List<Agency> unConfirmedList() {
		logger.info("unConirmedList Start");
		List<Agency> result = null;
		result = dao.selectByConfirm(0);
		logger.info("unConirmedList End");
		return result;
	}
	
	public List<Agency> viewAgencyByMemberId(int memberId) {
		logger.info("viewAgencyByMemberId("+memberId+") Start");
		
		List<Agency> result = null;
		
		result = dao.selectByMemberId(memberId);
		
		logger.info("viewAgencyByMemberId("+memberId+") End");
		return result;
	}
	
	/**
	 * 모든 중개사무소 가져오기
	 * @return
	 */
	public List<Agency> getAllAgency() {
		
		return null;
	}
	
	/**
	 * 해당 주소의 모든 중개 사무소를 가져온다.
	 * @param main
	 * @return
	 */
	public List<Agency> getAgency(String main) {
		 return getAgency(main, null);
	}
	
	/**
	 * 해당 주소의 모든 중개 사무소를 가져온다.
	 * @param main
	 * @param middle
	 * @return
	 */
	public List<Agency> getAgency(String main, String middle) {
		 return getAgency(main, null, null);
	}
	
	/**
	 * 해당 주소의 모든 중개 사무소를 가져온다.
	 * @param main
	 * @param middle
	 * @param small
	 * @return
	 */
	public List<Agency> getAgency(String main, String middle, String small) {
		 return null;
	}
	
	public boolean confirm(int agencyId) {
		logger.info("confirm(" + agencyId + ") Start");
		boolean result = false;
		
		result = dao.updateConfirm(agencyId, 1);
		logger.info("confirm(" + agencyId + ") End");
		return result;
	}
	

	public int selectAgencyId(String email){
		return dao.selectAgencyId(email);
	}

	
	/**
	 * 중개소 주소(main, middle, small, sub)를 반환한다. 
	 * @param 
	 * @return Agency List
	 */
	public List<Agency> agencyLocationPrint(){
		logger.info("agencyLocationPrint Start");
		
		List<Agency> result = null;
		result = dao.agencyLocationPrint();
		
		logger.info("agencyLocationPrint End");
		return result; 
	}
	

	public Agency selectAgencyOne(int agencyId){
		logger.info("selectAgencyOne Start");
		
		Agency result = null;
		result = dao.selectAgencyOne(agencyId);
		
		logger.info("selectAgencyOne End");
		return result; 
	}
	

}
