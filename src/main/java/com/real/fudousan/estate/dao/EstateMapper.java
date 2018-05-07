package com.real.fudousan.estate.dao;


import java.util.List;


import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.estate.vo.MunicipalityCode;
import com.real.fudousan.estate.vo.TransType;

public interface EstateMapper {
	
	//리스트 
	/*public List<Estate> select(Set<Integer> estateIds);*/
	
	public List<Estate> select(String email);
	//등록
	public int insertByIds(Estate esate);
	
	
	//public ArrayList<Estate> estatelist();
	//업데이트
	public int updateByIds(Estate estate);
	
	//매물가져올때
	public Estate viewEstate(int estateId);
	
	
	//코드 여부
	public String codecheck(int municipalitycodeId);
	
	

	
	// INSERT TRANS 
	public int insertTrans(TransType trans);
	
	// INSERT MUNICIPALITY CODE
	public int insertMunicipalitycode(MunicipalityCode mun);

	// SELECT selectEsatesLocation
	public List<Estate> selectEsatesLocation();

	public boolean updateBaseRoomId(int estateId, int roomId);
}
