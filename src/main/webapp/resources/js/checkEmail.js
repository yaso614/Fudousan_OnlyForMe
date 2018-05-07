/**
 * 
 */

	$(function() {
			var emailRegexp = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
			
			function emailClass(status) {
				switch(status) {
					case 'reset':
						$('#email').parent().removeClass('has-success');
						$('#email').parent().removeClass('has-error');
						$('#email').parent().find('span').removeClass('glyphicon-remove');
						$('#email').parent().find('span').removeClass('glyphicon-ok');
						break;
					case 'success':
						$('#email').parent().addClass('has-success');
						$('#email').parent().find('span').addClass('glyphicon-ok');
						break;
					case 'error':
						$('#email').parent().addClass('has-error');
						$('#email').parent().find('span').addClass('glyphicon-remove');
						break;
				}
			}
						
			
			$('#email').on('change', function() {
				emailClass('reset');
				if (!emailRegexp.test($('#email').val())) {
					emailClass('error');
						return false;
					
				} else {
					$.ajax({
						url:'../emailCheck',
						tyep:'GET',
						data:{
							email:$(this).val()
						},
						dataType:'text',
						success:function(data) {
							
							if (data=='true') {
								emailClass('error');
							} else {
								emailClass('success');
							}
							
							
						},
						error:function(err) {
							console.log(err);
						}
					});
					
				}
				
				
				
			});
			
		})