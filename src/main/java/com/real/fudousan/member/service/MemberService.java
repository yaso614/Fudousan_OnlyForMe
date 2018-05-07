package com.real.fudousan.member.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.estate.vo.TransType;
import com.real.fudousan.common.util.FileService;
import com.real.fudousan.member.controller.JoinController;
import com.real.fudousan.member.dao.MemberDAO;
import com.real.fudousan.member.vo.Member;
import com.real.fudousan.member.vo.Permission;

@Service
public class MemberService {
	
	private static final String memberFileBaseDirectory = "/member/";
	
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

	@Autowired
	private MemberDAO dao;
	
	
	

	
	/**
	 * 로그인
	 * @param member
	 * @return [1: not found id] [2: incorrect password] [3: login success]
	 */
	public Member login(Member member) {
		Member result = dao.select(member);
		return result;
	}

	//인테리어 목록만
	public ArrayList<Member> interior(){
		ArrayList<Member> result = dao.interior();
		return result;
	}

	/**
	 * 사용자 회원 가입
	 * @param member
	 * @return
	 */
	public boolean registerMember(Member member, MultipartFile file) {

			Permission p= new Permission(1, "member");
			member.setPermission(p);
			
			int result = 0; 
			result =dao.insertMember(member);
			if (result == 1) {
				// insert success
				return true; 
			}
			else{			
				
				// insert fail 
				return false; 
			}


	}
	
	/**
	 * 인테리어 업자 회원 가입
	 * @param member
	 * @return
	 */
	public boolean registerInterior(Member member, MultipartFile file) {
		

		Permission p= new Permission(2, "Interior");
		member.setPermission(p);
		
		int result = 0; 
		result = dao.insertMember(member);
		
		if (result == 1) {
			// insert success
			return true; 
		}
		else{			
			
			// insert fail 
			return false; 
		}
	
	}
	
	/**
	 * 부동산 업자 회원 가입
	 * @param member
	 * @return
	 */
	public boolean registerAgencyMember(Member member, MultipartFile file) {
		logger.info("start agency member - service");
		Permission p= new Permission(3, "Agency");
		member.setPermission(p);
		
		int result = 0; 

		result = dao.insertAgencyMember(member);
		
		if (result == 1) {
			// insert success
			logger.info("end agency member - service");
			return true; 
		
		}
		else{			
			// insert fail 
			logger.info("end agency member - service");
			return false; 
		}
	}
	
