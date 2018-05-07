package com.real.fudousan.texture.service;

import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.real.fudousan.common.util.FileService;
import com.real.fudousan.estate.service.EstateService;
import com.real.fudousan.texture.dao.TextureDAO;
import com.real.fudousan.texture.vo.Texture;

@Service
public class TextureService {
	private static final Logger logger = LoggerFactory.getLogger(TextureService.class);

	public static final String modelFileBaseDirectory = "/texture/";

	@Autowired
	private TextureDAO dao;

	/**
	 * 텍스쳐 업로드
	 * @param texture
	 * @param fileData
	 * @return
	 */
	public int textureuproad(Texture texture,MultipartFile file) {
		
		int result = 0;
		
		String files = FileService.saveFile(file, modelFileBaseDirectory , true);

			texture.setFile(modelFileBaseDirectory+files);
			
		 result = dao.textureuproad(texture);
		 
		 
		
		
		return result;
	}
	
	public List<Texture> getTextureList() {
		logger.info("getTextureList() Start");
		List<Texture> result = null;
		result = dao.selectAllTexture();
		logger.info("getTextureList() End");
		return result;
	}
	
	public boolean downloadFile( String fileName, OutputStream os) {
		logger.info("downloadFile({}) Start", fileName);
		boolean result = false;
		FileService.writeFile(modelFileBaseDirectory + fileName, os);
		result = true;
		logger.info("downloadFile({}) End", fileName);
		return result;
	}
}
