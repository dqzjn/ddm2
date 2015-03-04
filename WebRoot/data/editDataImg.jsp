<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<base target="_self" />

<title>编辑数据</title>
<%@include file="/include/jquerylib.jsp"%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/demo1.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/jqueryui/themes/default/easyui.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jqueryui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/web/style/layout2.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/data/kindEditor/themes/default/default.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/data/kindEditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="<%=request.getContextPath()%>/data/kindEditor/kindeditor.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/data/kindEditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath()%>/data/kindEditor/plugins/code/prettify.js"></script>
<style type="text/css">
body {
	background: #ffffff;
}
.button_b{height: 18px;width: 36px;background-image: url(<%=request.getContextPath()%>/images/inputBg.png) ;background-size:cover;background-color: transparent;border: none ;}
.button_b1{height: 18px;width: 89px;background-image: url(<%=request.getContextPath()%>/images/inputBg2.png) ;background-size:cover;background-color: transparent;border: none ;}
</style>
<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content1"]', {
				cssPath : '<%=request.getContextPath()%>/data/kindEditor/plugins/code/prettify.css',
				uploadJson : '<%=request.getContextPath()%>/data/kindEditor/jsp/upload_json.jsp',
				fileManagerJson : '<%=request.getContextPath()%>/data/kindEditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterBlur: function(){this.sync();}
			});
			//prettyPrint();
			//alert(document.getElementById("content1").value);
			//alert(document.getElementById("content1").value.length);
		});
	</script>
