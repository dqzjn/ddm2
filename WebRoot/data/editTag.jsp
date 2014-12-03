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

<title>编辑标签</title>
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
	background-image: url(images/);
}
.button_b{height: 18px;width: 36px;background-image: url(<%=request.getContextPath()%>/images/inputBg.png) ;background-size:cover;background-color: transparent;border: none ;}
</style>
<script type="text/javascript">
	$(document).ready(function(){
  		var optionsSubmit = {
    	    url:'<%=request.getContextPath()%>/locker_saveTag.action',
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
      	$('#submitBtn').click(function(){
	    	if(checkedForm()){
			    $('#pageFrom').ajaxSubmit(optionsSubmit);
		        return false;
	        }
	     });
	 });

	function checkedForm() {
		if ($.trim($("#tag_name").val()) == "") {
			alert("标签名称不能为空!");
			return false;
		}
		return true;
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
<body>
	<form action="" id="pageFrom" name="" method="post">
		<br />
		<fieldset class="fieldsetStyle">
			<legend>
				<font size="3">基本信息</font>
			</legend>
			<div class="fieldsetContent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="infoTableSpace">
					<input type="hidden" id="id" name="tag.id"
						value="${tag.id}" style="width: 500px" />
					<tr>
						<td align="right">标签：</td>
						<td align="left"><input id="tag_name" name="tag.tag_name"
							value="${tag.tag_name}" style="width: 500px" />
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="center"><input type="button"
							id="submitBtn" value="保 存" class="button_b" /> 
							 <input type="button" value="取 消"
							class="button_b" onclick="window.close();" />
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
	</form>
</body>
</html>
