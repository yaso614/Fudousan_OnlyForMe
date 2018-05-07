package com.real.fudousan.estate.service;

import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.real.fudousan.estate.dao.EstateDAO;
import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.estate.vo.MunicipalityCode;
import com.real.fudousan.estate.vo.TransType;

@Service
public class EstateService {
	private static final Logger logger = LoggerFactory.getLogger(EstateService.class);
	
	@Autowired
	private EstateDAO dao;
	
	/**
	 * 해당 매물 정보를 가져온다.
	 * @param estateId
	 * @return
	 */
	/*public Estate viewEstate(int estateId) {
	
		return null;
	}*/
	
	//수정페이지로 이동
	public Estate viewEstate(int estateId) {
		
		Estate result = dao.viewEstate(estateId);
		
		return result;
	}
	/**
	 * 매물 추가
	 * @param estate
	 * @return
	 * (완료)
	 */
	public int addEstate(Estate estate) {
		

		String address1 = estate.getPrefecture();
		String address2 = estate.getMunicipality();
		String address3 = estate.getDistrictname();
		String address4 = estate.getAddress();
		String address = address1 + address2 + address3 + address4;
		logger.debug("address : " + address);
		String locationInfo = address;
		   RestTemplate restTemplate = new RestTemplate();
		   String locationResult = restTemplate.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address={a}&key=AIzaSyAlZMVBrvQGWP2QTDvf5ur7HrtEC3xlOf0", String.class, locationInfo);
		   System.out.println("locationInfo:::"+locationResult);
		   
		   
		   try {
		      
		      JSONParser jsonParser = new JSONParser();
		      JSONObject jsonObject = (JSONObject) jsonParser.parse(locationResult);
		      JSONArray  locationArray = (JSONArray) jsonObject.get("results");
		      System.out.println("locationArray" + locationArray);
		      for (int i = 0; i < locationArray.size(); i++) {
		         JSONObject  geometry= (JSONObject)locationArray.get(i);
		         JSONObject geometryLocation=(JSONObject)geometry.get("geometry");
		         JSONObject location2 = (JSONObject)geometryLocation.get("location");
		         System.out.println("geometryLocation: "+ geometryLocation);
		      
		         String lat2 = location2.get("lat").toString();
		         String lng2 = location2.get("lng").toString();
		    
		         
		         estate.setEstateX(Double.parseDouble(lat2));
		         estate.setEstateY(Double.parseDouble(lng2));
		         
		        /* entry.getEstate().setEstateX(Double.parseDouble(lat2));
		         entry.getEstate().setEstateY(Double.parseDouble(lng2));*/
		         
		         System.out.println("estate 서비스 " + estate);
		      }
		      
		   } catch (Exception e) {
		      // TODO: handle exception
		   }
	
		int result = dao.insertByIds(estate);
		return result;
	}
	
	
	//수정//업데이트
	public int updateByIds(Estate estate){
		int result = dao.updateByIds(estate);
		return result;
		
		
	}
	
	//코드 여부 확인
	public String codecheck(int municipalitycodeId){
	
		String result = dao.codecheck(municipalitycodeId);
		return result;
	}
	

	
	/**
	 * estateId 들을 모두 검색하여 가져온다.
	 * @param estateIds
	 * @return
	 */
	public List<Estate> getEsates(String email) {
		logger.info("getEstates("+email+") Start");
		
		List<Estate> result = dao.select(email);
		
		logger.info("getEstates("+email+") End");
		return result;
	}

	

	// INSERT TRANS 
	public int insertTrans(TransType trans){
		logger.info("insert trans start");
		
		
		int result = dao.insertTrans(trans);
		logger.info("insert trans end");
		return result;
		
	}
	
	// INSERT MUNICIPALITY CODE
	public int insertMunicipalitycode(MunicipalityCode mun){
		
		logger.info("insert municipalitycode start");

		int result = dao.insertMunicipalitycode(mun);
		logger.info("insert municipalitycode end");
		return result;
	}
	
	public List<Estate> selectEsatesLocation() {
		logger.info("selectEsatesLocation Start");
		
		List<Estate> result= null;
		result = dao.selectEsatesLocation();
		
		logger.info("selectEsatesLocation End");
		return result;
	}

	public boolean updateBaseRoomId(int estateId, int roomId) {
		logger.info("updateBaseRoomId("+estateId+", "+roomId+") Start");
		
		boolean result= false;
		result = dao.updateBaseRoomId(estateId, roomId);

		logger.info("updateBaseRoomId("+estateId+", "+roomId+") End");
		return result;
	}
	
}
