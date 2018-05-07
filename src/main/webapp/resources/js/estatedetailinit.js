/**
 * estate detail initmap
 */

 function initMap() {
    	var lat = document.getElementById('lat').value;
    	var lng = document.getElementById('lng').value;
    	
        var myOptions = {
                center: new google.maps.LatLng(lat, lng),
                zoom: 17,
                mapTypeId: google.maps.MapTypeId.ROADMAP

            };
        var map = new google.maps.Map(document.getElementById('map'),
                    myOptions);
            
        var marker = new google.maps.Marker({
          position:  new google.maps.LatLng(lat, lng),
          map: map
        });
      }
 
