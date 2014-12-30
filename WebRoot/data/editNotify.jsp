<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

<title>编辑通知</title>
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
<style type="text/css">
body {
	background: #ffffff;
}
.button_b{height: 18px;width: 36px;background-image: url(<%=request.getContextPath()%>/images/inputBg.png) ;background-size:cover;background-color: transparent;border: none ;}
.button_b1{height: 18px;width: 89px;background-image: url(<%=request.getContextPath()%>/images/inputBg2.png) ;background-size:cover;background-color: transparent;border: none ;}
</style>
<script type="text/javascript">
	$(document).ready(function(){
  		var optionsSubmit = {
    	    url:'<%=request.getContextPath()%>/locker_saveNotify.action',
    	    dataType:'json',
    	    success: function(data) {
    	    	 if(data.result=='success'){
						alert("保存成功");
						window.returnValue = data;
						window.close();
	    	      }else{
	    	      	window.returnValue = "error";
	    	    	  alert("保存失败");
	    	      }
    	}};
  		var optionsSubmitInsert = {
    	    url:'<%=request.getContextPath()%>/locker_saveInsertNotify.action',
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
	    	if(checkedForm()){
	    		$("#submitBtn").attr("disabled", true);  
	    		$("#saveInsert").attr("disabled", true);  
	    		$("#exit").attr("disabled", true);  
			    $('#pageFrom').ajaxSubmit(optionsSubmit);
		        return false;
	        }
	     });
      	$('#saveInsert').click(function(){
	    	if(checkedForm()){
	    		$("#submitBtn").attr("disabled", true);  
	    		$("#saveInsert").attr("disabled", true);  
	    		$("#exit").attr("disabled", true);  
			    $('#pageFrom').ajaxSubmit(optionsSubmitInsert);
		        return false;
	        }
	     });
      	
	 });

	function checkedForm() {
		if ($.trim($("#title").val()) == "") {
			alert("通知不能为空!");
			return false;
		}
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
	
    function uploadImg() {
    	var f = document.getElementById("image").files;  
        document.getElementById("imgName").value=f[0].name;
    };
    
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
<body>
	<form action="" id="pageFrom" name="" method="post" enctype="multipart/form-data">
		<br />
		<fieldset class="fieldsetStyle">
			<legend>
				<font size="3">基本信息</font>
			</legend>
			<div class="fieldsetContent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="infoTableSpace">
					<c:if test="${notify.id!=''&&notify.id!=null}"><input type="hidden" id="id" name="notify.id" value="${notify.id}" /></c:if>
					<tr>
						<td align="right">标题：</td>
						<td align="left"><input id="title" name="notify.title"
							value="${notify.title}" style="width: 500px" />
						</td>
					</tr>
					<tr>
						<td align="right">类型：</td>
						<td align="left"><input type="text" id="type"
							name="notify.type" value="${notify.type}"
							style="width: 500px" />
						</td>
					</tr>
					<tr>
						<td align="right">URL：</td>
						<td align="left"><input type="text" id="url"
							name="notify.url" value="${notify.url}"
							style="width: 500px" />
						</td>
					</tr>
					<tr>
						<td align="right">应用：</td>
						<td align="left"><input type="text" id="application"
							name="notify.application" value="${notify.application}"
							style="width: 500px" />
						</td>
					</tr>
					<tr>
						<td align="right">优先级：</td>
						<td align="left"><select id="level" name="notify.level" style="width:150px;">
								<option value="0" selected="selected">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次数：
						<select id="times" name="notify.times" style="width:150px;">
								<option value="1" selected="selected">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
						</select>
						</td>
					</tr>
					<tr>
						<td align="right">图标：</td>
						<td align="left"><input id="icon" name="notify.icon"
							value="${notify.icon}" style="width: 500px" />
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<td align="right">时间：</td>
						<td align="left"><input type="text" id="start_time" name="notify.start_time" value="${notify.start_time}" class="input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end_time\')}'})" readonly="readonly" style="width:150px;" />&nbsp;&nbsp;
						至&nbsp;&nbsp;<input type="text" id="end_time" name="notify.end_time" value="${notify.end_time}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start_time\')}'})" readonly="readonly" class="input" style="width:150px;" />
						</td>
					</tr>
					<tr>
						<td align="right">内容：</td>
						<td align="left"><textarea rows="10" cols="5" id="content"
							name="notify.content" value="${notify.content}" style="width: 270px">${notify.content}</textarea>
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td colspan="4" align="center"><input type="button"
							id="submitBtn" value="保 存" class="button_b" /> 
							<c:if test="${sessionScope.USER_ORG=='0'}"><input type="button" id="saveInsert" value="保存并入云库" class="button_b1" /></c:if>
							 <input id="exit" type="button" value="取 消"
							class="button_b" onclick="window.close()" />
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
	</form>
</body>
</html>
