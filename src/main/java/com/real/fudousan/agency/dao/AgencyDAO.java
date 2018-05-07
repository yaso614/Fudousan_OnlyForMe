package com.real.fudousan.agency.dao;

import java.util.List;

import com.real.fudousan.agency.vo.Agency;

public interface AgencyDAO {
	/**
	 * 허가 값으로 검색한다.
	 * @param confirm 허가 값
	 * @return
	 */
	public List<Agency> selectByConfirm(int confirm);
	/**
	 * 특정 agency를 해당 confirm 값으로 변경한다.
	 * @param agencyId 특정 agency ID
	 * @param confirm 변경하고자하는 confirm 값
	 * @return
	 */
	public boolean updateConfirm(int agencyId, int confirm);
	

	public int selectAgencyId(String email);

	/**
	 * 중개소 주소(main, middle, small, sub)를 반환한다. 
	 * @param 
	 * @return Agency List
	 */
	public List<Agency> agencyLocationPrint();
	
	/**
	 * 해당 회원이 운영하는 모든 중개사 정보를 가져온다.
	 * @param memberId
	 * @return
	 */
	public List<Agency> selectByMemberId(int memberId);

	public Agency selectAgencyOne(int agencyId);
}
