/**
 * estate Favorite 
 */

	    $(function() {
	    	// select favorite
	    	setTimeout(function(){
			      	$.ajax({
						url : "../selectFavorite",
						type : "POST",
						data : { "estateId": $('#estateId').val(), "memberId": $('#memberId').val() },
						 //서버로 부터 받아오는 타입
						dataType : "json",
						success : function(obj){
								
								// member setting
								var oMemberId = obj.memberId
								var memberId = $('#memberId').val();
								
								//estate setting
								var oEstateId = obj.estate.estateId
								var estateId = $('#estateId').val();
								
								
								if (oMemberId == memberId && oEstateId == estateId ) {
									$("#id-of-input").attr("checked", "checked");
								}else{
									$("input:checkbox[id='#id-of-input']").prop("checked", false);
									
								}
							
						},
						error : function(e){//에러 정보를 갖고 있는 
							
						} 
						
		      	  });
	        }, 100);
	    });

		   $(function(){
		    $( '#id-of-input' ).on('click', function(){
 		    	var check =$('#id-of-input').is(":checked");
		    	
		    	if (check == true) {
					
		    		$.ajax({
						url : "../insertFavorite",
						type : "POST",
						data :   { "estateId": $('#estateId').val(), "memberId": $('#memberId').val() } ,
						 //서버로 부터 받아오는 타입
						dataType : "text",
						success : function(data){
								
						},
						error : function(e){//에러 정보를 갖고 있는 
							
						} 
		    		});
		    		
				}else{
					
					$.ajax({
						url : "../deleteFavorite",
						type : "POST",
						data :   { "estateId": $('#estateId').val(), "memberId": $('#memberId').val() } ,
						 //서버로 부터 받아오는 타입
						dataType : "text",
						success : function(data){
								
						},
						error : function(e){//에러 정보를 갖고 있는 
						
						} 
		    		});
				}
		    });
		   });
