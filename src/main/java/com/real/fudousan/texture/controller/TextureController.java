package com.real.fudousan.texture.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.item.vo.Item;
import com.real.fudousan.member.controller.MemberController;
import com.real.fudousan.texture.service.TextureService;
import com.real.fudousan.texture.vo.Texture;

@Controller
public class TextureController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private TextureService textureService;
	
	//업로드 페이지로 넘어왓을때 
	@RequestMapping(value="textureuproadpage", method=RequestMethod.GET)
	public String  textureuproadpage(Model model, HttpSession session , Agency agency){
		logger.info("텍스쳐 업로드 페이지로 이동");
	
		return "/textureuproadpage";
	}
		 

			
	// 업로드 버튼 눌렀을때 
	@RequestMapping(value="textureuproad", method=RequestMethod.POST)
	public String textureuproad(String name, String text ,HttpSession session
				,MultipartFile file ){
		logger.info("텍스쳐 업로드 ");

		int memberId = (int)session.getAttribute("loginId");
			
		Texture texture = new Texture();
			
		texture.setMemberId(memberId);
		texture.setName(name);
		texture.setText(text);
			
		textureService.textureuproad(texture, file);
		return "redirect:/textureuproadpage";
	}
	
	@RequestMapping(value = TextureService.modelFileBaseDirectory+"{file_name}.{file_ext}", method = RequestMethod.GET)
	public void getFile(
			@PathVariable("file_name") String fileName, 
			@PathVariable("file_ext") String fileExt, 
			HttpServletResponse response) {
		
		logger.info("getFile({}) Start", fileName+"."+fileExt);
		try {
			textureService.downloadFile(fileName+"."+fileExt, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("getFile({}) end", fileName+"."+fileExt);
		
	}
}
