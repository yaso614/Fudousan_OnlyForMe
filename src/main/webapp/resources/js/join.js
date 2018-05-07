/**
 * 
 */

function bs_input_file() {
	$(".input-file").before(
		function() {
			if ( ! $(this).prev().hasClass('input-ghost') ) {
				var element = $("<input type='file' name='file' class='input-ghost' style='visibility:hidden; height:0'>");
				element.attr("name",$(this).attr("name"));
				element.change(function(e){
					element.next(element).find('input').val((element.val()).split('\\').pop());
					var fileReader = new FileReader();
					fileReader.readAsDataURL(e.target.files[0]);
					fileReader.onload = function(e){
						console.log(e);
						document.getElementById('uploadPictures').src = e.currentTarget.result; 
					}
				});
				$(this).find("button.btn-choose").click(function(){
					element.click();
				});
				$(this).find("button.btn-reset").click(function(){
					element.val(null);
					$(this).parents(".input-file").find('input').val('');
				});
				$(this).find('input').css("cursor","pointer");
				$(this).find('input').mousedown(function() {
					$(this).parents('.input-file').prev().click();
					return false;
				});
				return element;
			}
		}
	);
}
$(function() {
	bs_input_file();
});