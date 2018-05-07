/**
 *  <!-- infinite scroll paging ajax -->
 */
var page = 1; 
var result = true;	
var count = 1; // 페이지가 몇 번 째인가 알려주는 변수  


// init 
$(function(){
	
	getRoomSnapShot();
    
	$('.modal-body').on("scroll", function(){ 				
		var modalHeight = $('.modal-body').height();
		var contentHeight = $('#aritcleView').height();
		var scrollBottom = $('.modal-body').scrollTop()+$('.modal-body').height();

		console.log("modalHeight   " + modalHeight);
		console.log("contentHeight " + contentHeight);
		console.log("scrollBottom  " + scrollBottom);
 				
		if(scrollBottom>contentHeight-modalHeight){			
			if (result == true ) {
				getRoomSnapShot();
				result = false; 
			};
		};
 	}); 
});
    
// get Room Snap Shot Method
function getRoomSnapShot() {
	$.ajax({
		url : "../selectRoomEstate",
		type : "POST",
		data : { "estateId": $('#estateId').val(), "page": page},
		
		//서버로 부터 받아오는 타입
		dataType : "json",
		success : function(data){
			
			console.dir(data);
			
			// 마지막 페이지를 가져온다. 
			var endPage = data.totalPage;
			
			// 내용을 변경해줄 것을 가져온다. 
			var str = "";
					
			// 가져온 데이터가 마지막이면 마지막이라고 표시 해준다. 
			if (page <= endPage ) {
				count++;
				page = count;
				console.log(count);
				$(data.list).each(
					function(index, room){
						str += "<div class="+ "'col-md-4 col-sm-6 co-xs-12 gal-item'" +">"
							+     "<div class="+"'box'"+">"
							+		"<a href='../roomPage?roomId="+room.roomId+"&editable=false' data-toggle="+"'modal'"+">"
						str +=        "<img src="+"'";
						
						if (room.snapshot == null || room.snapshot == "null") {
							str += "/fudousan/resources/image/noimg.gif";
						}else{
							str += "/fudousan"+room.snapshot;
						}
						
						str += "'"+">"
							+	  	"</a>"
							+     "</div>"
							+	"</div>";
				result = true;
				}); //each end
				console.log(str);
				$(".gal-container").append(str);
	
			}else{
				console.log("page End");
			}
		},
		error : function(e){//에러 정보를 갖고 있는 
			alert(JSON.stringify(e));
			result = true;
		} 
	});
		
}
    	