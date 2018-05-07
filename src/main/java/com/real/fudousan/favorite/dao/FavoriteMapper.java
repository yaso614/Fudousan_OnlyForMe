package com.real.fudousan.favorite.dao;

import java.util.List;

import com.real.fudousan.favorite.vo.Favorite;

public interface FavoriteMapper {
	
	public List<Favorite> allFavorite(Integer memberId);
	
	public List<Favorite> searchFavorite(int memberId,String estateId);
	
	public boolean add(Favorite favorite);
	
	public Favorite selectFavorite(Favorite favorite);
	

	public boolean deleteFavorite(Favorite favorite);
}
