package com.real.fudousan.entry.dao;

import com.real.fudousan.entry.vo.Entry;
import com.real.fudousan.estate.vo.Estate;


public interface EntryDAO {
	
	//삽입
	public int insertEntry(Entry entry);
	//삭제
	public int deleteEntry(Entry entry);
	
	//수정 페이지로 이동?
	public int modifyEstatePage(Entry entry);
	
	public int updateByIds(Entry entry);
	//수정 페이지 에서 가격불러올때 
	public Entry listEntry(int estateId);
	
	
}
