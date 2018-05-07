package com.real.fudousan.estate.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.real.fudousan.agency.service.AgencyService;
import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.entry.service.EntryService;
import com.real.fudousan.entry.vo.Entry;
import com.real.fudousan.estate.service.EstateService;
import com.real.fudousan.estate.vo.Estate;
import com.real.fudousan.estate.vo.MunicipalityCode;
import com.real.fudousan.estate.vo.TransType;
import com.real.fudousan.member.controller.MemberController;
import com.real.fudousan.room.service.RoomService;




//@SessionAttributes(value="trash")
@Controller
public class EstateController {
	
	
	
	/*테스트*/
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private EstateService estateService;
	@Autowired
	private EntryService entryService;
	@Autowired
	private AgencyService agencyService;
	@Autowired
	private RoomService roomService;
	

	
	//매물등록
	@RequestMapping(value="estatewrite", method=RequestMethod.POST)
	public String estatewrite(Model model, HttpSession session, Estate estate, TransType transType
			,MunicipalityCode municipalitycode , String prefecture ,String municipality,String districtname
			,String neareststation,Integer  timetoneareststation,Integer tradeprice,Integer priceperunit,String floorplan ,Integer area,
			Integer unitprice,String landshape, Integer totalfloorarea ,String buildingyear,String structure,String use,
		    Integer  coverageratio ,Integer  floorarearatio , String address , String estateName, Integer price){
		
		logger.info("매물 등록 시작 컨트롤러");
		Entry entry = new Entry();
		
		System.out.println("estate = " +   estate);
		entry.setPrice(price);
		System.out.println("entry : "  + entry);
		estate.setEstateName(estateName);
		estate.setTransType(transType);
		estate.setMunicipalitycode(municipalitycode);
		estate.setPrefecture(prefecture);
		estate.setMunicipality(municipality);
		estate.setDistrictname(districtname);
		estate.setNeareststation(neareststation);
		estate.setTimetoneareststation(timetoneareststation);
		estate.setTradeprice(tradeprice);
		estate.setPriceperunit(priceperunit);
		estate.setFloorplan(floorplan);
		estate.setArea(area);
		estate.setUnitprice(unitprice);
		estate.setLandshape(landshape);
		estate.setTotalfloorarea(totalfloorarea);
		estate.setBuildingyear(buildingyear);
		estate.setStructure(structure);
		estate.setUse(use);
		estate.setCoverageratio(coverageratio);
		estate.setFloorarearatio(floorarearatio);
		estate.setAddress(address);
		estate.setAddress(estate.getPrefecture()+estate.getMunicipality()+estate.getDistrictname()+estate.getAddress());
		
		estate.setEstateX(estate.getEstateX());
		estate.setEstateY(estate.getEstateY());
		
		System.out.println("어디까지오나 0" + estate);
		estateService.addEstate(estate);
		
		System.out.println("어디까지오나 1");
		int estate_id = estate.getEstateId();
		System.out.println("어디까지오나 2");
		String email = (String) session.getAttribute("loginEmail");
		System.out.println("어디까지오나 3");
		int agency_id = agencyService.selectAgencyId(email);
		System.out.println("어디까지오나 4");
		logger.info("estate_id : " + estate_id);
		entry.setEstate(estate);
		
		
		Agency agency = new Agency();
		agency.setAgencyId(agency_id);
		entry.setAgency(agency);
		model.addAttribute("entry", entry);
		System.out.println(entry);
		entryService.addEntry(entry);

		logger.info("매물 등록 종료");
		return "redirect:/bm";
	}
	
	
	//삭제
	@RequestMapping(value="deleteEntry" ,method=RequestMethod.GET)
	public String deleteEntry(Model model, HttpSession session, Estate estate, int agencyId , int estateId, Agency Select){
		
		Entry entry = new Entry();
		
		Agency agency = new Agency();
		agency.setAgencyId(agencyId);
		entry.setAgency(agency);
		
		Estate deleteEstate = new Estate();
		deleteEstate.setEstateId(estateId);
		entry.setEstate(deleteEstate);
		
		System.out.println("entry : " + entry);
		entryService.deleteEntry(entry);
		
			
		return "redirect:bm";
	}

	 @RequestMapping(value="okhtest", method=RequestMethod.GET)
	    public String prototype() {
		 logger.info("okh 테스트 페이지 이동");
	    	return "/okhtest";
	    }
	 @RequestMapping(value="bw", method=RequestMethod.GET)
	    public String bw() {
		 logger.info("매물 등록 페이지 이동");
	    	return "/brokers/bw";    	
	 }
	 //수정 페이지로 이동  
	 @RequestMapping(value="bc", method=RequestMethod.GET)
	    public String modifyEstatePage(Model model, HttpSession session, int estateId, Entry entry, Integer price){
		
		 
		 logger.info("매물 수정 페이지로 이동 ");
		 logger.debug("estateId(bc) : " + estateId);
		 System.out.println(price);
		 
		Estate estate = estateService.viewEstate(estateId);
		model.addAttribute("estate", estate);
		
		Entry entry1 = entryService.listEntry(estateId);
		System.out.println("entry 1 - " + entry1);
		entry.getPrice();
		model.addAttribute("entry", entry1) ;
		 System.out.println(estate);
		 System.out.println(entry);

	    	return "/brokers/bc";
	 }
	 
