<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >


<!-- main layout -->
<div class="container-fluid text-center">    
  <div class="row content">
    
    <!-- side nav(left) -->
    <div class="col-sm-2 sidenav" >
    
    </div>
    
<!-- text main -->
<div class="col-sm-8 text-left"> 
	<h1>Details</h1>
	<p>${agency.addressMain}${agency.addressMiddle}${agency.addressSmall}${agency.addressSub}</p>
    <hr>
	<!-- map  -->
	<div id="map"></div>
	<hr>
		<div class="row row-flex" id="box">
      			<div class="col-sm-6">
          			<div class="price-table pt-bg-blue">
	            	<div>
						<span>Agency Info</span>
					</div>
					<ul>
    					<li>
				    		<h6>Agency Name</h6>
				    		${agency.name}
						</li>
						<li>
							<h5>Agency Trans Type</h5>
   							${agency.transType.transName}
						</li>
						<li>
							<h5>Text</h5>
   							${agency.text}
						</li>
          			</ul>
				</div>
			</div>
			<div class="col-sm-6">
          			<div class="price-table pt-bg-red">
	            	<div>
						<span>Member Info</span>
					</div>
					<ul>
    					<li>
				    		<h6>Member Name</h6>
				    		${agency.member.memberName}
						</li>
						<li>
							<h5>Email</h5>
   							${agency.member.email}
						</li>
						<li>
							<h5>Text</h5>
   							${agency.member.text}
						</li>
						<li>
							<h5>Phone</h5>
   							${agency.member.phone}
						</li>
						<li>
							<h5>Photo</h5>
							<div class="thumnail">
								<img src="/fudousan${agency.member.picture}" style="width: 30%; height: auto;">
								<br>
								<br>
							</div>

						</li>
          			</ul>
				</div>
			</div>
			
			
		</div>
		
</div>
	
		<div class="col-sm-2 sidenav">
			    <div class="row row-flex" >
      			
		</div>
		</div>
	</div>
</div>

<!-- value -->
<input type="hidden" value="${sessionScope.memberId}" id="memberId">
<input type="hidden" value=" ${estateId}" id="estateId">
