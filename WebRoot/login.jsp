<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理登陆</title>
<link rel="stylesheet" href="css/reset.css" type="text/css" />
<link rel="stylesheet" href="css/style.css" type="text/css" />
<c:if test="${requestScope.message!=null}">
	<script type="text/javascript">
		alert('<c:out value="${requestScope.message}"></c:out>');
	</script>
</c:if>
<style>
.log_bt {
	background-image: url(<%=request.getContextPath()%>/images/loginBtn.png);
	BACKGROUND-COLOR: transparent;
	height: 46px;
	width: 176px;
	border: 0px;
	margin-left: 35px;
}
</style>
</head>

<body class="loginPage" onload="checkParent()" leftmargin="0"
	topmargin="0" marginwidth="0" marginheight="0">
	<form method="post" action="login.action" id="loginForm">
		<div class="loginLogoContainer">
			<div class="logo"></div>
		</div>
		<div class="loginContainer">
			<div class="content">
				<div class="loginBox">
					<div style="margin-left: 84px;">
						<a style="font-size: 24px;color: white;" href="javascript:changeDiv(1);">用户登录</a>
						<a style="font-size: 24px;color: white;">&nbsp;&nbsp;|&nbsp;&nbsp;</a>
						<a style="font-size: 24px;color: white;" href="javascript:changeDiv(2);">申请注册</a>
						<br/><br /><br />
					</div>
					<div id="div1"style="width: 100%;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="loginTable">
							<tr>
								<td style="color: white;font-size: 20px;">用户名：<input type="text" name="j_username" id="username"
									class="input_text" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td style="color: white;font-size: 20px;">密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="text" class="input_text" id="password1"
									onkeypress="javascript:hiddenPass(event)"
									onkeyup="this.value=this.value.replace(/./g,'*');" /> <input
									type="hidden" name="j_password" id="password" class="input_text" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td align="center"><input type="button" name="button"
									id="button" onClick="javascript:login()" class="log_bt" /></td>
							</tr>
						</table>
					</div>
					<div id="div2" style="display: none;color: white;width: 256px;height:173px;background-image: url('<%=request.getContextPath()%>/web/images/withe.png');margin-left: 70px;">
						<p style="font-size: 18px;line-height: 30px;padding-left: 15px;padding-right: 5px;padding-top: 25px;">商务合作：13910064771</p>
							<p style="font-size: 18px;line-height: 30px;padding-left: 15px;padding-right: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;pandora@hdlocker.com</p>
							<p style="font-size: 18px;line-height: 30px;padding-left: 15px;padding-right: 5px;padding-top: 8px;">地址：北京市海淀区中关村西区善缘街1号立方庭1-301</p>
					</div>
				</div>
			</div>
		</div>
		</from>
		<div class="loginBottom">北京智美点心科技有限公司 &copy; Copyright 2014</div>
</body>
</html>
<script type="text/javascript">
	String.prototype.trim = function() {
		return this.replace(/^\s+/, '').replace(/\s+$/, '');
	}
	function checkParent() {
		document.getElementById('loginForm').j_username.focus();
		if (window.parent.length > 0) {
			window.parent.location = "login.jsp";
		}
	}
	function login() {
		var usernameObj = document.getElementById("username");
		var passwordObj = document.getElementById("password1");
		//onFocus(usernameObj);
		//onFocus(passwordObj);
		if (usernameObj.value.trim() == "" || usernameObj.value.trim() == null) {
			alert("用户名不能为空！");
			usernameObj.focus();
			return;
		} else if (passwordObj.value.trim() == ""
				|| passwordObj.value.trim() == null) {
			alert("密码不能为空！");
			passwordObj.focus();
			return;
		} else {
			document.getElementById("loginForm").submit();
		}
	}
	function nologin() {
		var usernameObj = document.getElementById("username");
		var passwordObj = document.getElementById("password1");
		usernameObj.value = "";
		passwordObj.value = "";
	}
	function onFocus(obj) {
		if (obj.value.trim() == "" || obj.value.trim() == null) {
			obj.className = 'requiredInput';
			obj.title = "请输入密码";
		} else {
			obj.className = '';
			obj.title = "";
		}
	}
	function hiddenPass(e) {
		e = e ? e : window.event;
		var kcode = e.which ? e.which : e.keyCode;
		var pass = document.getElementById("password1");
		var j_pass = document.getElementById("password");
		if (kcode != 8 && kcode != 13) {
			var keychar = String.fromCharCode(kcode);
			j_pass.value = j_pass.value + keychar;
			j_pass.value = j_pass.value.substring(0, pass.length);
		} else if (kcode == 13) {
			login();
		}
	}
	function changeDiv(id){
		if(id==1){
			document.getElementById("div1").style.display="";
			document.getElementById("div2").style.display="none";
		}else{
			document.getElementById("div2").style.display="";
			document.getElementById("div1").style.display="none";
		}
	}
</script>
</html>
