<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!empty item && !empty item.itemId}">
	<input type="hidden" id="itemId" name="itemId" value="${item.itemId}">
</c:if>
<div class="form-group">
		<label>アイテム名</label>
	<input id="name" name="itemName" class="form-control" type="text" required="required"<c:if test="${!empty item}"> value="${item.itemName}"</c:if>>
</div>
<div class="form-group">
	<label>アイテム説明</label>
	<input id="text" name="text" class="form-control" type="text"<c:if test="${!empty item}"> value="${item.text}"</c:if>>
</div>
<div class="form-group">
	<label>アイテムタイプ</label>
	<select id="type" name="itemTypeId" class="form-control" required="required">
		<c:forEach var="type" items="${itemTypeList}">
			<option value="${type.itemTypeId}"<c:if test="${!empty item && item.itemType.itemTypeId == type.itemTypeId}"> selected="selected"</c:if>>${type.itemTypeName}</option>
		</c:forEach>
	</select>
</div>
<div class="form-group">
	<label>3Dモデルファイル名</label>
	<input id="model" name="modelFileName" class="form-control" type="text" required="required"<c:if test="${!empty item}"> value="${item.modelFileName}" onchange="onModelFileChange('${item.itemId}')"</c:if>>
</div>
<c:if test="${empty item}">
	<div class="form-group">
		<label>ファイルディレクトリ</label>
		
		<input id="files" name="files" class="form-control " webkitdirectory directory type="file" <c:if test="${empty item}"> required="required"</c:if>>
	</div>
	
</c:if>
<c:if test="${!empty files}">
	<div class="form-group">
		<label>ファイルリスト</label>
			<ul>
			<c:forEach var="file" items="${files }">
				<li>${file.name}</li>
			</c:forEach>
			</ul>
	</div>
</c:if>
<c:if test="${!empty item}">
	<div class="form-group">
		<label>3D詳細設定</label>
		<div class="col-sm-12">
			<div id="preview" class="preview col-sm-8">
			</div>
			<div id="objectController" class="col-sm-4">
				<div>
					<label>Axis X</label>
					<input name="itemRotateX" type="hidden" value="${item.itemRotateX}">
					<div id="ax">${item.itemRotateX}</div>
				</div>
				<div>
					<label>Axis Y</label>
					<input name="itemRotateY" type="hidden" value="${item.itemRotateY}">
					<div id="ay">${item.itemRotateY}</div>
				</div>
				<div>
					<label>Axis Z</label>
					<input name="itemRotateZ" type="hidden" value="${item.itemRotateZ}">
					<div id="az">${item.itemRotateZ}</div>
				</div>
				<div>
					<label>Position X</label>
					<input name="itemX" type="hidden" value="${item.itemX}">
					<div id="px">${item.itemX}</div>
				</div>
				<div>
					<label>Position Y</label>
					<input name="itemY" type="hidden" value="${item.itemY}">
					<div id="py">${item.itemY}</div>
				</div>
				<div>
					<label>Position Z</label>
					<input name="itemZ" type="hidden" value="${item.itemZ}">
					<div id="pz">${item.itemZ}</div>
				</div>
				<div>
					<label>Scale</label>
					<input name="itemScale" type="hidden" value="${item.itemScale}">
					<div id="scale">${item.itemScale}</div>
				</div>
			</div>
			<div class="col-sm-12">
				<label><input id="backgroundEnable" type="checkbox" checked='checked' onchange="backgroundToggle()">背景</label>
				<p class="text-warning"><span class="glyphicon glyphicon-warning-sign"></span>見えるとおりにプレビューに設定されます。</p>
			</div>
		</div>
	</div>
</c:if>
<div class="form-group">
	<label>関連サイト</label>
	<div id="sitelist" style="border: 1px solid ; padding:3%;">
		<c:if test="${!empty item}"> 
			<c:forEach var="site" items="${item.refSiteSet}">
			
				<div id="${site.id}">
					<div class="form-group">
						<label>タイトル</label>
						<input class='form-control' name='titles' type='text' value='${site.text }'>
					</div>
	
					<div class="form-group">
						<label>アドレス</label>
						<input class='form-control' name='sites' type='text' value='${site.url }'>
					</div>
					<input class="btn btn-danger" type="button" onclick="delSite(${site.id})" value="削除">
				</div>
			</c:forEach>
			<script type="text/javascript">backupSiteList();</script>
		</c:if>
	</div>
	<br>
	<input class="btn btn-default" type="button" onClick="addsitecolumn()" value="サイト追加">
</div>
<div class="form-group">
	<input class="col-sm-6 btn btn-success" type="submit" value="アップロード">
	<input class="col-sm-6 btn btn-warning" type="reset" value="リセット">
</div>
<script type="text/javascript">
	threeJSInit(${item.itemX}, ${item.itemY}, ${item.itemZ}, ${item.itemRotateX}, ${item.itemRotateY}, ${item.itemRotateZ}, ${item.itemScale}, ${item.itemId}, '${item.modelFileName}');
</script>