<script type="text/javascript">
	$(document).ready(function(){
  		var optionsSubmit = {
    	    url:'<%=request.getContextPath()%>/locker_updateDataImg.action',
    	    dataType:'json',
    	    success: function(data) {
    	    	 if(data.result=='success'){
						alert("保存成功");
						//window.returnValue = data;
						window.close();
	    	      }else{
	    	      //	window.returnValue = "error";
	    	    	  alert("保存失败");
	    	      }
    	}};
  		var optionsSubmitInsert = {
    	    url:'<%=request.getContextPath()%>/locker_updateDataImg.action?flag=1',
    	    dataType:'json',
    	    success: function(data) {
    	    	 if(data.result=='success'){
						alert("保存并插入成功!");
						window.returnValue = data;
						window.close();
	    	      }else{
	    	      	window.returnValue = "error";
	    	    	  alert("保存插入失败!");
	    	      }
    	}};
      	$('#submitBtn').click(function(){
      		alert(document.getElementById("content1").value);
      		alert(document.getElementById("content1").value.length);
			//return false;
	    	if(checkedForm()){
	    		//uploadImg();
	    		//$("#submitBtn").attr("disabled", true);  
	    		//$("#saveInsert").attr("disabled", true);
	    		$("#exit").attr("disabled", true); 
	    		$("#addHtml").attr("disabled", true);  
	    		$("#delHtml").attr("disabled", true);  
			    $('#pageFrom').ajaxSubmit(optionsSubmit);
		        return false;
	        }
	     });
      	$('#saveInsert').click(function(){
	    	if(checkedForm()){
	    		$("#submitBtn").attr("disabled", true);  
	    		$("#saveInsert").attr("disabled", true);  
	    		$("#exit").attr("disabled", true); 
	    		$("#addHtml").attr("disabled", true);  
	    		$("#delHtml").attr("disabled", true);  
			    $('#pageFrom').ajaxSubmit(optionsSubmitInsert);
		        return false;
	        }
	     });
      	
	 });

	function checkedForm() {
		if ($.trim($("#title").val()) == "") {
			alert("标题不能为空!");
			return false;
		}
		if ($.trim($("#collect_time").val()) == "") {
			alert("时间不能为空!");
			return false;
		}
		
		if ($("input[name='check']:checkbox:checked").length == 0) {
			alert("标签不能为空!");
			return false;
		}
		if ($.trim($("#collect_website").val()) == "") {
			alert("来源网站不能为空!");
			return false;
		}
		var imgUrls=document.getElementsByName("image");
		if ($.trim($("#url").val()) == ""&&imgUrls[0].value=="") {
			alert("图片不能空!请添加图片url或者上传图片!");
			return false;
		}
		if(imgUrls.length > 1){
			for(var i=0;i<imgUrls.length;i++){
				if(imgUrls[i].value==null){
					alert("上传图片不能为空");
					return false;
				}
			}
		}
		if ($.trim($("#type").val()) == "") {
			alert("数据类型不能为空!");
			return false;
		}
		var d=/\.[^\.]+$/.exec($("#url").val())+'';
		if (d.toLowerCase() == ".gif") {
			document.getElementById("data_type").value= 'gif';
			return true;
		}
		
		if (d.toLowerCase() == ".jpg"||d.toLowerCase() == ".png"||imgUrls[0].value!="") {
			document.getElementById("data_type").value= 'singleImg';
			return true;
		}else{
			alert("url格式不正确!");
			return false;
		}
		
		//if ($.trim($("#data_type").val()) == "gif") 
//		if (d.toLowerCase() != ".jpg"&&d.toLowerCase() != ".png") {
//			if($("#data_type").val()=='joke'||$("#data_type").val()=='news'){
//				alert("类型与url不匹配!");
//				return false;
//			}
//		}

		return true;
	}
	
	//插入云数据库
	function saveInsertDataImg(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
       
		if(!confirm("是否确认插入云数据 ？")){
			return false;
		}
		if(checkedForm()){
			var params = {"ids": ids};  
			var actionUrl = "<%=request.getContextPath()%>/locker_saveInsertDataImg.action";
			$.ajax({
				url : actionUrl,
				type : "post",
				data : params,
				dataType : "json",
				cache : false,
				error : function(textStatus, errorThrown) {
					alert("系统ajax交互错误: " + textStatus.value);
				},
				success : function(data, textStatus) {
					if (data.result == 'success') {
						alert("保存并插入成功！");
						refreshIt();
					} else {
						alert("保存插入失败！");
					}
				}
			});
		}
	}
	
	
	//把已选中的多选框更改成未选中 
	function clearCheckbox(){ 
		$("input[name='check']").each( 
		function(){ 
			if($(this).attr("checked",true)){ 
			$(this).attr("checked",false); 
			} 
		}); 
	} 
	
	function uploadImg() {
    	var imgUrls=document.getElementsByName("image");
	   	 var imgUrl ='';
	   	 if(!+[1,]){
	    		for(var i=0;i<imgUrls.length;i++){
		    		var f = imgUrls[i].value;
		    		f=f.substring( f.lastIndexOf('\\')+1 );
		    		imgUrl=imgUrl+f+'#';
		    	}
		        document.getElementById("imgNames").value = imgUrl;
	    	} else{
	    		for(var i=0;i<imgUrls.length;i++){
		    		var f = imgUrls[i].files;
		    		if(f[0]!='undefined'&&f[0]!=undefined){
		    			imgUrl=imgUrl+f[0].name+'#';
		    		}
		    	}
		        document.getElementById("imgNames").value = imgUrl;
	    	}
    	 
    }
    
    function addHTML(){
    	 var d=document.getElementById('tableId').lastChild;
    	 var a =d.cloneNode(true);
    	 document.getElementById('tableId').appendChild(a);
    }
    function delHTML(){
    	var a=document.getElementById('tableId').lastChild;
    	var d = document.getElementById('tableId').childNodes.length;
    	if(d == 2){
    	}else{
   	    document.getElementById('tableId').removeChild(a);
    	}
    }
    
    //选中数据所包含标签
    function changedTag(){
    	var tagIds='${tagIdList}';
    	var tagIdss=tagIds.replace("[", "").replace("]", "").split(",");
    	var checkboxs =document.getElementsByName("check");
    	for(var i=0;i<checkboxs.length;i++){
    		for(var j =0;j<tagIdss.length;j++){
				if(checkboxs[i].id==$.trim(tagIdss[j])){
					checkboxs[i].checked="true";
					break;
				}
	    	}
    	}
    }
    //删除指定id的table
    function delTab(tabId){
    	document.getElementById("div").removeChild(document.getElementById(tabId));
    	var delImgIds=document.getElementById("delImgIds").value;
    	delImgIds=delImgIds+tabId.substring(3, tabId.length);
    	document.getElementById("delImgIds").value=delImgIds+",";
    	alert("删除成功！");
    }
