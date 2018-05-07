package com.real.fudousan.member.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.member.vo.Member;

@Repository
public interface MemberDAO {

	public Member select(Member member);
	
	// 회원가입 
	public int insertMember(Member member);
	public int insertAgencyMember(Member member);
	public int insertAgency(Agency agency);
	
	//회원 정보 수정 
	public int updateMember(Member member);
	public int updateInterior(Member member);
	public int updateAgencyMember(Member member);
	public int updateAgency(Agency agency);

	//인테리어 업자만 뽑아옴
	public ArrayList<Member> interior();
	
	
	// select member 
	public Member selectMemberOne(Member member);
	

	

}
