<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >

 
 <!--  member update form  -->
 
 <article class="container">
        <div class="page-header">
          <h1 data-lang="84"> </h1>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <form role="form" action="modifyMember" method="post" enctype="multipart/form-data" onsubmit="return formsubmit()"  >
          
            <div class="form-group">
              <label for="InputName" data-lang="8" ></label>
              <input type="text" class="form-control" id="InputName" data-lang="8" name="memberName" value="${member.memberName }" required="required">
            </div>
            
            <div class="form-group">
              <label for="InputPassword1" data-lang="3"></label>
              <input type="password" class="form-control" id="InputPassword1" data-lang="3" name="password" required="required">
            </div>
            
            <div class="form-group">
              <label for="InputPassword2" data-lang="9"></label>
              <input type="password" class="form-control" id="InputPassword2"  data-lang="9" required="required">
              <p class="help-block" data-lang="10"></p>
            </div>
            
            <div class="form-group">
              <label for="InputEmail" data-lang="2"></label>
              <input type="email" class="form-control" id="InputEmail" readonly="readonly" data-lang="2" name="email" value="${member.email }" required="required">
            </div>
            
            <div class="form-group">
              <label for="InputText" data-lang="11"></label>
              <input type="text" class="form-control" id="userphone"  data-lang="11" name="phone" value="${member.phone }" >
            </div>
			<div class="input-group">
			     <label for="InputText" data-lang="13"></label>
			     <textarea class="form-control" rows="3" cols="100" style="resize:none"  data-lang="13" name="text" >${member.text }</textarea>     
			</div>
		
			<div class="form-group">
			    <label for="InputPhoto" data-lang="15"></label>
			    <div>
				 	<c:if test="${!empty member.picture}">
			    		<img class="col-sm-12" src="/fudousan${member.picture}" id="uploadPictures">
			   	 	</c:if>
			    	<c:if test="${empty member.picture}">
		    			<img class="col-sm-12" src="" id="uploadPictures">
					</c:if>		
				</div>
				<div class="input-group input-file" name="file">
					<span class="input-group-btn">
		        		<button class="btn btn-default btn-choose" type="button" data-lang="16"></button>
		    		</span>
		    		<input type="text" class="form-control"  data-lang="17" name="picture" value="${member.picture }"/>
		    
				</div>
			</div>
			
            <div class="form-group">
               <label for="InputDesigner" data-lang="19"></label>
               <div class="btn-group" data-toggle="buttons">
               
               	<c:if test="${0 ne member.designer}">
 					<label class="btn btn-secondary active">
				   		 <input type="radio" name="designer" id="option1" autocomplete="off" value="1" checked><span data-lang="104">Yes</span> 
				    </label>
				 	<label class="btn btn-secondary">
				    <input type="radio" name="designer" id="option2" autocomplete="off" value="0"><span data-lang="105">No</span>
				  </label>
				 </c:if>
				
				 <c:if test="${1 ne member.designer}">
					 <label class="btn btn-secondary ">
					   		 <input type="radio" name="designer" id="option1" autocomplete="off" value="1" ><span data-lang="104">Yes</span> 
					    </label>
					 <label class="btn btn-secondary active">
					    <input type="radio" name="designer" id="option2" autocomplete="off" value="0" checked><span data-lang="105">No</span>
					  </label>
				  
				 </c:if>
				  
				 
				</div>
            </div>
        
         
            <div class="form-group text-center">
              <button type="submit" class="btn btn-info" data-lang="84"><i class="fa fa-check spaceLeft"></i></button>
            
            </div>
          </form>
        </div>
</article>
 