</script>
<style type="text/css">
html {
	overflow-x: hidden;
	overflow-y: auto;
}

.infoTableSpace {
	font-size: 12px;
}

.infoTableSpace input {
	border: 1px solid #999;
	padding: 5px;
	width: 180px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	margin-bottom: 5px;
	color: #666;
	background: url(../images/input_bg.gif) repeat-x top;
}

.infoTableSpace select {
	border: 1px solid #999;
	padding: 4px;
	width: 190px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	margin-bottom: 3px;
	color: #666;
	background: url(../images/input_bg.gif) repeat-x top;
}

.infoTableSpace textarea {
	border: 1px solid #999;
	padding: 5px;
	width: 590px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	margin-bottom: 5px;
	color: #666;
	background: url(../images/input_bg.gif) repeat-x top;
}

.infoTableSpace .form_bt_grey {
	width: 80px;
	border: 1px solid #aaaaaa;
	background-image: url(../images/bt_bg2.gif);
	height: 27px;
	background-repeat: repeat-x;
	background-color: #e9e9e9;
	color: #333333;
	padding: 0 15px 0 15px;
}

.infoTableSpace .form_bt_orange {
	width: 80px;
	border: 1px solid #aaaaaa;
	background-image: url(../web/images/bt_bg1.gif);
	height: 27px;
	background-repeat: repeat-x;
	background-color: #ffa00a;
	color: #FFFFFF;
	padding: 0 15px 0 15px;
	font-weight: bold;
}

.infoTableSpace .form_bt_blue {
	width: 80px;
	border: 1px solid #6081c3;
	background-image: url(../images/bt_bg1.gif);
	height: 27px;
	background-repeat: repeat-x;
	background-color: #ffa00a;
	color: #FFFFFF;
	padding: 0 15px 0 15px;
	font-weight: bold;
}

#gridTable input {
	border: 1px solid #fff;
	padding: 0px;
	width: 20px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url() repeat-x top;
}

#cb_gridTable {
	border: 1px solid #e6e6e6;
	padding: 0px;
	width: 20px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url(images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x
		50% 50%;
}

#gridPager input {
	border: 1px solid #ccc;
	padding: 1px;
	padding-top: 2px;
	width: 25px;
	height: 14px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url() repeat-x top;
	background-color: #fff;
}

