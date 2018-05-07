package com.real.fudousan;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.real.fudousan.agency.service.AgencyService;
import com.real.fudousan.agency.vo.Agency;
import com.real.fudousan.estate.service.EstateService;
import com.real.fudousan.estate.vo.Estate;


@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    private AgencyService service; 
    
    @Autowired
    private EstateService eService; 
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String Home(Model model) {
    	logger.info("Home Start");
    	
    	List<Agency> result = null;
    	List<String> locationList = new ArrayList<>();
    	String location = "";
    	
    	result = service.agencyLocationPrint();
    	/*['loan 1', 33.890542, 151.274856, 'address 1']*/
    	for (Agency agency : result) {
			Double gpsX = agency.getGpsX();
			Double gpsY = agency.getGpsY();
			
			String agencyAddress= "AgencyAddress:"+agency.getAddressMain()+agency.getAddressMiddle()+agency.getAddressSmall()+agency.getAddressSub();
			String agencyId = "AgencyId:"+String.valueOf(agency.getAgencyId());
			
			String lat = gpsX.toString();
			String lng = gpsY.toString();
			
			location = "["+"'"+agencyId+"'"+","+lat +", "+lng+","+"'"+agencyAddress+"'"+"]";
					
			locationList.add(location);
		}

    	List<Estate> Eresult = null;
		List<String> ElocationList = new ArrayList<>();
		List<Integer> estateIdList = new ArrayList<>();
		List<String> addList = new ArrayList<>();
    	Eresult = eService.selectEsatesLocation();
    	
    	for (Estate estate : Eresult) {
    		//System.out.println(estate);
    		
    		Double estateX = estate.getEstateX();
			Double estateY = estate.getEstateY();
			
			String estateId ="EstateId:"+String.valueOf(estate.getEstateId());
			String estateAddress = "EstateAddress:"+estate.getAddress();
			String lat = estateX.toString();
			String lng = estateY.toString(); 
			
			//String estateLocation =  "['"+estateId+"',"+lat +", "+lng+",'"+estateAddress+"']";
			StringBuffer estateLocation = new StringBuffer("['");
			estateLocation.append(estateId);
			estateLocation.append("',");
			estateLocation.append(lat);
			estateLocation.append(",");
			estateLocation.append(lng);
			estateLocation.append(", '");
			estateLocation.append(estateAddress);
			estateLocation.append("']");
			
			ElocationList.add(estateLocation.toString());
			estateIdList.add(estate.getEstateId());
			addList.add(estate.getAddress());
		}
    	model.addAttribute("estateIdList", estateIdList );
    	model.addAttribute("locationList", locationList);
		model.addAttribute("elocationList", ElocationList);
		model.addAttribute("addList", addList);
    	logger.info("Home End");
    	return "home";
    }

    @RequestMapping(value="/prototype", method=RequestMethod.GET)
    public String prototype() {
    	logger.info("prototype Start");
    	logger.info("prototype End");
    	return "prototype/prototype";
    }
    
    @RequestMapping(value="/anhaemin",method=RequestMethod.GET)
    public String stackTest(){
    	return "anhaeminTest";
    }
}
