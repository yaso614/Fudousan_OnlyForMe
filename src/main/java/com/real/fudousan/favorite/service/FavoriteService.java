package com.real.fudousan.favorite.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.real.fudousan.favorite.dao.FavoriteDAO;
import com.real.fudousan.favorite.vo.Favorite;

@Service
public class FavoriteService {

	private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);
	
	@Autowired
	private FavoriteDAO dao;
	
	/**
	 * 내가 찜한 매물 확인
	 * @param id
	 * @return
	 */
	public List<Favorite> showAllFavorite(Integer memberId){
		logger.info("FS 사용자의 찜목록을 소환합니다 Start");
		List<Favorite> flist = null; //담을그릇
		flist = dao.allMyfavorite(memberId); //그릇에 담기
		logger.info("FS 사용자의 찜목록을 소환합니다 End");
		return flist;
	}
	
	public List<Favorite> showSearchFavorite(int memberId, String favoSearch){
		logger.info("FS 찜목록에서 사용자의 검색어와 일치하는 찜 불러오기 Start");
		List<Favorite> fslist = null; //담을그릇
		fslist = dao.searchFavorite(memberId, favoSearch);
		logger.info("FS 찜목록에서 사용자의 검색어와 일치하는 찜 불러오기 End");
		return fslist;
	}
	
	
	
	
	public List<Favorite> viewMyFavorite(String id) {
	
		return null;
	}
	
	/**
	 * 찜하기
	 * @param id
	 * @param estateId
	 * @return
	 */
	public boolean add(Favorite favorite) {
		logger.info("찜하기 서비스 시작 ");
		boolean result=dao.add(favorite);
		logger.info("찜하기 서비스 종료 ");
		return result;
	}
	
	public Favorite selectFavorite(Favorite favorite){
		logger.info("찜하기 가져오기 서비스 시작");
		
		Favorite result = dao.selectFavorite(favorite);
		logger.info("찜하기 가져오기 서비스 종료");
		
		return result; 
	}
	
	public boolean deleteFavorite(Favorite favorite){
		logger.info("찜하기 삭제 서비스 시작");
		boolean result = dao.deleteFavorite(favorite);
		logger.info("찜하기 삭제 서비스 종료");
		
		return result; 
	}
	
}
