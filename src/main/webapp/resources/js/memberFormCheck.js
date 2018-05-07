/**
 * Member Form Check 
 */

function formsubmit() {
	
	if ($("#InputName").val() == null || $("#InputName").val() == "") {
		$("#InputName").focus();
		return false;
	}
	if ($("#InputPassword1").val() == null || $("#InputPassword1").val() == "") {
		$("#InputPassword1").focus();
		return false;
	}
	
	if ($("#InputPassword2").val() == null || $("#InputPassword2").val() == "") {
		$("#InputPassword2").focus();
		return false;
	}
	
	if ($("#InputPassword1").val() != $("#InputPassword2").val()) {
		alert("パスワード①とパスワード②が異なります。");
		return false;
	}
	
	if ($(".glyphicon-remove").length > 0) {
		alert("正しいメールアドレスを入力してください");
		return false;
	}

	if ($("#agree").prop("checked")) {
		return false; 
	}
	
	return true;
}