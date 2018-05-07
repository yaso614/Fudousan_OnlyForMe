<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
 <!--  agency update form  -->
 
 <article class="container">
        <div class="page-header">
          <h1 data-lang="84"></h1>
        </div>
        <div class="col-md-6 col-md-offset-3">
          <form role="form" action="modifyAgency" method="post"  enctype="multipart/form-data" onsubmit="return formsubmit()">
             <div class="form-group">
	              <label for="InputName" data-lang="8"></label>
	              <input type="text" class="form-control" id="InputName" data-lang="8" name="memberName" value="${agency.member.memberName}" required="required">
	            </div>
	            <div class="form-group">
	              <label for="InputPassword1" data-lang="3"></label>
	              <input type="password" class="form-control" data-lang="3" id="InputPassword1"  name="password" required="required">
	            </div>
	            <div class="form-group">
	              <label for="InputPassword2" data-lang="9"></label>
	              <input type="password" class="form-control" id="InputPassword2" data-lang="9" required="required">
	              <p class="help-block" data-lang="10"></p>
	            </div>
	            <div class="form-group">
	              <label for="InputEmail" data-lang="2"></label>
	              <input type="email" class="form-control" id="InputEmail" data-lang="2" name="email" value="${sessionScope.loginEmail}" disabled="disabled" required="required">
	            </div>
	            <div class="form-group">
	              <label for="InputText" data-lang="11"></label>
	              <input type="text" class="form-control" id="userphone" data-lang="11" name="phone" value="${agency.member.phone}">
	            </div>
	            
				<div class="input-group">
				     <label for="InputText" data-lang="13"></label>
				    <textarea class="form-control" rows="3" cols="100" style="resize:none" data-lang="13" name="membertext">${agency.member.text}</textarea>   
				</div>
			
				<div class="form-group">
				    <label for="InputPhoto" data-lang="15"></label>
				    <div>
				    	<c:if test="${!empty agency.member.picture}">
				    		<img class="col-sm-12" src="/fudousan${agency.member.picture}" id="uploadPictures">
				   	 	</c:if>
				    	<c:if test="${empty agency.member.picture}">
			    			<img class="col-sm-12" src="" id="uploadPictures">
						</c:if>					
					</div>
					<div class="input-group input-file" name="file">
						<span class="input-group-btn">
			        		<button class="btn btn-default btn-choose" type="button" data-lang="16"></button>
			    		</span>
			    		<input type="text" class="form-control" data-lang="17"  name="picture" value="${agency.member.picture }"/>
			    	
					</div>
				</div>
	
	            <div class="form-group">
	               <label for="InputDesigner" data-lang="19"></label>
	               <div class="btn-group" data-toggle="buttons">
					  <label class="btn btn-secondary<c:if test="${agency.member.designer == 1}"> active</c:if>">
					    <input type="radio" name="designer" id="option1" autocomplete="off" value="1"<c:if test="${agency.member.designer == 1}"> checked="checked"</c:if>> Yes
					  </label>
					  <label class="btn btn-secondary<c:if test="${agency.member.designer == 0}"> active</c:if>">
					    <input type="radio" name="designer" id="option2" autocomplete="off" value="0"<c:if test="${agency.member.designer == 0}"> checked="checked"</c:if>> No
					  </label>
					</div>
	            </div>
         
           	<!-- agency join menu  -->
         
			 <div class="form-group">
              <label for="InputText" data-lang="23"></label>
              <input type="text" class="form-control" id="agencyname" data-lang="24" name="name" value="${agency.name}" required="required">
            </div>
            
             <div class="form-group">
              <label for="InputText" data-lang="25"></label>
              <input type="text" class="form-control" id="agencybiznum" data-lang="26" name="biznum" value="${agency.biznum}" required="required">
            </div>
            
			<div class="form-group">
				<label for="InputText" data-lang="27"></label>
	            <select class="selectpicker" data-live-search="true" name="main">
				  <option value='0'<c:if test="${empty agency.addressMain}"> selected="selected"</c:if>>-- 選択 --</option>
				  <!-- 홋카이도 -->
				  <option value='北海道' data-lang="29" <c:if test="${agency.addressMain == '北海道'}"> selected="selected"</c:if>>北海道</option>
				  <!-- 아오모리현 -->
				  <option value='青森県' data-lang="30" <c:if test="${agency.addressMain == '青森県'}"> selected="selected"</c:if>>青森県</option>
				  <!-- 이와테현 -->
				  <option value='岩手県' data-lang="31" <c:if test="${agency.addressMain == '岩手県'}"> selected="selected"</c:if>>岩手県</option>
				  <!-- 미야기현 -->
				  <option value='宮城県' data-lang="32"<c:if test="${agency.addressMain == '宮城県'}"> selected="selected"</c:if>>宮城県</option>
				  <!-- 아키타현 -->
				  <option value='秋田県' data-lang="33"<c:if test="${agency.addressMain == '秋田県'}"> selected="selected"</c:if>>秋田県</option>
				   <!-- 야가마타현 -->
				  <option value='山形県' data-lang="34"<c:if test="${agency.addressMain == '山形県'}"> selected="selected"</c:if>>山形県</option>
				  <!-- 후쿠시마현-->
				  <option value='福島県' data-lang="35"<c:if test="${agency.addressMain == '福島県'}"> selected="selected"</c:if>>福島県</option>
				  <!-- 이바라키현 -->
				  <option value='茨城県' data-lang="36"<c:if test="${agency.addressMain == '茨城県'}"> selected="selected"</c:if>>茨城県</option>
				  <!-- 토치 기현 -->
				  <option value='栃木県' data-lang="37"<c:if test="${agency.addressMain == '栃木県'}"> selected="selected"</c:if>>栃木県</option>
				  <!-- 군마현 -->
				  <option value='群馬県' data-lang="38"<c:if test="${agency.addressMain == '群馬県'}"> selected="selected"</c:if>>群馬県</option>
				  <!-- 사이타마 현 -->
				  <option value='埼玉県' data-lang="39"<c:if test="${agency.addressMain == '埼玉県'}"> selected="selected"</c:if>>埼玉県</option>
				  <!-- 치바현 -->
				  <option value='千葉県' data-lang="40"<c:if test="${agency.addressMain == '千葉県'}"> selected="selected"</c:if>>千葉県</option>
				 <!--  도쿄도 -->
				  <option value='東京都' data-lang="41"<c:if test="${agency.addressMain == '東京都'}"> selected="selected"</c:if>>東京都</option>
				  <!-- 가나가와 현 -->
				  <option value='神奈川県' data-lang="42"<c:if test="${agency.addressMain == '神奈川県'}"> selected="selected"</c:if>>神奈川県</option>
				  <!-- 니가타 현 -->
				  <option value='新潟県' data-lang="43"<c:if test="${agency.addressMain == '新潟県'}"> selected="selected"</c:if>>新潟県</option>
				  <!-- 도야마 현 -->
				  <option value='富山県' data-lang="44"<c:if test="${agency.addressMain == '富山県'}"> selected="selected"</c:if>>富山県</option>
				  <!-- 이시카와 현 -->
				  <option value='石川県' data-lang="45"<c:if test="${agency.addressMain == '石川県'}"> selected="selected"</c:if>>石川県</option>
				  <!-- 후쿠이 현 -->
				  <option value='福井県' data-lang="46"<c:if test="${agency.addressMain == '福井県'}"> selected="selected"</c:if>>福井県</option>
				  <!-- 야마나시 현 --> 
				  <option value='山梨県' data-lang="47"<c:if test="${agency.addressMain == '山梨県'}"> selected="selected"</c:if>>山梨県</option>
				  <!-- 나가노 현 -->
				  <option value='長野県' data-lang="48"<c:if test="${agency.addressMain == '長野県'}"> selected="selected"</c:if>>長野県</option>
				  <!-- 기후현 -->
				  <option value='岐阜県' data-lang="49"<c:if test="${agency.addressMain == '岐阜県'}"> selected="selected"</c:if>>岐阜県</option>
				  <!-- 스지오카현 -->
				  <option value='静岡県' data-lang="50"<c:if test="${agency.addressMain == '静岡県'}"> selected="selected"</c:if>>静岡県</option>
				  <!-- 아이치현 -->
				  <option value='愛知県' data-lang="51"<c:if test="${agency.addressMain == '愛知県'}"> selected="selected"</c:if>>愛知県</option>
				  <!-- 미에현 -->
				  <option value='三重県' data-lang="52"<c:if test="${agency.addressMain == '三重県'}"> selected="selected"</c:if>>三重県</option>
				  <!-- 시가현 -->
				  <option value='滋賀県' data-lang="53"<c:if test="${agency.addressMain == '滋賀県'}"> selected="selected"</c:if>>滋賀県</option>
				  <!-- 교토부 -->
				  <option value='京都府' data-lang="54"<c:if test="${agency.addressMain == '京都府'}"> selected="selected"</c:if>>京都府</option>
				  <!-- 오사카부 -->
				  <option value='大阪府' data-lang="55"<c:if test="${agency.addressMain == '大阪府'}"> selected="selected"</c:if>>大阪府</option>
				  <!-- 효고현 -->
				  <option value='兵庫県' data-lang="56"<c:if test="${agency.addressMain == '兵庫県'}"> selected="selected"</c:if>>兵庫県</option>
				  <!-- 나라현 -->
				  <option value='奈良県' data-lang="57"<c:if test="${agency.addressMain == '奈良県'}"> selected="selected"</c:if>>奈良県</option>
				  <!-- 와카야마현 -->
				  <option value='和歌山県' data-lang="58"<c:if test="${agency.addressMain == '和歌山県'}"> selected="selected"</c:if>>和歌山県</option>
				  <!-- 돗토리현 -->
				  <option value='鳥取県' data-lang="59"<c:if test="${agency.addressMain == '鳥取県'}"> selected="selected"</c:if>>鳥取県</option>
				  <!-- 시마네현 -->
				  <option value='島根県' data-lang="60"<c:if test="${agency.addressMain == '島根県'}"> selected="selected"</c:if>>島根県</option>
				  <!-- 오카야마현 -->
				  <option value='岡山県' data-lang="61"<c:if test="${agency.addressMain == '岡山県'}"> selected="selected"</c:if>>岡山県</option>
				  <!-- 히로시마현 -->
				  <option value='広島県' data-lang="62"<c:if test="${agency.addressMain == '広島県'}"> selected="selected"</c:if>>広島県</option>
				  <!-- 야마구치현 -->
				  <option value='山口県' data-lang="63"<c:if test="${agency.addressMain == '山口県'}"> selected="selected"</c:if>>山口県</option>
				  <!-- 도쿠시마현 -->
				  <option value='徳島県' data-lang="64"<c:if test="${agency.addressMain == '徳島県'}"> selected="selected"</c:if>>徳島県</option>
				  <!-- 가가와현 -->
				  <option value='香川県' data-lang="65"<c:if test="${agency.addressMain == '香川県'}"> selected="selected"</c:if>>香川県</option>
				  <!-- 에히메현 -->
				  <option value='愛媛県' data-lang="66"<c:if test="${agency.addressMain == '愛媛県'}"> selected="selected"</c:if>>愛媛県</option>
				  <!-- 고치현 -->
				  <option value='高知県' data-lang="67"<c:if test="${agency.addressMain == '高知県'}"> selected="selected"</c:if>>高知県</option>
				  <!-- 후쿠오카현 -->
				  <option value='福岡県' data-lang="68"<c:if test="${agency.addressMain == '福岡県'}"> selected="selected"</c:if>>福岡県</option>
				  <!-- 사가현 -->
				  <option value='佐賀県' data-lang="69"<c:if test="${agency.addressMain == '佐賀県'}"> selected="selected"</c:if>>佐賀県</option>
				  <!-- 나가사키현 -->
				  <option value='長崎県' data-lang="70"<c:if test="${agency.addressMain == '長崎県'}"> selected="selected"</c:if>>長崎県</option>
				  <!-- 구마모토현 -->
				  <option value='熊本県' data-lang="71"<c:if test="${agency.addressMain == '熊本県'}"> selected="selected"</c:if>>熊本県</option>
				  <!-- 오이타현 -->
				  <option value='大分県' data-lang="72"<c:if test="${agency.addressMain == '大分県'}"> selected="selected"</c:if>>大分県</option>
				  <!-- 미야자키현 -->
				  <option value='宮崎県' data-lang="73"<c:if test="${agency.addressMain == '宮崎県'}"> selected="selected"</c:if>>宮崎県</option>
				  <!-- 가고시마현 -->
				  <option value='鹿児島県' data-lang="74"<c:if test="${agency.addressMain == '鹿児島県'}"> selected="selected"</c:if>>鹿児島県</option>
				  <!-- 오키나와 -->
				  <option value='沖縄県' data-lang="75"<c:if test="${agency.addressMain == '沖縄県'}"> selected="selected"</c:if>>沖縄県</option>
				</select>
            </div>
            
             <div class="form-group">
              <label for="InputText" data-lang="76"></label>
              <input type="text" class="form-control" id="agencyaddressmiddle" data-lang="77" name="addressMiddle" value="${agency.addressMiddle }" required="required">
            </div>
            
             <div class="form-group">
              <label for="InputText" data-lang="78"></label>
              <input type="text" class="form-control" id="agencyaddresssmall" data-lang="79"  name="addressSmall" value="${agency.addressSmall }" required="required">
            </div>
            
             <div class="form-group">
              <label for="InputText" data-lang="80">b</label>
              <input type="text" class="form-control" id="agencyaddresssub" data-lang="81"  name="addressSub" value="${agency.addressSub }" required="required">
            </div>
            
			<div class="input-group">
			     <label for="InputText"  data-lang="82"></label>
			    <textarea class="form-control" rows="3" cols="100" style="resize:none" data-lang="83" name="agencytext">${agency.text}</textarea>  
			</div>
         
 			<br>
            <div class="form-group text-center">
              <button type="submit" class="btn btn-info">수정<i class="fa fa-check spaceLeft"></i></button>
            </div>
            
          </form>
        </div>
</article>
 
 


