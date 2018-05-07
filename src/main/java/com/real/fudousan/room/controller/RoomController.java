package com.real.fudousan.room.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.real.fudousan.advice.service.AdviceService;
import com.real.fudousan.advice.vo.Advice;
import com.real.fudousan.common.util.PageNavigator;
import com.real.fudousan.favorite.service.FavoriteService;
import com.real.fudousan.favorite.vo.Favorite;
import com.real.fudousan.item.service.ItemService;
import com.real.fudousan.item.vo.Item;
import com.real.fudousan.room.service.RoomService;
import com.real.fudousan.room.vo.Room;
import com.real.fudousan.roomitem.service.RoomItemService;
import com.real.fudousan.roomwall.service.RoomWallService;

import com.real.fudousan.roomwall.vo.RoomWall;
import com.real.fudousan.texture.service.TextureService;


@SessionAttributes("loginId")
@Controller
public class RoomController {

	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
	@Autowired
	private RoomService roomService;
	@Autowired
	private FavoriteService favService;
	@Autowired
	private AdviceService advService;
	@Autowired
	private RoomWallService roomWallService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private RoomItemService roomItemService;
	@Autowired
	private TextureService textureService;
	
	@ResponseBody
	@RequestMapping(value="searchMyRoom" , method=RequestMethod.GET)
	public List<Room> searchMyRoom(Model model,String roomSearch,int memberId){
		logger.info("RoomController-searchMyRoom_Start");
		logger.info("매물 이름 검색한 결과 :  " + roomSearch);
		logger.info("검색자ID :  " + memberId);
		List<Room> srlist = roomService.showMyRoom(roomSearch, memberId);
	/*	model.addAttribute("rlist",srlist);

        List<Favorite> flist = favService.showAllFavorite(memberId);
        List<Advice> alist = advService.getRequestList(memberId, Advice.REQUEST);
        List<Advice> rclist = advService.getRequestList(memberId, Advice.CONFIRMED);
        
        model.addAttribute("flist", flist);
        model.addAttribute("alist", alist);
        model.addAttribute("rclist", rclist);*/
		
		logger.info("RoomController-searchMyRoom_End");
		logger.info("srlist : "+srlist);
		return srlist;
	}
	
	@RequestMapping(value="allMyRooms" , method=RequestMethod.GET)
	public String allMyRooms(Model model,int memberId){
		logger.info("RoomController-searchMyRoom_Start");
		List<Room> allRooms = roomService.showAllRoom(memberId);
		model.addAttribute("rlist",allRooms);
		logger.info("RoomController-searchMyRoom_End");
		return "user/mypagecustomer";
	}
	
