package com.real.fudousan.item.dao;

import java.util.ArrayList;
import java.util.List;

import com.real.fudousan.item.vo.Item;
import com.real.fudousan.item.vo.ItemType;

public interface ItemDAO {
	public int insert(Item item);
	public List<Item> selectAll();
	public Item select(int itemId);
	public boolean update(Item item);
	public boolean delete(int itemId);
	public List<Item> selectByName(String itemName);

	public ArrayList<Item> itemlist(int itemTypeId);

	public List<ItemType> selectAllItemType();

	public int updatePreview(Item item);
}

