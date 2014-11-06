<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <base target="_self"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/web/style/layout.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/grid.locale-cn.js"></script>
    <c:if test="${requestScope.message!=null}">
		<script type="text/javascript">
 	 		     alert('<c:out value="${requestScope.message}"></c:out>');
 	 		     window.close();
 		</script>
	</c:if>
	<script type="text/javascript">
  	  $().ready(function(){
		var options = {
  	    url:'<%=request.getContextPath() %>/login_updatePwd.action',
  	     beforeSubmit: function() {
   				return checking();
    			},
  	    dataType:'json',
  	    success: function(data) {
  	      if(data.result=='success'){
			alert("修改成功");
			window.location='<%=request.getContextPath()%>/login_userLoginOut.action?temp='+Math.round(Math.random()*10000);
  	      }else{
  	    	alert("密码修改失败");
  	      }
  	    }};
		$('#saveBtn').click(function(){
			    	$('#pageFrom').ajaxSubmit(options);
	 	            return false;
    	 });
	});
		function checking(){
		    var newPwd = $("#newPwd").val();
			var oldPwd = $("#oldPwd").val();
		    var psw =$("#psw").val();
	     	newPwd = $.trim(newPwd);
	     	oldPwd = $.trim(oldPwd);
	     	psw   = $.trim(psw);
	    if(oldPwd ==null || oldPwd == "" ){
					alert("旧密码不能为空！");
					$("#oldPwd").focus();
		    		  return false;
		}else if(!checkExp($("#oldPwd").val())){
			alert("旧密码不含有非法字符，请重新输入！");
			$("#oldPwd").focus();
			return false;
		}
			var flag = true;		
	    if(!(flag = checkName())){		
			$("#oldPwd").focus();
			return false;
	  	}
		if(newPwd ==null || newPwd == "" ){
					alert("新密码不能为空！");
					$("#newPwd").focus();
		    		  return false;
		}else if(!checkExp($("#newPwd").val())){
			alert("新密码不含有非法字符，请重新输入！");
			$("#newPwd").focus();
			return false;
		}
		if(psw ==null || psw == "" ){
					alert("新密码确认不能为空！");
					$("#psw").focus();
		    		  return false;
			}else if(!checkExp($("#psw").val())){
					alert("新密码确认含有非法字符，请重新输入！");
					$("#psw").focus();
					return false;
		}		
		if(newPwd!=psw ){
					alert("密码不一致！");
					$("#psw").focus();
						return false;
				}
		}	
		function checkName(){
		var flag = true;
		$.ajax({
		   type: "POST",
		   async: false,//取消异步提交
		   url: "<%=request.getContextPath() %>/login_findPwd.action",
		   dataType: "json",
		   data: {
		   		oldPwd: function(){
					return encodeURIComponent($("#oldPwd").val());
				},
		   },
		   success: function(data){
		   	 if(data.result != "success"){
		     	alert( "旧密码错误，请重新输入！" );
		     	flag = false;
		     }
		   }
		}); 
		return flag;
	}
	function checkExp(inputData){ 
		var regEx = new RegExp(/^(([^\^\.<>%&',:;=?$"'#@!~\]\[{}【】￥（）！·‘’”“。，、？：；—|\/\\/`\|])*)$/);
		var result = inputData.match(regEx);
		if (result == null) {
			return false;
		}
		return true;
	}
	</script>
 	<style type="text/css">
 		body{
 			background:#ffffff; background-image:url(images/);
 		}
   		html{
		    overflow-x: hidden;
			overflow-y: hidden;
		}  		
   </style>
  </head>
  
  <body>
  <form id="pageFrom" action="" name="" method="post">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="5" class="formTableNew">
		  	<tr>
		    <td align="right">旧密码:</td>
		    <td align="left">
			    <div class="div">
					<input type="password" id="oldPwd" name="oldPwd" value="" />
					<img src="<%=request.getContextPath() %>/images/xh.png"></img>
				</div>
		    </td>
		  </tr>	
		  <tr>
		    <td align="right">新密码:</td>
		    <td align="left">
			    <div class="div">
					<input type="password" id="newPwd" name="newPwd" value="" />
					<img src="<%=request.getContextPath() %>/images/xh.png"></img>
				</div>
		    </td>
		  </tr>				                  
		 <tr>
		    <td align="right">确认新密码:</td>
		    <td align="left">
			    <div class="div">
					<input type="password" id=psw name="psw" value="" />
					<img src="<%=request.getContextPath() %>/images/xh.png"></img>
				</div>
		    </td>
		  </tr>				
		   <tr>
		    <td align="right" >
		      
		    </td>
		    <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input type="button" id="saveBtn" value="保存" class="form_bt_orange" />
		    </td>
		  </tr>
		</table>
	<!--表单结束-->
	</form>
  </body>
</html>
