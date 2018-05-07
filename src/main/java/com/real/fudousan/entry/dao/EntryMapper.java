package com.real.fudousan.entry.dao;

import com.real.fudousan.entry.vo.Entry;
import com.real.fudousan.estate.vo.Estate;

public interface EntryMapper {
	//등록
	public int insertEntry(Entry entry);
	
	//삭제
	public int deleteEntry(Entry entry);
	
	//가져오기
	public Entry selectEntry(int agencyId, int estateId);
	
	//수정할때 매물 불러오기
	public int modifyEstatePage(Entry entry);
	
	//수정
	  public int updateByIds(Entry entry);
	  
	  public Entry listEntry(int estateId);
}
