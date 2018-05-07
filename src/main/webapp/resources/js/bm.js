/**
 * 
 */
$(function () {
	$("input[name^=public]:radio").change(function () {
		var roomId = $(this).attr("roomId");
		var value = $(this).val();
		$.ajax({
			url:"./changeRoomPublic?roomId="+roomId+"&roomPublic="+value,
			type:"GET",
			success:function(data) {
				if(data == -1) {
					alert("공개여부 변경에 실패하였습니다.");
					$("input[name=public"+roomId+"]").filter("[value="+(1-value)+"]").prop("checked", true);
				}
			},
			error:function(e) {
				console.log(e);
				alert("공개여부 변경 중 오류가 발생하였습니다.");
				$("input[name=public"+roomId+"]").filter("[value="+(1-value)+"]").prop("checked", true);
			}
		});
	});
});

function roomDeleteListener(roomId) {
	
	$.ajax({
		url:"./deleteRoom",
		type:"GET",
		data:{
			roomId : roomId
		},
		success:function(data) {
			if(data || data == "true") {
				$("#room"+roomId).remove();
			} else {
				alert("삭제에 실패하였습니다.");
			}
		},
		error:function(e) {
			console.log(e);
			alert("삭제 중 오류가 발생하였습니다.");
		}
	});
	
	
	
}