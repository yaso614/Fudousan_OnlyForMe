package com.real.fudousan.favorite.dao;

import java.util.List;

import com.real.fudousan.favorite.vo.Favorite;

public interface FavoriteDAO {

	//사용자가 찜한 모든 것들의 리스트 출력
	public List<Favorite> allMyfavorite(Integer memberId);
	
	//사용자의 찜목록에서 
	public List<Favorite> searchFavorite(int memberId,String favoSearch);
	
	// 찜하기 등록
	public boolean add(Favorite favorite);
	
	public Favorite selectFavorite(Favorite favorite);
	
	public boolean deleteFavorite(Favorite favorite);
	
}
