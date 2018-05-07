package com.real.fudousan.favorite.dao;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.real.fudousan.favorite.vo.Favorite;

@Repository
public class FavoriteDAOOracle implements FavoriteDAO {

	private static final Logger logger = LoggerFactory.getLogger(FavoriteDAOOracle.class);
	
	@Autowired
	SqlSession sqlsession;
	
	@Override
	public List<Favorite> allMyfavorite(Integer memberId) {
		logger.info("사용자가 찜했던 모든 방 불러오기 - Start");
		FavoriteMapper mapper = sqlsession.getMapper(FavoriteMapper.class);
		List<Favorite> flist = null;
		try{
			flist = mapper.allFavorite(memberId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("사용자가 찜했던 모든 방 불러오기 - End");
		return flist;
	}

	@Override
	public List<Favorite> searchFavorite(int memberId,String favoSearch) {
		logger.info("사용자 찜목록 검색- Start");
		
		FavoriteMapper mapper = sqlsession.getMapper(FavoriteMapper.class);
		List<Favorite> fslist = null;
		try{
			fslist = mapper.searchFavorite(memberId,favoSearch);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("사사용자 찜목록 검색 - Start");
		return fslist;
	}
	
	@Override
	public boolean add(Favorite favorite){
		logger.info("찜목록 등록 dao oracle - Start");
		
		FavoriteMapper mapper = sqlsession.getMapper(FavoriteMapper.class);
		boolean result = false; 
		try{
			result = mapper.add(favorite);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("찜 목록 등록 dao oracle - end");
		return result;
	}
	
	public Favorite selectFavorite(Favorite favorite){
		logger.info("찜목록 가져오기 dao oracle - Start");
		
		FavoriteMapper mapper = sqlsession.getMapper(FavoriteMapper.class);
		Favorite result = null; 
		try{
			result = mapper.selectFavorite(favorite);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("찜 목록 가져오기 dao oracle - end");
		
		return result; 
	}
	
	public boolean deleteFavorite(Favorite favorite){
		FavoriteMapper mapper = sqlsession.getMapper(FavoriteMapper.class);
		boolean result = false; 
		try{
			result = mapper.deleteFavorite(favorite);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("찜 목록 등록 dao oracle - end");
		return result;
		
		
	}
	
}
