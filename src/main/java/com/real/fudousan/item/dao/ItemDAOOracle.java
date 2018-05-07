package com.real.fudousan.item.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.real.fudousan.item.vo.Item;
import com.real.fudousan.item.vo.ItemType;
import com.real.fudousan.item.vo.RefSite;
import com.real.fudousan.member.dao.MemberMapper;
import com.real.fudousan.member.vo.Member;

@Repository
public class ItemDAOOracle implements ItemDAO {
	private static final Logger logger = LoggerFactory.getLogger(ItemDAOOracle.class);
	
	@Autowired
	private SqlSession session;

	@Override
	@Transactional
	public int insert(Item item) {
		logger.info("insert(" + item + ") Start");
		int result = -1;

		ItemMapper mapper = session.getMapper(ItemMapper.class);
		mapper.insert(item);
		// ID 가져오기
		result = item.getItemId();
		if(item.getRefSiteSet() != null) {
			for(RefSite site : item.getRefSiteSet()) {
				site.setItemId(result);
			}
			if(mapper.insertRefSite(item) != item.getRefSiteSet().size()) {
				throw new RuntimeException("참고 사이트 삽입 중 오류 발생");
			}
		}

		logger.info("insert(" + item + ") End");
		return result;
	}

	@Override
	public List<Item> selectAll() {
		logger.info("selectAll() Start");
		List<Item> result = null;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.selectAll();
		} catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		logger.info("selectAll() End");
		return result;
	}

	@Override
	public Item select(int itemId) {
		logger.info("select("+itemId+") Start");
		Item result = null;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.selectById(itemId);
			logger.debug("result : " + result);
		} catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		logger.info("select("+itemId+") End");
		return result;
	}

	@Override
	@Transactional
	public boolean update(Item item) {
		logger.info("update(" + item + ") Start");
		boolean result = false;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.update(item);
			if(result) {
				result &= mapper.deleteAllRefSite(item.getItemId());
				if (item.getRefSiteSet() != null) {
					for(RefSite site : item.getRefSiteSet()) {
						site.setItemId(item.getItemId());
					}
					result &= mapper.insertRefSite(item) == item.getRefSiteSet().size();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		logger.info("update(" + item + ") End");
		return result;
	}

	@Override
	public boolean delete(int itemId) {
		logger.info("selectAll() Start");
		boolean result = false;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.delete(itemId);
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		logger.info("selectAll() End");
		return result;
	}

	@Override
	public List<Item> selectByName(String itemName) {
		logger.info("selectByName("+itemName+") Start");
		List<Item> result = null;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.selectByName(itemName);
		} catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		logger.info("selectByName("+itemName+") End");
		return result;
	}

	
	public ArrayList<Item> itemlist(int itemTypeId){
		ArrayList<Item> result = null;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.itemlist(itemTypeId);
		} catch(Exception e){
			e.printStackTrace();
		}
		return  result;
	}


	@Override
	public List<ItemType> selectAllItemType() {
		logger.info("sselectAllItemType() Start");
		List<ItemType> result = null;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.selectAllItemType();
		} catch(Exception e) {
			e.printStackTrace();
			result = null;
		}
		logger.info("selectAllItemType() End");
		return result;
	}

	@Override
	public int updatePreview(Item item) {
		logger.info("updatePreview("+item+") Start");
		int result = -1;
		try {
			ItemMapper mapper = session.getMapper(ItemMapper.class);
			result = mapper.updatePreview(item);
		} catch(Exception e) {
			e.printStackTrace();
			result = -1;
		}
		logger.info("updatePreview("+item+") End");
		return result;
	}


}