	/**
	 * 부동산 업자 회원 가입
	 * @param member
	 * @param agency
	 * @return
	 */
	public boolean registerAgency(Agency agency) {
		
		logger.info("agency register start");
		int result = 0;	
		
		
		
		// 중개 업소 주소 -> 좌표 변경 세팅  DB에 저장 
		Double lat=0.0; 
		Double lng=0.0;
		String location = "";		
		String address = ""; 
		String Main = agency.getAddressMain(); 
		String Middle = agency.getAddressMiddle();
		String Small = agency.getAddressSmall();
		String Sub = agency.getAddressSub();
		address = Main + Middle + Small + Sub; 
		logger.debug("address : " + address);
		//매물 정보 API활용해서 가져오기 
    	String locationInfo = address;
		RestTemplate restTemplate = new RestTemplate();
		String locationResult = restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address={a}&key=AIzaSyAlZMVBrvQGWP2QTDvf5ur7HrtEC3xlOf0", String.class, locationInfo);
		System.out.println("locationInfo:::"+locationResult);
		
		
		try {
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(locationResult);
			JSONArray  locationArray = (JSONArray) jsonObject.get("results");
			
			for (int i = 0; i < locationArray.size(); i++) {
				JSONObject  geometry= (JSONObject)locationArray.get(i);
				JSONObject geometryLocation=(JSONObject)geometry.get("geometry");
				JSONObject location2 = (JSONObject)geometryLocation.get("location");
				System.out.println("geometryLocation: "+ geometryLocation);
			
				String lat2 = location2.get("lat").toString();
				String lng2 = location2.get("lng").toString();
				agency.setGpsX(Double.parseDouble(lat2));
				agency.setGpsY(Double.parseDouble(lng2));

			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		// trans type set 
		
		TransType t = new TransType(1, "apart");
		agency.setTransType(t);
		
		// confirm set
		agency.setConfirm(0);
		System.out.println("service:"+agency);
		result = dao.insertAgency(agency);
		
		if (result == 1) {
			// insert success
			logger.info("agency register end");
			return true; 
		}
		else{			
			
			// insert fail 
			logger.info("agency register end");
			return false; 
		}
					
				
	}
	
	/**
	 * 회원 정보 수정
	 * @param member id에 해당하는 회원을 해당 정보로 변경한다.
	 * @return
	 */
	public boolean modifyMember(Member member, MultipartFile file) {
		logger.info("회원 수정 시작 SERVICE");
		int result = 0;
		// get designer
		int designer = member.getDesigner();
		
		if (designer == 0) {
			
			Permission p= new Permission(1, "Member");
			member.setPermission(p);
			result = dao.updateMember(member);
			System.out.println(member);
			
			
		} else if(designer == 1){
			
			Permission p= new Permission(2, "Interior");
			member.setPermission(p);
			result = dao.updateMember(member);
			System.out.println(member);
		
		}
	
		if (result == 1) {
			// insert success
			return true; 
		}
		else{			
			// insert fail 
			return false; 
		}
	}
	
	
	public boolean modifyAgencyMember(Member member, MultipartFile file){
		logger.info("회원 수정 시작 SERVICE");
		int result = 0;
		Permission p= new Permission(3, "Agency");
		member.setPermission(p);
		System.out.println(member);
		result = dao.updateAgencyMember(member);
			
		
		if (result == 1) {
			// insert success
			return true; 
		}
		else{			
			// insert fail 
			return false; 
		}
	}
	
	public boolean modifyAgency(Agency agency){
		logger.info("회원 수정 시작 SERVICE");
		int result = 0;	
		

		// 중개 업소 주소 -> 좌표 변경 세팅  DB에 저장 
		String address = ""; 
		String Main = agency.getAddressMain(); 
		String Middle = agency.getAddressMiddle();
		String Small = agency.getAddressSmall();
		String Sub = agency.getAddressSub();
		address = Main + Middle + Small + Sub; 
		logger.debug("address : " + address);
		
		//중개소 주소 파싱  
    	String locationInfo = address;
		RestTemplate restTemplate = new RestTemplate();
		String locationResult = restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address={a}&key=AIzaSyAlZMVBrvQGWP2QTDvf5ur7HrtEC3xlOf0", String.class, locationInfo);
		System.out.println("locationInfo:::"+locationResult);
		
		
		try {
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(locationResult);
			JSONArray  locationArray = (JSONArray) jsonObject.get("results");
			
			for (int i = 0; i < locationArray.size(); i++) {
				JSONObject  geometry= (JSONObject)locationArray.get(i);
				JSONObject geometryLocation=(JSONObject)geometry.get("geometry");
				JSONObject location2 = (JSONObject)geometryLocation.get("location");
				System.out.println("geometryLocation: "+ geometryLocation);
			
				String lat2 = location2.get("lat").toString();
				String lng2 = location2.get("lng").toString();
				agency.setGpsX(Double.parseDouble(lat2));
				agency.setGpsY(Double.parseDouble(lng2));

			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		
		// trans type set 
		
		TransType t = new TransType(1, "apart");
		agency.setTransType(t);
		
		// confirm set
		agency.setConfirm(0);
		System.out.println("service:"+agency);
		result = dao.updateAgency(agency);
		
		if (result == 1) {
			// insert success
			return true; 
		}
		else{			
			
			// insert fail 
			return false; 
		}
	}
	
	public Member selectMemberOne(Member member){
		logger.info("select Member start - service");
		Member result=dao.selectMemberOne(member);
		logger.info("select Member End - service ");
		return result; 
	}

	public boolean downloadPicture(String fileName, OutputStream os) {
		logger.info("downloadPicture({}) Start", fileName);
		boolean result = false;
		FileService.writeFile(JoinController.uploadPath + "/" + fileName, os);
		result = true;
		logger.info("downloadPicture({}) End", fileName);
		return result;
	}
}