#gridPager select {
	border: 1px solid #ccc;
	padding: 0px;
	width: 50px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url() repeat-x top;
	background-color: #fff;
}
</style>
</head>
<body onload="changedTag()">
	<form action="" id="pageFrom" name="" method="post" enctype="multipart/form-data" style="background-color: #F0F0F0">
		<br />
		<fieldset class="fieldsetStyle">
			<legend>
				<font size="3">基本信息</font>
			</legend>
			<div id="div" class="fieldsetContent">
				<table width="650px;" border="0" cellspacing="0" cellpadding="0"
					class="infoTableSpace" >
					<input type="hidden" id="id" name="dataImgId" value="${dataImgTable.id}" />
					<tr>
						<td align="right">标题：</td>
						<td align="left"><input id="title" name="dataImgTable.title"
							value="${dataImgTable.title}" style="width: 300px" />
						</td>
						<td align="right">来源网站：</td>
						<td align="left"><input id="collect_website"
							name="dataImgTable.collect_website" 
							<c:if test="${userOrg!='0'&&userOrg!='1'}">readonly="readonly"</c:if> value="${dataImgTable.collect_website}" style="width: 120px" />
						</td>
					</tr>
					
					<tr>
						<td align="right">URL：</td>
						<td align="left" colspan="3"><input type="text" id="url"
							<c:if test="${sessionScope.USER_ORG!='0'}"> readonly="readonly" </c:if> name="dataImgTable.url" value="${dataImgTable.url}"
							style="width: 555px" />
						</td>
					</tr>
					<tr>
						<td align="right">数据类型：</td>
						<td align="left"><select id="type"
							name="dataImgTable.type" style="width:200px;">
								<option value="">--请选择--</option>
								<option value="joke"
									<c:if test="${dataImgTable.type=='joke'}">selected="selected"</c:if>>搞笑</option>
								<option value="news"
									<c:if test="${dataImgTable.type=='news'}">selected="selected"</c:if>>新闻</option>
								<option value="film"
									<c:if test="${dataImgTable.type=='film'}">selected="selected"</c:if>>影视</option>
								<option value="entertainment"
									<c:if test="${dataImgTable.type=='entertainment'}">selected="selected"</c:if>>娱乐</option>
								<option value="game"
									<c:if test="${dataImgTable.type=='game'}">selected="selected"</c:if>>游戏</option>
						</select>
						</td>
						<td align="right">发布时间：</td>
						<td align="left"><input id="collect_time"
							name="dataImgTable.collect_time" readonly="readonly"
							value="${dataImgTable.collect_time}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px" />
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<td align="right">标签：</td>
						<td align="left" colspan="3">
						<c:forEach var="tag" items="${tagList}" varStatus="vs">
							<input style="width: 30px;border: 0px;" type="checkbox"
								name="check"  id="${tag[0]}" value="${tag[0]}"/>${tag[1]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <c:if test="${(vs.count % 5)==0}"><br/></c:if>
						</c:forEach>
						</td>
					</tr>
						<tr>
							<td align="right">上传封面：</td>
							<td align="left" colspan="3"><input type="file" id="image"
								name="image" value="${image}" onchange="uploadImg()"/>
								<input type="hidden" name="cover" <c:if test="${fn:length(imgList)>0 }">value="${dataImgTable.url }"</c:if> />
							</td>
						</tr>
						<tr>
						<td colspan="4">
							<c:if test="${fn:length(imgList)>0 }">
								<c:forEach var="img" items="${imgList }" varStatus="vs">
									<textarea id="content1" name="content1" cols="100" rows="8" style="width:669px;height:200px;visibility:hidden;">${img[1]}</textarea>
								</c:forEach>
							</c:if>
							<c:if test="${fn:length(imgList)==0 }">
								<textarea id="content1" name="content1" cols="100" rows="8" style="width:669px;height:200px;visibility:hidden;">${dataImgTable.imgUrl}</textarea>
							</c:if>
						</td>
					</tr>
					</table>
				<table>
				   <tr>
				   	<td>
					   	<input type="button" id="addHtml" value="+图片" onclick="addHTML()"  class="button_b"/> 
							<input type="button" id="delHtml" value="-图片" onclick="delHTML()"  class="button_b"/> 
						</td>
				    </tr>
					<tr>
						<td colspan="4" align="center"><input type="button"
							id="submitBtn" value="保 存" class="button_b" /> 
							<c:if test="${userOrg=='0'}"><input type="button" id="saveInsert" value="保存并入云库" class="button_b1" /></c:if>
							 <input type="button" value="取 消" id="exit"
							class="button_b" onclick="window.close()" />
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<input type="hidden" name="imgNames" id="imgNames" value="${imgNames}"/>
		<input type="hidden" name="delImgIds" id="delImgIds" value=""/>
		<input type="hidden" name="dataImgTable.data_type" id="data_type" value="${dataImgTable.data_type}"/>
	</form>
</body>
</html>
