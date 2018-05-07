package com.real.fudousan.item.dao;

import java.util.ArrayList;
import java.util.List;

import com.real.fudousan.item.vo.Item;
import com.real.fudousan.item.vo.ItemType;

public interface ItemMapper {
	public int insert(Item item);
	public int insertRefSite(Item item);
	public List<Item> selectAll();
	public Item selectById(int itemId);
	public boolean update(Item item);
	public boolean deleteAllRefSite(int itemId);
	public boolean delete(int itemId);
	public List<Item> selectByName(String itemName);

	public ArrayList<Item> itemlist(int itemTypeId);
	public ArrayList<Item> selectitem(ItemType itemTypeId);

	public List<ItemType> selectAllItemType();
	
	public int updatePreview(Item item);

}
