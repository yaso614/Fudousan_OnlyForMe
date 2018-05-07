package com.real.fudousan.item.service;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.real.fudousan.common.util.FileService;
import com.real.fudousan.item.dao.ItemDAO;
import com.real.fudousan.item.vo.Item;
import com.real.fudousan.item.vo.ItemType;
import com.real.fudousan.room.vo.Room;

@Service
public class ItemService {
	private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
	
	private static final String modelFileBaseDirectory = "/model/";
	
	private static final String PREVIEW_DIR = "/item/preview/";
	
	@Autowired
	private ItemDAO itemDao;
	
	/**
	 * 아이템을 추가한다.
	 * @param item
	 * @param files 파일이 있으면 함께 저장한다.
	 * @return 추가한 아이템 ID, 실패시 -1
	 */
	@Transactional
	public int addItem(Item item, List<MultipartFile> files) {
		logger.info("addItem(" + item + ", " + files +  ") Start");
		item.setFileDirectory(modelFileBaseDirectory);
		int result = -1;
		if((result = itemDao.insert(item)) >= 0 && files != null) {
			logger.info("DAO insert -> item ID : " + result);
			for(MultipartFile file : files) {
				FileService.saveFile(file, modelFileBaseDirectory + result, true, true);
			}
		}
		logger.info("addItem(" + item + ", " + files +  ") End");
		return result;
	}
	
	/**
	 * 아이템을 수정한다.
	 * @param item
	 * @param files 파일이 있으면 함께 수정한다.
	 * @return
	 */
	public boolean modifyItem(Item item) {
		logger.info("modifyItem(" + item + ") Start");
		boolean result = false;
		result = itemDao.update(item);
		logger.info("modifyItem(" + item + ") End");
		return result;
	}
	
	/**
	 * 아이템을 삭제한다.
	 * @param itemId
	 * @return
	 */
	public boolean deleteItem(int itemId) {
		logger.info("deleteItem("+itemId+") Start");
		boolean result = false;
		
		if((result = itemDao.delete(itemId))) {
			logger.info("DAO delete -> " + result);
			FileService.deleteDirectory(modelFileBaseDirectory + itemId);
		}
		logger.info("deleteItem("+itemId+") end");
		return result;
	}
	
	/**
	 * 아이템 상세 보기
	 * @param itemId
	 * @return
	 */
	public Item viewItem(int itemId) {
		logger.info("allList() Start");
		Item result = null;
		result = itemDao.select(itemId);
		
		logger.info("allList() end");
		return result;
	}
	
	/**
	 * 모든 아이템 리스트
	 * @return
	 */
	public List<Item> allList() {
		logger.info("allList() Start");
		List<Item> result = null;
		result = itemDao.selectAll();
		
		logger.info("allList() end");
		return result;
		
		
	}
	
	/**
	 * 아이템 이름 포함 검색
	 * @param itemName
	 * @return
	 */
	public List<Item> searchName(String itemName) {
		logger.info("allList() Start");
		List<Item> result = null;
		result = itemDao.selectByName(itemName);
		
		logger.info("allList() end");
		return result;
	}
	
	public File[] viewFilesInItem(int itemId) {
		logger.info("viewFilesInItem("+itemId+") Start");
		File[] result = FileService.getFilesInDirectory(modelFileBaseDirectory + itemId);
		
		logger.info("viewFilesInItem("+itemId+") end");
		return result;
	}
	
	public boolean downloadFile(String filePath, String fileName, OutputStream os) {
		logger.info("downloadFile({}, {}) Start", filePath, fileName);
		boolean result = false;
		FileService.writeFile(modelFileBaseDirectory + filePath + "/" + fileName, os);
		result = true;
		logger.info("downloadFile({}, {}) End", filePath, fileName);
		return result;
	}
	

	//아이템 목록 가져오기
	public ArrayList<Item> itemlist(int itemTypeId){
		ArrayList<Item> result = itemDao.itemlist(itemTypeId);
		return result;
	}

	public List<ItemType> getItemTypeList() {
		logger.info("getItemTypeList() Start");
		List<ItemType> result = null;
		result = itemDao.selectAllItemType();
		logger.info("getItemTypeList() End");
		return result;
	}
	
	public String savePreview(int itemId, MultipartFile file) {
		logger.info("savePreview("+itemId+", "+file.getOriginalFilename()+") Start");
		
		String result = null;
		FileService.deleteFile(PREVIEW_DIR + file.getOriginalFilename());
		result = FileService.saveFile(file, PREVIEW_DIR, true);
		result = PREVIEW_DIR+result;
		
		Item item = new Item();
		item.setItemId(itemId);
		item.setItemPreview(result);
		
		// DB 저장
		if ( itemDao.updatePreview(item) != 1 ) {
			FileService.deleteFile(PREVIEW_DIR+result);
			result = null;
		}

		logger.info("savePreview("+itemId+", "+file.getOriginalFilename()+") End");
		return result;
	}
	
	public boolean downloadPreviewFile(String fileName, OutputStream os) {
		logger.info("downloadPreviewFile({}) Start", fileName);
		boolean result = false;
		FileService.writeFile(PREVIEW_DIR + fileName, os);
		result = true;
		logger.info("downloadPreviewFile({}) End", fileName);
		return result;
	}
}
