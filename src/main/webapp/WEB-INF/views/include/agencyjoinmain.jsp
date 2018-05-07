<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<!--  agency join form  -->
	<article class="container">
		<div class="page-header">
			<h1><span data-lang="7"></span></h1>
		</div>
		<div class="col-md-6 col-md-offset-3">
			<form role="form" action="insertAgency" method="post" enctype="multipart/form-data" onsubmit="return formsubmit()">
				<div class="form-group">
					<label for="InputName" data-lang="8"></label>
					<input type="text" class="form-control" id="InputName" name="memberName" data-lang="8" required="required">
				</div>
				<div class="form-group">
					<label for="InputPassword1" data-lang="3"></label>
					<input type="password" class="form-control" id="InputPassword1" name="password" data-lang="3" required="required">
				</div>
				<div class="form-group">
					<label for="InputPassword2" data-lang="9"></label>
					<input type="password" class="form-control" id="InputPassword2" data-lang="9" required="required">
					<p class="help-block" data-lang="10"></p>
				</div>
				<div class="form-group has-feedback">
					<label for="InputEmail" data-lang="2"></label>
					<input id="email" class="form-control" type="text" name="email" required="required"  data-lang="89">
					<span class="glyphicon form-control-feedback"></span>
				</div>
				<div class="form-group">
					<label for="InputPhone" data-lang="11"></label>
					<input type="text" class="form-control" id="userphone" name="phone" data-lang="12">
				</div>
	            
				<div class="input-group">
					<label for="InputText" data-lang="13"></label>
					<textarea class="form-control" rows="3" cols="100" style="resize:none" name="text" data-lang="14"></textarea>     
				</div>
			
				<div class="form-group">
					<label for="InputPhoto" data-lang="15"></label>
					<div class="col-sm-12">
			    		<img class="col-sm-12" src="" id="uploadPictures">
					</div>
					
					<div class="input-group input-file" name="file">
						<span class="input-group-btn">
							<button class="btn btn-default btn-choose" type="button" data-lang="16"></button>
						</span>
						<input type="text" class="form-control" name="picture" data-lang="17"/>
					
					</div>
				</div>
	
				<div class="form-group">
					<label for="InputDesigner" data-lang="19"></label>
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-secondary ">
							<input type="radio" name="designer" id="option1" autocomplete="off" value="1" > Yes
						</label>
						<label class="btn btn-secondary active">
							<input type="radio" name="designer" id="option2" autocomplete="off" value="0" checked> No
						</label>
					</div>
				</div>
         
				<!-- agency join menu  -->
				<div class="form-group">
					<label for="InputText" data-lang="23"></label>
					<input type="text" class="form-control" id="agencyname" name="name" data-lang="24">
				</div>
            
				<div class="form-group">
					<label for="InputText" data-lang="25"></label>
					<input type="text" class="form-control" id="agencybiznum" name="biznum" data-lang="26">
				</div>
            
				<div class="form-group">
					<label for="InputText" data-lang="27"></label>
					<select class="selectpicker" data-live-search="true" name="main" id="main">
						<option value='0' selected="selected" data-lang="28"></option>
						<!-- 홋카이도 -->
						<option value='北海道' data-lang="29">北海道</option>
						<!-- 아오모리현 -->
						<option value='青森県' data-lang="30">青森県</option>
						<!-- 이와테현 -->
						<option value='岩手県' data-lang="31">岩手県</option>
						<!-- 미야기현 -->
						<option value='宮城県' data-lang="32">宮城県</option>
						<!-- 아키타현 -->
						<option value='秋田県' data-lang="33">秋田県</option>
						<!-- 야마가타현 -->
						<option value='山形県' data-lang="34">山形県</option>
						<!-- 후쿠시마현-->
						<option value='福島県' data-lang="35">福島県</option>
						<!-- 이바라키현 -->
						<option value='茨城県' data-lang="36">茨城県</option>
						<!-- 토치기현 -->
						<option value='栃木県' data-lang="37">栃木県</option>
						<!-- 군마현 -->
						<option value='群馬県' data-lang="38">群馬県</option>
						<!-- 사이타마현 -->
						<option value='埼玉県' data-lang="39">埼玉県</option>
						<!-- 치바현 -->
						<option value='千葉県' data-lang="40">千葉県</option>
						<!--  도쿄도 -->
						<option value='東京都' data-lang="41">東京都</option>
						<!-- 가나가와현 -->
						<option value='神奈川県' data-lang="42">神奈川県</option>
						<!-- 니가타현 -->
						<option value='新潟県' data-lang="43">新潟県</option>
						<!-- 도야마현 -->
						<option value='富山県' data-lang="44">富山県</option>
						<!-- 이시카와현 -->
						<option value='石川県' data-lang="45">石川県</option>
						<!-- 후쿠이현 -->
						<option value='福井県' data-lang="46">福井県</option>
						<!-- 야마나시현 -->
						<option value='山梨県' data-lang="47">山梨県</option>
						<!-- 나가노현 -->
						<option value='長野県' data-lang="48">長野県</option>
						<!-- 기후현 -->
						<option value='岐阜県' data-lang="49">岐阜県</option>
						<!-- 스지오카현 -->
						<option value='静岡県' data-lang="50">静岡県</option>
						<!-- 아이치현 -->
						<option value='愛知県' data-lang="51">愛知県</option>
						<!-- 미에현 -->
						<option value='三重県' data-lang="52">三重県</option>
						<!-- 시가현 -->
						<option value='滋賀県' data-lang="53">滋賀県</option>
						<!-- 교토부 -->
						<option value='京都府' data-lang="54">京都府</option>
						<!-- 오사카부 -->
						<option value='大阪府' data-lang="55">大阪府</option>
						<!-- 효고현 -->
						<option value='兵庫県' data-lang="56">兵庫県</option>
						<!-- 나라현 -->
						<option value='奈良県' data-lang="57">奈良県</option>
						<!-- 와카야마현 -->
						<option value='和歌山県' data-lang="58">和歌山県</option>
						<!-- 돗토리현 -->
						<option value='鳥取県' data-lang="59">鳥取県</option>
						<!-- 시마네현 -->
						<option value='島根県' data-lang="60">島根県</option>
						<!-- 오카야마현 -->
						<option value='岡山県' data-lang="61">岡山県</option>
						<!-- 히로시마현 -->
						<option value='広島県' data-lang="62">広島県</option>
						<!-- 야마구치현 -->
						<option value='山口県' data-lang="63">山口県</option>
						<!-- 도쿠시마현 -->
						<option value='徳島県' data-lang="64">徳島県</option>
						<!-- 가가와현 -->
						<option value='香川県' data-lang="65">香川県</option>
						<!-- 에히메현 -->
						<option value='愛媛県' data-lang="66">愛媛県</option>
						<!-- 고치현 -->
						<option value='高知県' data-lang="67">高知県</option>
						<!-- 후쿠오카현 -->
						<option value='福岡県' data-lang="68">福岡県</option>
						<!-- 사가현 -->
						<option value='佐賀県' data-lang="69">佐賀県</option>
						<!-- 나가사키현 -->
						<option value='長崎県' data-lang="70">長崎県</option>
						<!-- 구마모토현 -->
						<option value='熊本県' data-lang="71">熊本県</option>
						<!-- 오이타현 -->
						<option value='大分県' data-lang="72">大分県</option>
						<!-- 미야자키현 -->
						<option value='宮崎県' data-lang="73">宮崎県</option>
						<!-- 가고시마현 -->
						<option value='鹿児島県' data-lang="74">鹿児島県</option>
						<!-- 오키나와현 -->
						<option value='沖縄県' data-lang="75">沖縄県</option>
					</select>
				</div>
            
				<div class="form-group">
					<label for="InputText" data-lang="76"></label>
					<input type="text" class="form-control" id="agencyaddressmiddle" name="addressMiddle" data-lang="77">
				</div>
            
				<div class="form-group">
					<label for="InputText" data-lang="78"></label>
					<input type="text" class="form-control" id="agencyaddresssmall" name="addressSmall" data-lang="79">
				</div>
            
				<div class="form-group">
					<label for="InputText" data-lang="80"></label>
					<input type="text" class="form-control" id="agencyaddresssub" name="addressSub" data-lang="81">
				</div>
				 
				<div class="input-group">
					<label for="InputText" data-lang="82"><span data-lang="agency-introduction"></span></label>
					<textarea class="form-control" rows="3" cols="100" style="resize:none" name="text" data-lang="83"></textarea>       
				</div>
				
				<br>
				
				<div class="form-group">
					<label data-lang="20"></label>
					<div data-toggle="buttons">
						<label class="btn btn-primary active">
							<span class="fa fa-check"></span>
							<input id="agree" type="checkbox" autocomplete="off" name="checkbox" value="checked">
						</label>
						<span data-lang="21"></span>
					</div>
				</div>
				
				<br>
	         
				<div class="form-group text-center">
					<button type="submit" class="btn btn-info"><span data-lang="5"></span><i class="fa fa-check spaceLeft"></i></button>
				</div>
			</form>
		</div>
	</article>