	@ResponseBody
	@RequestMapping(value="changeRoomPublic" , method=RequestMethod.GET)
	public int changeRoomPublic(@ModelAttribute("loginId") int memberId, int roomId, int roomPublic){
		logger.info("changeRoomPublic("+roomId+", "+roomPublic+") Start");
		
		int result = -1;
		if(roomService.changeRoomPublic(memberId, roomId, roomPublic)) {
			result = roomPublic;
		}
		logger.info("changeRoomPublic("+roomId+", "+roomPublic+") End");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteRoom" , method=RequestMethod.GET)
	public boolean deleteRoom(@ModelAttribute("loginId") int memberId, int roomId){
		logger.info("deleteRoom("+roomId+") Start");
		
		boolean result = roomService.deleteRoom(memberId, roomId);
		logger.info("deleteRoom("+roomId+") End");
		return result;
	}
	
	@RequestMapping(value="deletionLogical",method=RequestMethod.GET)
	public String deletionLogical(@ModelAttribute("loginId") int memberId , int roomId){
		logger.info("deletionLogical("+roomId+") Start");
		boolean result = roomService.deletionLogical(memberId, roomId);
		logger.info("deletionLogical("+roomId+") End");
		return "redirect:mypageNormalUser";
	}
	
	
	@RequestMapping(value="newRoom", method=RequestMethod.GET)
	public String newRoom(@ModelAttribute("loginId") int loginId, Room room, Model model) {
		logger.info("newRoom("+loginId+", "+room+") Start");
		String returnedURL = "redirect:roomPage";
		
		room.setMemberId(loginId);
		int roomId = roomService.createRoom(room);
		model.addAttribute("roomId", roomId);
		// 만약, 실제 방이 존재하지 않는 방이면 벽 생성화면으로 이동한다.
		if(room.getEstate() == null) {
			returnedURL = "redirect:wall/wallPage";
		}
		
		
		logger.info("newRoom("+loginId+", "+room+") End");
		return returnedURL;
	}
	
	@RequestMapping(value="newBaseRoom", method=RequestMethod.GET)
	public String newBaseRoom(@ModelAttribute("loginId") int loginId, int estateId, Model model) {
		logger.info("newBaseRoom("+loginId+", "+estateId+") Start");
		String returnedURL = "redirect:roomPage";
		
		int roomId = roomService.createBaseRoom(loginId, estateId);
		model.addAttribute("roomId", roomId);
		
		returnedURL = "redirect:wall/wallPage";
		
		logger.info("newBaseRoom("+loginId+", "+estateId+") End");
		return returnedURL;
	}
	
	@RequestMapping(value="roomPage", method=RequestMethod.GET)
	public String roomPage(@ModelAttribute("loginId") int loginId, int roomId, Model model, @RequestParam( value="editable", defaultValue="true" ) boolean editable ) {
		logger.info("roomPage("+loginId+", "+roomId+", "+editable+") Start");
		

		Room room = roomService.showRoom(roomId);

		if (room != null) {
			model.addAttribute("room", room);
			
			Map<String, List<?>> map = roomWallService.getWallAndConnector(roomId);
			model.addAttribute("walls", map.get("walls"));
			
			model.addAttribute("itemList", itemService.allList());
			
			model.addAttribute("roomitemList", roomItemService.getRoomItemsInRoom(roomId));
			
			model.addAttribute("textureList", textureService.getTextureList());
			
			model.addAttribute("itemTypeList", itemService.getItemTypeList());
			
			model.addAttribute("editable", editable);
		}
		logger.info("roomPage("+loginId+", "+roomId+", "+editable+") End");
		return "room/room";
	}
	
	
	//룸에 아이템 만들기
	@ResponseBody
	@RequestMapping(value="itemlist", method=RequestMethod.GET)
		public ArrayList<Item> itemlist(int itemTypeId, Model model, HttpSession session){
		
		logger.info("아이템 리스트 시작");
	
		ArrayList<Item> itemlist = new ArrayList<>();
		itemlist= itemService.itemlist(itemTypeId);
		
		System.out.println(itemlist);
		
		return itemlist;
	}
	

	/*방높이*/
	@ResponseBody
	@RequestMapping(value="wallheightchange", method=RequestMethod.POST)
	public boolean wallheightchange(int roomId, int height){
		logger.info("방높이 변경 시작 컨트롤러 ");
		System.out.println("넘어오낭");
		Room room = new Room();
		room.setRoomId(roomId);
		room.setHeight(height);
		System.out.println("room: " + room);
		boolean result = roomService.wallheightchange(room) == 1;
	
		return result;
	}
	/*방이름*/
	@ResponseBody
	@RequestMapping(value="roomTitleChange", method=RequestMethod.POST)
	public boolean roomTitleChange(int roomId, String roomTitle){
		logger.info("방이름 변경 시작 컨트롤러 ");
		System.out.println(roomTitle);
		Room room = new Room();
		room.setRoomId(roomId);
		room.setRoomTitle(roomTitle);
		System.out.println("room: " + room);
		boolean result = roomService.roomtitleChange(room) == 1;
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value="selectRoomEstate", method=RequestMethod.POST)
	public HashMap<String, Object> selecteRoomEstate(
			Model model 
			,String estateId
			, @RequestParam(value="page", defaultValue="1")int page
			){
		logger.info("estate id에 해당하는 3d디자인 방 가져오기 시작");
		final int countPerPage = 50;
		final int pagePerGroup = 5; 
		
		String estateIdResult=estateId.trim();
		
		// estateId(String) --> int 
		int estateIdresult = Integer.parseInt(estateIdResult);
		
		int total = roomService.getTotal(estateIdresult);
		
		PageNavigator navi = new PageNavigator (countPerPage, pagePerGroup, page, total);
		
		// 총 페이지  수 
		
		
		List<Room> list= roomService.selectEstateRoom(estateIdresult, navi.getStartRecord(), navi.getCountPerPage());
		HashMap<String, Object> map = new HashMap<>(); 
		map.put("list", list);
		map.put("totalPage", navi.getTotalPageCount());
		
		logger.info("estate id에 해당하는 3d디자인 방 가져오기 종료");

		return map;

	}


	@ResponseBody
	@RequestMapping(value="snapshot", method=RequestMethod.POST)
	public String snapshot(MultipartHttpServletRequest request){
		logger.info("snapshot() Start");
		
		String result = null;

        Iterator<String> itr =  request.getFileNames();
        if(itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());
            
            result = roomService.saveSnapShot(Integer.parseInt(mpf.getOriginalFilename()), mpf);
        }
		logger.info("snapshot() End");
        return result;
	}
	
	@RequestMapping(value = "/snapshot/{file_name}", method = RequestMethod.GET)
	public void getSnapShotFile(
			@PathVariable("file_name") String fileName, 
			HttpServletResponse response) {
		
		logger.info("getSnapShotFile({}) Start", fileName);
		try {
			roomService.downloadSnapShotFile(fileName, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("getSnapShotFile({}) end", fileName);
		
	}
	
	@ResponseBody
	@RequestMapping(value="changeFloorTexture", method=RequestMethod.GET) 
	public boolean changeFloorTexture(int roomId, int textureId) {
		logger.info("changeFloorTexture("+roomId+", "+textureId+") Start");
		boolean result = false;
		result = roomService.changeFloorTexture(roomId, textureId);
		logger.info("changeFloorTexture("+roomId+", "+textureId+") End");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="changeCeilTexture", method=RequestMethod.GET) 
	public boolean changeCeilTexture(int roomId, int textureId) {
		logger.info("changeCeilTexture("+roomId+", "+textureId+") Start");
		boolean result = false;
		result = roomService.changeCeilingTexture(roomId, textureId);
		logger.info("changeCeilTexture("+roomId+", "+textureId+") End");
		return result;
	}
}
