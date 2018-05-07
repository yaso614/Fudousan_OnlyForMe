package com.real.fudousan.agency.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.fudousan.agency.vo.Agency;

@Repository
public class AgencyDAOOracle implements AgencyDAO {
	private static final Logger logger = LoggerFactory.getLogger(AgencyDAOOracle.class);
	@Autowired
	private SqlSession session;
	
	@Override
	public List<Agency> selectByConfirm(int confirm) {
		logger.info("selectByConfirmed(" + confirm + ") Start");
		List<Agency> result = null;
		
		try {
			AgencyMapper mapper = session.getMapper(AgencyMapper.class);
			result = mapper.selectByConfirmed(confirm);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		
		logger.info("selectByConfirmed(" + confirm + ") End");
		return result;
	}

	@Override
	public boolean updateConfirm(int agencyId, int confirm) {
		logger.info("updateConfirm(" + agencyId + ", " + confirm + ") Start");
		boolean result = false;
		
		try {
			AgencyMapper mapper = session.getMapper(AgencyMapper.class);
			result = mapper.updateConfirm(agencyId, confirm);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		logger.info("updateConfirm(" + agencyId + ", " + confirm + ") End");
		return result;
	}
	
	@Override

	public int selectAgencyId(String email){
		int agencyId = 0;
		
		try {
			AgencyMapper mapper = session.getMapper(AgencyMapper.class);
			agencyId = mapper.selectAgencyId(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return agencyId;
	}

	@Override
	public List<Agency> agencyLocationPrint(){
		logger.info("agency Location Print 시작");
		List<Agency> result = null;
		
		try {
			AgencyMapper mapper = session.getMapper(AgencyMapper.class);
			result = mapper.agencyLocationPrint();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		logger.info("agency Location Print 종료");
		return result;
	}
	
	@Override
	public Agency selectAgencyOne(int agencyId){
		logger.info("selectAgencyOne 시작");
		Agency result = null;
		
		try {
			AgencyMapper mapper = session.getMapper(AgencyMapper.class);
			result = mapper.selectAgencyOne(agencyId);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		logger.info("selectAgencyOne 종료");
		return result;
	}

	@Override
	public List<Agency> selectByMemberId(int memberId) {
		logger.info("agency Location Print 시작");
		List<Agency> result = null;
		
		try {
			AgencyMapper mapper = session.getMapper(AgencyMapper.class);
			result = mapper.selectByMemberId(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		logger.info("agency Location Print 종료");
		return result;
	}

}