	 /*수정*/
	@RequestMapping(value="updateByIds", method=RequestMethod.POST)
	    public String updateByIds(Model model, HttpSession session, Estate estate, TransType transType
				,MunicipalityCode municipalitycode , String prefecture ,String municipality,String districtname
				,String neareststation,Integer  timetoneareststation,Integer tradeprice,Integer priceperunit,String floorplan ,Integer area,
				Integer unitprice,String landshape, Integer totalfloorarea ,String buildingyear,String structure,String use,
			    Integer  coverageratio ,Integer  floorarearatio , String address , String estateName, Integer price){
			
			logger.info("매물 수정 시작(컨트롤러)");
			Entry entry = new Entry();
			
			System.out.println("(수정)estate = " +   estate);
			entry.setPrice(price);
			estate.setEstateName(estateName);
			estate.setTransType(transType);
			estate.setMunicipalitycode(municipalitycode);
			estate.setPrefecture(prefecture);
			estate.setMunicipality(municipality);
			estate.setDistrictname(districtname);
			estate.setNeareststation(neareststation);
			estate.setTimetoneareststation(timetoneareststation);
			estate.setTradeprice(tradeprice);
			estate.setPriceperunit(priceperunit);
			estate.setFloorplan(floorplan);
			estate.setArea(area);
			estate.setUnitprice(unitprice);
			estate.setLandshape(landshape);
			estate.setTotalfloorarea(totalfloorarea);
			estate.setBuildingyear(buildingyear);
			estate.setStructure(structure);
			estate.setUse(use);
			estate.setCoverageratio(coverageratio);
			estate.setFloorarearatio(floorarearatio);
			estate.setAddress(address);
			
			
			System.out.println(price);
			System.out.println("estate2 = " +   estate);
				
			
			estateService.updateByIds(estate);
			System.out.println("수정 여기까지 1");
			int estate_id = estate.getEstateId();
			System.out.println("수정 여기까지2");
			
			String email = (String) session.getAttribute("loginEmail");
			System.out.println("수정 여기까지 3");
			int agency_id = agencyService.selectAgencyId(email);
			
			logger.info("estate_id : " + estate_id);
			entry.setEstate(estate);
			Agency agency = new Agency();
			agency.setAgencyId(agency_id);
			entry.setAgency(agency);
			
			entryService.updateByIds(entry);

			logger.info("매물 수정 완료");
		return "redirect:/bm";
	}
	
	 @RequestMapping(value="bm", method=RequestMethod.GET)
	 public String bm(Model model, HttpSession session,Agency agency) {
		logger.info("매니저 페이저 진입 / 매물 목록 출력 완료  ");
		 
		String email = (String) session.getAttribute("loginEmail");
		System.out.println("Login Email: " + email);
		ArrayList<Estate> select = new ArrayList<Estate>();
		select = (ArrayList<Estate>) estateService.getEsates(email);
		
		//여기에 estateid 들어가있음 
		model.addAttribute("select", select);
	
		//에이전시 아이디 
		//int agencyId = agencyService.selectAgencyId(email);
		int agencyId = (int)session.getAttribute("loginId");
		
		model.addAttribute("agencyId", agencyId);
		
		// 해당 중개사가 만든 방
		model.addAttribute( "roomList", roomService.showAllRoom(agencyId) );
		
		System.out.println();
		System.out.println(email);

		System.out.println(select);
		// System.out.println(estate);
		logger.info("매물 목록 출력 완료  ");
	    return "/brokers/bm";
	 }
	 
	 	

	/*메물 상세 페이지 이동*/
	 
	 @RequestMapping(value="estate/detailedinfomation", method=RequestMethod.GET)
	public String detailedInfomation(Estate estate, String id, Model model){
		logger.info("매물 상세 보기 이동 시작 ");
		System.out.println("id"+id);
		String result[] = id.split(":"); 
		if(result.length == 0) {
			logger.info("id가 없이 매물 정보를 요청하였습니다.");
			 return "redirect:/";
		}
		String estateIdString = result[1];	
		int estateId=Integer.parseInt(estateIdString);
		
		System.out.println(estateId);
		Estate resultEstate= estateService.viewEstate(estateId);
		model.addAttribute("estateId", estateId);
		model.addAttribute("resultEstate",resultEstate);
		
		logger.info("매물 상세 보기 이동 종료 "); 
		 return "estate/detailedinfomation";
	}

	@ResponseBody
	@RequestMapping(value="codecheck", method=RequestMethod.GET)
	public String codeCheck(int municipalitycodeId){
		String result = null;
		logger.info("서비스 지역 여부 확인 "); 
		System.out.println(municipalitycodeId);
	
		result = estateService.codecheck(municipalitycodeId);
		
		System.out.println(result);
		
		return result;
	}
}
