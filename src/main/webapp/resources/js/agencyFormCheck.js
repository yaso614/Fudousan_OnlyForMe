/**
 * 
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

	if ($("#agencyname").val() == null || $("#agencyname").val() == "") {
		$("#agencyname").focus();
		return false;
	}
	if ($("#agencybiznum").val() == null || $("#agencybiznum").val() == "") {
		$("#agencybiznum").focus();
		return false;
	}
	
	if ($('#main').val() == 0) {
		alert("mainを選択してください");
		return false;
	}
	if ($("#agencyaddressmiddle").val() == null || $("#agencyaddressmiddle").val() == "") {
		$("#agencyaddressmiddle").focus();
		return false;
	}
	if ($("#agencyaddresssmall").val() == null || $("#agencyaddresssmall").val() == "") {
		$("#agencyaddresssmall").focus();
		return false;
	}
	if ($("#agencyaddresssub").val() == null || $("#agencyaddresssub").val() == "") {
		$("#agencyaddresssub").focus();
		return false;
	}
	if ($("#agree").prop("checked")) {
		return false; 
	}
	
	
	
	
	return true;
}