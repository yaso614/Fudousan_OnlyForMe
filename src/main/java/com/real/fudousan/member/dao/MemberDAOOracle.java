package com.real.fudousan.member.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.member.vo.Member;

@Repository
public class MemberDAOOracle implements MemberDAO {
	private static final Logger logger = LoggerFactory.getLogger(MemberDAOOracle.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public Member select(Member member) {
		// TODO 직접 Mybatis로 id와 pw 검색해서 Member 반환
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		
		Member result = null;
		
		try {
			result = memberMapper.selectMember(member);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 회원 가입  
	@Override
	public int insertMember(Member member){
		
		int result = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.insertMember(member);
		} catch(Exception e){
			e.printStackTrace();
			
		}
		
		return result; 
	}
	
	
	// 중개 업자 가입 (member table)
	@Override
	public int insertAgencyMember(Member member){
	int result = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.insertAgencyMember(member);
			logger.info("dao:"+member);
		} catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		return result; 
	}
	
	// 중개업자 가입(agency table)
	@Override
	public int insertAgency(Agency agency){
		int result = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.insertAgency(agency);
		
		} catch(Exception e){
			e.printStackTrace();
			
		}
		
		return result; 
	}
	
	@Override
	public int updateMember(Member member){
		int result = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.updateMember(member);
		
		} catch(Exception e){
			e.printStackTrace();
			
		}
		
		return result; 
	}
	
	@Override
	public int updateInterior(Member member){
		int result = 0;
		try {
			
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.updateMember(member);
		  
		} catch(Exception e){
		  
		  e.printStackTrace();
			
		}
		
		return result; 
	}
	
	@Override
	public int updateAgencyMember(Member member){
		int result = 0;
		try {
			
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.updateAgencyMember(member);
		  
		} catch(Exception e){
		  
		  e.printStackTrace();
			
		}
		
		return result; 
	}
	
	@Override
	public int updateAgency(Agency agency){
		int result = 0;
		try {
			
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.updateAgency(agency);
		  
		} catch(Exception e){
		  
		  e.printStackTrace();
			
		}
		
		return result; 
	}
	
	public ArrayList<Member> interior(){
		ArrayList<Member> result = null;
		try {
			
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.interior();
		} catch(Exception e){
		  
		  e.printStackTrace();
			
		}
		return  result;
	}


	@Override
	public Member selectMemberOne(Member member){
		Member result = null; 
		try {
			
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			result = memberMapper.selectMemberOne(member);
		} catch(Exception e){
		  
		  e.printStackTrace();
			
		}
		return  result;
	}
}
